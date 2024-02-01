<script setup>
import { ref } from "vue";
import mainImg from '@/assets/meeting/meetingMainIcon.png'
import { useRouter } from "vue-router";
import { useMeetingMultiStore } from "@/stores/meetingMulti";
import meetingMultiHelpModal from "@/components/meetingMulti/meetingMultiHelpModal.vue"
import meetingMultiWait from "@/components/meetingMulti/meetingMultiWait.vue";

const router = useRouter()

const meetingMultiStore = useMeetingMultiStore()

const steps = ref(["면접자 인원 선택", "면접 유형 선택", "시작하기"]);
const peopleCount = ref(false); // 1인 - 다인
const type = ref(false); // 면접 유형


const choosePeople = function (val) {
  peopleCount.value = val;
  meetingMultiStore.choice.people = peopleCount.value ? 'multi' : 'solo';
}

const chooseType = function (val) {
  type.value = val
  if (val) {
    meetingMultiStore.choice.type = '직무면접'
    meetingMultiStore.choice.typeDetail = 'backend'
  }
  else {
    meetingMultiStore.choice.type = '인성면접'
    meetingMultiStore.choice.typeDetail = ''
  }
}

const enter = function () {
  
  if (meetingMultiStore.choice.people === 'multi') {
    meetingMultiStore.dialog.wait = true
  }
  else {
    alert('1인 방')
  }
}

const openHelp = function () {
  meetingMultiStore.dialog.help = true
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
        step {{n+1}} - {{step}}
      </div>
    </div>

    <div class="item-wrapper d-flex justify-space-between">

      <div class="item">
        <div class="item-body">
          <button class="btn" :class="{ isSelect : !peopleCount}" @click="choosePeople(false)">
            1인
          </button>
          <button class="btn" :class="{ isSelect : peopleCount}" @click="choosePeople(true)">
            3 ~ 4인
          </button>
        </div>
      </div>

      <div class="item">
        <div class="item-body">
          <div class="job">
            <button :class="{ isSelect : type}" @click="chooseType(true)" class="btn job-btn">
              직무
            </button>
            <v-radio-group class="job-detail" v-if="type" v-model="meetingMultiStore.choice.typeDetail">
              <v-radio
                label="백엔드"
                value="backend"
              ></v-radio>
              <v-radio
                label="프론트엔드"
                value="frontend"
              ></v-radio>
            </v-radio-group>
          </div>
          <div class="fit">
            <button class="btn fit-btn" :class="{ isSelect : !type}" @click="chooseType(false)">
              인성
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
  <meetingMultiWait />
  
  <!-- 도움말 모달 창 -->
  <meetingMultiHelpModal />
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
}.fit {
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

.enter-body{
  position: relative;
}

.help-btn{
  position: absolute;
  bottom: 5%;
  right: 5%;
}
.isSelect {
  border: 0.2em solid #8A439C;
}

.btns>*{
  color: white;
}

</style>