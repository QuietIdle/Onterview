<script setup>
import meetingMultiInterviewer from "@/components/meetingMulti/meetingMultiInterviewer.vue";
import meetingMultiOV from "@/components/meetingMulti/meetingMultiOV.vue";
</script>

<template>
<div class="main-container w-screen h-screen d-flex flex-column bg-black">

  <v-layout style="height: 50px;">
    <v-system-bar 
      class="nav-bar d-flex justify-space-between pa-0"
      height="50" 
      window color="black" 
      style="border-bottom: 1px solid white;"
    >
      <div style="border: 0px"></div>
      <div></div>
      <div class="d-flex align-center w-75 px-3" style="font-size: large;">Q.평양냉면 맛있다 우래옥 을밀대 </div>
      <button class="d-flex justify-center align-center">
        <v-icon color="white" icon="mdi-close"></v-icon>
        <div>나가기</div>
      </button>
    </v-system-bar>
  </v-layout>

  <div class="bg-white w-100 h-50">
    <meetingMultiInterviewer />
  </div>

  <div class="bg-green w-100 h-50">
    <meetingMultiOV />
  </div>

</div>
</template>

<style scoped>
.nav-bar>*{
  border-left: 1px solid white;
  height: 100%;
  padding: 0 4px;
}
</style>