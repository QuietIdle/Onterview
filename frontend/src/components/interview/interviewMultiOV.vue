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
const remoteStreams = ref([]);

const joinSession = async function () {
  
  session.connect(websocketStore.token).then(() => {
  
    publisher.value = OV.initPublisher("video-container", {
          audioSource: undefined,
          videoSource: undefined,
          publishAudio: true,
          publishVideo: true,
          resolution: "320x240",
          frameRate: 30,
          insertMode: "APPEND",
    }).then(() => {
      session.publish(publisher.value);
    })
  })

  session.on("streamCreated", ({ stream }) => {
    const subscriber = session.subscribe(stream, stream.streamId);
    //console.log("Stream created by", stream, subscriber);
    remoteStreams.value.push(subscriber);
  });

}

const leaveSession = () => {
  
  if (session) {
    session.disconnect();
    publisher.value.destory()
    //mainStreamManager = undefined;
    publisher.value = undefined;
    remoteStreams.value = [];
  }
};

onMounted(() => {
  joinSession()
})

onBeforeUnmount(() => {
  leaveSession()
})
</script>

<template>
  <div class="w-100 h-100 d-flex align-center">
    
    <div id="video-container" class="w-100 h-100 d-flex align-center justify-space-around">
      <ov-video
        class="ma-2"
        v-for="stream in remoteStreams" 
        :key="stream.stream.streamId" 
        :id="stream.stream.streamId" 
        :stream-manager="stream"
        style="transform: rotateY(180deg);"
      ></ov-video>
    </div>

  </div>
</template>

<style scoped>
</style>