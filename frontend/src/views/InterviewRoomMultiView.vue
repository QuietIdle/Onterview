<script setup>
import interviewMultiInterviewer from "@/components/interview/interviewMultiInterviewer.vue";
import interviewMultiOV from "@/components/interview/interviewMultiOV.vue";
import { useRouter } from "vue-router";
import { useInterviewStore } from "@/stores/interview";

const interviewStore = useInterviewStore()
const router = useRouter()

const goInterviewMain = function () {
  interviewStore.ov = false
  router.push({name: 'interview'})
}
</script>

<template>
<div class="main-container w-screen h-screen d-flex flex-column bg-black">

  <v-layout style="height: 50px;">
    <v-system-bar 
      class="nav-bar d-flex justify-space-between pa-0"
      height="50" 
      window
      color="black" 
      style="border-bottom: 1px solid white;"
    >
      <div class="d-flex align-center justify-center">
        <div>{{ interviewStore.choice.type }}</div>
      </div>

      <div class="d-flex align-center">
        <v-icon icon="mdi-record" color="#FA3F3F"></v-icon>
        time
      </div>

      <div class="d-flex align-center px-3 w-75" style="font-size: large;" >
        Q.평양냉면 맛있다 우래옥 을밀대
      </div>

      <button @click="goInterviewMain" class="d-flex justify-center align-center">
        <v-icon icon="mdi-close"></v-icon>
        <div>나가기</div>
      </button>

    </v-system-bar>
  </v-layout>

  <div class="interviewer-container w-100 h-50">
    <interviewMultiInterviewer />
  </div>

  <div class="w-100 h-50">
    <interviewMultiOV />
  </div>

</div>
</template>

<style scoped>
.nav-bar>*{
  border-left: 1px solid white;
  height: 100%;
  padding: 8px;
}
.interviewer-container{
  border-bottom: 1px solid white;
}
</style>