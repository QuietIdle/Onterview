<script setup>
import { ref } from "vue";
import mainImg from '@/assets/meeting/meetingMainIcon.png'
import { useRouter } from "vue-router";
import { multiApi } from "@/api/meetingMulti";
import meetingMultiHelp from "@/components/meetingMulti/meetingMultiHelp.vue"
import meetingMultiConfig from "@/components/meetingMulti/meetingMultiConfig.vue"

const router = useRouter()

const dialog = ref({
  wait: false,
  help: false,
}) // 모달

const steps = ref(["면접자 인원 선택", "면접 유형 선택", "시작하기"]);
const peopleCount = ref(false); // 1인 - 다인
const type = ref(false); // 면접 유형
const choice = ref({
  people: 'solo',
  type: '인성면접',
  typeDetail: 'backend'
})

const choosePeople = function (val) {
  peopleCount.value = val;
  choice.value.people = peopleCount.value ? 'multi' : 'solo';
}

const chooseType = function (val) {
  type.value = val;
  choice.value.type = type.value ? '직무면접' : '인성면접';
  if (choice.value.type === '인성면접') {
    choice.value.typeDetail = ''
  }
  else choice.value.typeDetail = 'backend'
}

const enter = function () {
  
  if (choice.value.people === 'multi') {
    dialog.value.wait = true
  }
  else {
    alert('1인 방')
  }
}

const openHelp = function () {
  dialog.value.help = true
}


const selected = ref(0) // 매칭화면 '환경설정'/'도움말' 탭
const time = ref({
  second: 0,
  match: false,
}) // 대기 시간, 매칭 여부
let timerId;

const startTimer = function() {
  time.value.second++;
  stopTimer();
  timerId = setTimeout(startTimer, 1000); // 스탑워치 주기 1초
}

const stopTimer = function() {
  if (timerId !== null) {
    clearTimeout(timerId);
  }
}

const startMatch = async function () {
  startTimer()
  time.value.match = true

  try {
    const result = await multiApi.getMatch()
    console.log(result.data)
  } catch (error) {
    console.log(error)
  }
}

const stopMatch = function () {
  dialog.value.wait = false
  stopTimer()
  time.value.match = false
  time.value.second = 0
}

// 웹 소켓

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
            <v-radio-group class="job-detail" v-if="type" v-model="choice.typeDetail">
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
  <v-dialog 
    v-model="dialog.wait" 
    fullscreen
  >
    <v-card class="bg-purple-lighten-4 pa-5">
      <v-card-title class="text-center">
        다인 모의 면접 대기실
      </v-card-title>
      <v-divider class="border-opacity-100"></v-divider>
      <div class="mt-5 d-flex justify-space-between">
        <v-btn-toggle 
          v-model="selected"
          mandatory
          color="purple-darken-3"
          class="btn-container"
          @click="console.log(selected)"
        >
          <v-btn elevation="2">
            환경설정
          </v-btn>
          <v-btn elevation="2">
            도움말
          </v-btn>
        </v-btn-toggle>
        <div v-if="!choice.typeDetail">{{ choice.type }}</div>
        <div v-else>{{ choice.type }} - {{ choice.typeDetail }}</div>
      </div>

      <v-card-text class="bg-white">
        <div v-show="selected===0" class="w-100 h-100">
          <meetingMultiConfig />
        </div>

        <div v-show="selected===1" class="w-100 h-100">
          <meetingMultiHelp />
        </div>
      </v-card-text>

      <v-card-actions class="d-flex flex-row-reverse btns">
        <v-btn @click="stopMatch" class="ma-1" rounded elevation="4" size="x-large" style="background-color: #9B9B9B;">매칭취소</v-btn>
        <v-btn v-if="!time.match" @click="startMatch" class="ma-1" rounded elevation="4" size="x-large" style="background-color: #A069B3;">매칭시작</v-btn>
        <v-btn v-else class="ma-1" rounded elevation="4" size="x-large" style="background-color: #A069B3;" disabled>매칭 대기 시간 {{ String(Math.floor(time.second/60)).padStart(2,'0') }} : {{ String(time.second%60).padStart(2,'0') }}</v-btn>
      </v-card-actions>

    </v-card>
  </v-dialog>
  
  <!-- 도움말 모달 창 -->
  <v-dialog 
    v-model="dialog.help"
  >
    <v-card class="bg-purple-lighten-4 pa-5">
      <v-card-title class="d-flex flex-row-reverse">
        <v-btn @click="dialog.help=false" icon="mdi-close"></v-btn>
      </v-card-title>
      <v-card-text class="bg-white">
        <div class="w-100 h-100">
          <meetingMultiHelp />
        </div>
      </v-card-text>
    </v-card>
  </v-dialog>
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