<script setup>
import axios from 'axios';
import { useSelfSpeechStore } from '@/stores/selfSpeech.js';

const pinia = useSelfSpeechStore();

function backToRecording() {
  pinia.display = true;
  pinia.listIdx = 1;
}

async function getVideo() {
  try {
    const response = await axios.get('http://70.12.247.60:8080/stream?filePath=f7e694f9-6f83-4e52-9ac3-712a055df47d.mp4');
    //console.log(response.data);
    const recordingPlayer = document.querySelector('#recordedVideo');
    recordingPlayer.src = 'http://70.12.247.60:8080/stream?filePath=f7e694f9-6f83-4e52-9ac3-712a055df47d.mp4';
    //recordingPlayer.play();
  }
  catch {
    console.log('err');
  }
}

// function playRecording() {
//     const recordingPlayer = document.querySelector("#recordedVideo");
//     const recordedBlob = new Blob(recordedChunks, { type: "video/webm" });
//     recordingPlayer.src = URL.createObjectURL(recordedBlob);
//     recordingPlayer.play();
//     downloadButton.href = recordingPlayer.src;
//     downloadButton.download = `recording_${new Date()}.webm`;
//     console.log(recordedChunks);
// }

function streamVideo(url) {
  const xhr = new XMLHttpRequest();
  console.log(xhr);
  xhr.open('GET', url, true);
  xhr.setRequestHeader('Range', 'bytes=0-'); // 전체 파일을 요청하고 싶다면 Range 헤더를 지우세요

  let arrayBuffer = [];
  const contentType = xhr.getResponseHeader('Content-Type');
  const recordingPlayer = document.querySelector('#recordedVideo');

  xhr.onload = function () {
    if (xhr.status === 200 || xhr.status === 206) { // 200: 전체 파일, 206: 청크
      
      // arrayBuffer = xhr.response;
      // TODO: arrayBuffer를 처리하는 로직을 작성하세요
      // const videoBlob = new Blob([arrayBuffer], { type: contentType });
      // const videoUrl = URL.createObjectURL(videoBlob);
      
      // recordingPlayer.src = videoUrl;
      // recordingPlayer.controls = true;
    } else {
      // 에러 처리
    }
  };

  xhr.onprogress = function (event) {
    if (event.lengthComputable) {
      const percentComplete = (event.loaded / event.total) * 100;
      // TODO: 다운로드 진행 상황을 표시하는 로직을 작성하세요
      const tempBuffer = xhr.response;
      
      arrayBuffer.push(tempBuffer);
      const videoBlob = new Blob(arrayBuffer, { type: contentType });
      const videoUrl = URL.createObjectURL(videoBlob);
      console.log(videoUrl)
      recordingPlayer.src = videoUrl;
      recordingPlayer.controls = true;
    }
  };

  xhr.send();
}

</script>

<template>
  <div class="w-auto d-flex align-center">
    <div class="ma-3">질문을 선택하세요</div>
  </div>
  <div class="w-100 h-75 text-center">
    <video id="recordedVideo" width="450"></video>
  </div>
  <div class="btn-container w-100 d-flex align-center">
    <v-btn class="ma-3" @click="getVideo" variant="outlined">북마크</v-btn>
    <v-btn class="ma-3" @click="streamVideo('http://70.12.247.60:8080/stream?filePath=myvideo.mp4')" variant="outlined">삭제</v-btn>
    <v-btn class="ma-3 ml-auto bg-red" @click="backToRecording" variant="outlined">녹화하러가기</v-btn>
  </div>
</template>

<style scoped></style>
