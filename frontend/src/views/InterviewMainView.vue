<script setup>
import { ref } from "vue"
import mainImg from '@/assets/interview/interviewMainIcon.png'
import BtnImg1 from "@/assets/interview/interviewMainBtnIcon1.png"
import BtnImg2 from "@/assets/interview/interviewMainBtnIcon2.png"
import BtnImg3 from "@/assets/interview/interviewMainBtnIcon3.png"
import BtnImg4 from "@/assets/interview/interviewMainBtnIcon4.png"
import { useRouter } from "vue-router"
import { useInterviewStore } from "@/stores/interview"
import interviewMultiHelpModal from "@/components/interview/interviewMultiHelpModal.vue"
import interviewMultiWait from "@/components/interview/interviewMultiWait.vue"

const router = useRouter()
const interviewStore = useInterviewStore()

const steps = ref(["면접 인원 선택", "면접 유형 선택", "시작하기"])
const peopleCount = ref(interviewStore.people === 'MULTI')  // 1인 - 다인
const type = ref(interviewStore.type === '직무면접')  // 면접 유형

const choosePeople = function (val) {
  peopleCount.value = val
  interviewStore.choice.people = peopleCount.value ? 'MULTI' : 'SINGLE'
}

const chooseType = function (val) {
  type.value = val
  if (val) {
    interviewStore.choice.type = '직무면접'
    interviewStore.choice.typeDetail = 'BACKEND'
  } else {
    interviewStore.choice.type = '인성면접'
    interviewStore.choice.typeDetail = ''
  }
}

const enter = function () {

  if (interviewStore.choice.people === 'MULTI') {
    interviewStore.dialog.wait = true
  }
  else {
    alert('1인 방')
  }
}

const openHelp = function () {
  interviewStore.dialog.help = true
}

</script>

<template>
  <div class="pa-10 d-flex justify-center align-center">
    <div class="mr-3" style="font-size: 32px;">
      모의 면접관과 <br> 함께하는 <br> 모의 면접 <br> 시작하기
    </div>
    <div class="ml-3">
      <v-img :src="mainImg" width="200"></v-img>
    </div>
  </div>

  <div>
    <div class="item-header d-flex justify-space-between">
      <div class="step" v-for="(step, n) in steps" :key="n">
        step {{ n + 1 }} - {{ step }}
      </div>
    </div>

    <div class="item-wrapper d-flex justify-space-between">

      <div class="item">
        <div class="item-body">
          <button class="btn d-flex justify-center align-center" :class="{ isSelect: !peopleCount }"
            @click="choosePeople(false)">
            <div class="w-25 ma-2">1인</div>
            <div class="w-25 ma-2"><v-img :src="BtnImg1"></v-img></div>
          </button>

          <button class="btn d-flex justify-center align-center" :class="{ isSelect: peopleCount }"
            @click="choosePeople(true)">
            <div class="w-25 ma-2">3인~4인</div>
            <div class="w-25 ma-2"><v-img :src="BtnImg2"></v-img></div>
          </button>
        </div>
      </div>

      <div class="item">
        <div class="item-body">
          <div class="job">
            <button :class="{ isSelect: type }" @click="chooseType(true)" class="btn job-btn">
              <div class="w-50 ma-1">직무</div>
              <div class="w-50 ma-1"><v-img :src="BtnImg3"></v-img></div>
            </button>
            <v-radio-group class="job-detail" v-if="type" v-model="interviewStore.choice.typeDetail">
              <v-radio label="백엔드" value="BACKEND"></v-radio>
              <v-radio label="프론트엔드" value="FRONTEND"></v-radio>
            </v-radio-group>
          </div>
          <div class="fit">
            <button class="btn fit-btn" :class="{ isSelect: !type }" @click="chooseType(false)">
              <div class="w-25 ma-1">인성</div>
              <div class="ma-1" style="width: 35%;"><v-img :src="BtnImg4"></v-img></div>
            </button>
          </div>
        </div>
      </div>

      <div class="item">
        <div class="item-body enter-body">
          <button @click="enter" class="btn enter">
            <div class="text-h3" style="font-weight: bold;">입장하기</div>
            <br>
            <div>환경세팅 및 매칭 대기가 시작됩니다.</div>
          </button>
          <v-btn class="help-btn" icon="mdi-help" @click="openHelp"></v-btn>
        </div>
      </div>
    </div>
  </div>

  <!-- 매칭 대기 모달 창 -->
  <interviewMultiWait />

  <!-- 도움말 모달 창 -->
  <interviewMultiHelpModal />
</template>

<style scoped>
.item {
  width: 100%;
  margin: 0 0.1em;
}

.item-header {
  font-weight: bold;
}

.step {
  background-color: #C4BEDE;
  text-align: center;
  width: 100%;
  margin: 0.1em;
  padding: 0.3em 0;
}

.item-body {
  background-color: #E0DCF2;
  height: 70vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.btn {
  margin: 0.8em;
  width: 90%;
  border: 0;
  font-weight: bold;
  cursor: pointer;
  background-color: #fff;
  border-radius: 6px;
  height: 30vh;
}

.fit {
  width: 100%;
}

.fit-btn {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.job {
  width: 100%;
  display: flex;
}

.job-btn {
  width: 50%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.job-detail {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 0 0.5em;
}

.enter {
  background-color: #B3ABD6;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 3px, rgba(0, 0, 0, 0.24) 0px 1px 2px;
}

.enter-body {
  position: relative;
}

.help-btn {
  position: absolute;
  bottom: 5%;
  right: 5%;
}

.isSelect {
  border: 0.2em solid #8A439C;
}

.btns>* {
  color: white;
}
</style>