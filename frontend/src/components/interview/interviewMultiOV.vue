<script setup>
import { ref, onMounted, onBeforeUnmount, watch } from 'vue';
import { OpenVidu } from 'openvidu-browser';
import { useInterviewStore, useWebsocketStore } from "@/stores/interview";
import { useUserStore } from "@/stores/user"
import { apiMethods, fileServer } from "@/api/video";
import OvVideo from "@/components/interview/OvVideo.vue";
import { v4 as uuidv4 } from 'uuid'

const userStore = useUserStore()
const websocketStore = useWebsocketStore()
const interviewStore = useInterviewStore()

const OV = new OpenVidu();
const session = OV.initSession()
const publisher = ref(undefined);
const subscribers = ref([]);
const myStream = ref(null)
OV.enableProdMode()

let recorder
let recordedChunks = [];
let endOfChunk = 0;
const filename = ref(null)

const num = String(websocketStore.roomData.index)
// 디버깅용 이름
const name = num + num + num + num
//const name = userStore.nickname


// 방 입장
const joinSession = async function () {
  
  session.connect(websocketStore.roomData.token, { clientData: {name: name, id: websocketStore.roomData.index} })
    .then(() => {
  
    publisher.value = OV.initPublisher(undefined, {
      audioSource: undefined,
      videoSource: undefined,
      publishAudio: true,
      publishVideo: true,
      resolution: "320x240",
      frameRate: 30,
      insertMode: "APPEND",
      mirror: false,
    })

    session.publish(publisher.value)
    myStream.value = Object(publisher.value.stream)
    
    subscribers.value.push({
      subscriber: publisher.value,
      id: websocketStore.roomData.index,
    })
  })

  session.on("streamCreated", ({ stream }) => {
    const subscriber = session.subscribe(stream, stream.streamId);
    //console.log("Stream created by", stream, subscriber);
    subscribers.value.push({
      subscriber: subscriber,
      id: JSON.parse(subscriber.stream.connection.data).clientData.id,
    });
  });
}

const leaveSession = () => {
  
  if (session) {
    session.disconnect();
    if (publisher.value) {
      session.unpublish(publisher.value)
    }
    //mainStreamManager = undefined;
    subscribers.value = [];

    websocketStore.stomp.disconnect()
  }
};

//녹화 기능

const startRecording = function() {
  const myVideoStream = document.querySelector(`#${myStream.value.streamId}`).captureStream()
  filename.value = uuidv4();
  endOfChunk = 0
  let idxOfChunk = 0
  recordedChunks.length = 0

  recorder = new MediaRecorder(myVideoStream);

  recorder.ondataavailable = (e) => {
    if (e.data.size > 0) {
      recordedChunks.push(e.data);
      idxOfChunk++;
    }
    sendToServer(e.data, idxOfChunk);
  }
  recorder.start(3000);
}

const sendToServer = async function(chunk, idx) {
  try {
    // FormData를 생성하고 녹화된 데이터를 추가
    const formData = new FormData();
    formData.append('chunk', chunk);

    const jsonData = {
      filename: filename.value,
      username: userStore.email || "null",
      chunkNumber: idx,
      endOfChunk: endOfChunk,
    }
    formData.append('jsonData', new Blob([JSON.stringify(jsonData)], {
      type: "application/json",
    }))
    // axios를 사용하여 POST 요청을 서버로 보냄
    const response = await fileServer.uploadVideo(formData);
    //console.log('Chunk sent successfully!', response);
    if (response.status === 200) {
      console.log('upload success', response.data);
    }
  } catch (error) {
    console.error('Error sending chunk to server:', error);
  }
}

const stopRecording = function () {
  endOfChunk = 1;
  
  recorder.stop();
  recordedChunks.length = 0
}

// 순서 재배치
const reArrangeById = (arr, idOrder) => {
  // 주어진 ID 배열의 순서대로 배열을 재배치하는 함수
  return idOrder.map(id => arr.find(item => item.id === id));
};

const changePosition = function (orders) {
  subscribers.value = reArrangeById(subscribers.value, orders)
  console.log('changed!!!!!!!!!!!!!')
}

// 웹소켓 통신
const sendMessage = async function (type, idx) {
  await websocketStore.stomp.send(`/server/answer/${websocketStore.roomData.sessionId}`, {}, JSON.stringify({
    type: type,
    index: idx,  
  }))
}

const receive = async function (message) {
  const result = JSON.parse(message.body);
  websocketStore.message = result;
  console.log(result);

  switch (result.type) {
    case 'ENTER':
      websocketStore.now.orders = result.orders;
      websocketStore.flag.interviewer = !websocketStore.flag.interviewer;
      await interviewStore.TTS(interviewStore.script.enter)
      changePosition(result.orders)
      sendMessage('START')
      break;

    case 'START':
      websocketStore.flag.interviewer = !websocketStore.flag.interviewer;
      break;

    case 'PROCEEDING':
      websocketStore.now.turn = result.number + 1;
      websocketStore.flag.interviewer = !websocketStore.flag.interviewer;
      break;

    case 'TIMEOUT':
      break;

    case 'FINISH':
      websocketStore.now.turn = result.number + 1;
      websocketStore.flag.interviewer = !websocketStore.flag.interviewer;
      
      websocketStore.now.orders = result.orders;
      await interviewStore.TTS(interviewStore.script.finish)
      changePosition(result.orders);
      sendMessage('START')
      break;

    case 'END':
      websocketStore.flag.interviewer = !websocketStore.flag.interviewer;
      break;

    default:
      break;
  }
};

onMounted(() => {
  joinSession()

  websocketStore.stomp.unsubscribe()
  websocketStore.stomp.subscribe(`/client/answer/${websocketStore.roomData.sessionId}`, function (message) {
    receive(message)
  })
  websocketStore.stomp.send(`/server/answer/${websocketStore.roomData.sessionId}`, {},
    JSON.stringify({
      type: 'ENTER',
    })
  )
})

onBeforeUnmount(() => {
  leaveSession()
})

watch(interviewStore.mediaToggle ,
  () => {
    if (publisher.value) {
      publisher.value.publishVideo(interviewStore.mediaToggle.video)
      publisher.value.publishAudio(interviewStore.mediaToggle.audio)
    }
  }
)
</script>

<template>
  <div class="w-100 h-100 d-flex align-center">
    <v-btn @click="startRecording">g</v-btn>
    <v-btn @click="stopRecording">s</v-btn>
    <div id="video-container" class="w-100 h-100 d-flex align-center justify-space-around">
      <div v-for="(item, idx) in subscribers" :key="item.subscriber.stream.streamId" class="ma-2">
        <div class="w-100 bg-grey-lighten-1 text-center my-1" style="border-radius: 12px;">{{ idx+1 }}번 째 답변자</div>
        <ov-video
          :id="item.subscriber.stream.streamId"
          :stream-manager="item.subscriber"
          style="transform: rotateY(180deg);"
          :muted="!interviewStore.mediaToggle.volume"
        />
        <div class="d-flex align-center">
          <div>답변 중</div>
          <div>{{ JSON.parse(item.subscriber.stream.connection.data).clientData.name }}</div>
        </div>
        
      </div>
    </div>

  </div>
</template>

<style scoped>
</style>