<script setup>
import { ref, onMounted } from 'vue';
import { OpenVidu } from 'openvidu-browser';
import { useWebsecketTokenStore } from "@/stores/meetingMulti";
import OvVideo from "@/components/meetingMulti/OvVideo.vue";

const websecketTokenStore = useWebsecketTokenStore()

const OV = new OpenVidu();
const session = OV.initSession()
const publisher = ref(undefined);
const remoteStreams = ref([]);

const joinSession = async function () {
  
  session.connect(websecketTokenStore.token).then(() => {
  
    publisher.value = OV.initPublisher("video-container", {
          audioSource: undefined,
          videoSource: undefined,
          publishAudio: true,
          publishVideo: true,
          resolution: "320x240",
          frameRate: 30,
          insertMode: "APPEND",
    });

    session.publish(publisher.value);
  })
  session.on("streamCreated", ({ stream }) => {
    const subscriber = session.subscribe(stream, stream.streamId);
    //console.log("Stream created by", stream, subscriber);
    remoteStreams.value.push(subscriber);
  });

}

const leaveSession = () => {
  session.disconnect();
  publisher.value.destroy();
};

onMounted(() => {
  joinSession()
})
</script>

<template>
  <div class="w-100 h-100">
    
    <div id="video-container">
      <ov-video
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