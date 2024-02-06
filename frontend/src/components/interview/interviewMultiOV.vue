<script setup>
import { ref, onMounted, onBeforeUnmount, watch } from 'vue';
import { OpenVidu } from 'openvidu-browser';
import { useInterviewStore ,useWebsocketStore } from "@/stores/interview";
import OvVideo from "@/components/interview/OvVideo.vue";

const websocketStore = useWebsocketStore()
const interviewStore = useInterviewStore()

const OV = new OpenVidu();
const session = OV.initSession()
const publisher = ref(undefined);
const subscribers = ref([]);

const joinSession = async function () {
  
  session.connect(websocketStore.token).then(() => {
  
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
    
    subscribers.value.push(publisher.value)
  })

  session.on("streamCreated", ({ stream }) => {
    const subscriber = session.subscribe(stream, stream.streamId);
    //console.log("Stream created by", stream, subscriber);
    subscribers.value.push(subscriber);
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

onMounted(() => {
  joinSession()
  websocketStore.stomp.send(`/room/answer/${websocketStore.sessionId}`, {})
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
    
    <div id="video-container" class="w-100 h-100 d-flex align-center justify-space-around">

      <div v-for="(sub, idx) in subscribers" :key="sub.stream.streamId" class="ma-2">
        <div class="w-100 bg-grey-lighten-1 text-center my-1" style="border-radius: 12px;">{{ idx+1 }}번 째 답변자</div>
        <ov-video
          :id="sub.stream.streamId" 
          :stream-manager="sub"
          style="transform: rotateY(180deg);"
        />
        <div>{{ sub.stream.streamId }}</div>
      </div>
    </div>

  </div>
</template>

<style scoped>
</style>