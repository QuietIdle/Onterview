<script setup>
import axios from 'axios';
import { useSelfSpeechStore } from '@/stores/selfSpeech.js';

const pinia = useSelfSpeechStore();

const backToRecording = function() {
  pinia.display = true;
  pinia.listIdx = 1;
}

const getVideo =async function() {
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

// new ver
const streamVideo = function(url) {
  const videoElement = document.querySelector('#recordedVideo');
  videoElement.controls = true;

  const mediaSource = new MediaSource();
  videoElement.src = URL.createObjectURL(mediaSource);

  mediaSource.addEventListener('sourceopen', () => {
    const sourceBuffer = mediaSource.addSourceBuffer('video/mp4; codecs="avc1.42E01E, mp4a.40.2"');

    const xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
    xhr.setRequestHeader('Range', 'bytes=0-');

    xhr.onprogress = (event) => {
      if (event.lengthComputable) {
        const percentComplete = (event.loaded / event.total) * 100;
        // 진행 상황을 표시하는 요소에 업데이트합니다.
        updateProgress(percentComplete);
      }
    };

    xhr.onload = function() {
      if (xhr.status === 200 || xhr.status === 206) {
        const arrayBuffer = xhr.response;
        sourceBuffer.addEventListener('updateend', () => {
          if (!sourceBuffer.updating && mediaSource.readyState === 'open') {
            mediaSource.endOfStream();
          }
        });
        sourceBuffer.appendBuffer(arrayBuffer);
        console.log(arrayBuffer);
      } else {
        // 에러 처리
        console.log('error');
      }
    };

    xhr.responseType = 'arraybuffer';
    xhr.send();
  });
}

function updateProgress(percentComplete) {
  // 진행 상황을 표시하는 요소를 업데이트하는 로직을 작성하세요.
  // 예: 진행 막대의 너비를 조절하거나, 텍스트로 진행 상황을 표시합니다.
  console.log('다운로드 진행 상황:', percentComplete + '%');
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
    <v-btn class="ma-3" @click="streamVideo('http://70.12.247.60:8080/api/chunk/download?filePath=myvideo.mp4')" variant="outlined">삭제</v-btn>
    <v-btn class="ma-3 ml-auto bg-red" @click="backToRecording" variant="outlined">녹화하러가기</v-btn>
  </div>
</template>

<style scoped></style>
