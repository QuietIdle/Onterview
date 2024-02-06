<script setup>
import { ref } from "vue";
import mainImg from '@/assets/interview/interviewMainIcon.png'
import { useInterviewStore } from "@/stores/interview";
import interviewMultiHelpModal from "@/components/interview/interviewMultiHelpModal.vue"

const interviewStore = useInterviewStore()

const logMessages = ref([])
const index = ref(0)
const addL = function () {
  logMessages.value.push({
    message: `답변 후 [답변 완료] 버튼을 눌러주세요 ${index.value}`,
  })
  index.value++
}
const closeL = function (idx) {
  logMessages.value.splice(idx, 1)
}

const controlMedia = function (com) {
  if (com === 0) {
    interviewStore.mediaToggle.video = !interviewStore.mediaToggle.video
  }
  else if (com === 1) {
    interviewStore.mediaToggle.audio = !interviewStore.mediaToggle.audio
  }
  else if (com === 2) {
    interviewStore.mediaToggle.volume = !interviewStore.mediaToggle.volume
  }
}

const openHelp = function () {
  interviewStore.dialog.help = true
}

</script>

<template>
  <div class="w-100 h-100 d-flex justify-space-between align-center">
    
    <div class="h-100" style="width: 30%;">

      <div class="w-100 d-flex justify-space-between pa-2 ma-2" style="border: 1px solid white; border-radius: 12px;" v-for="(log, idx) in logMessages" :key="idx">
        <v-icon icon="mdi-alert-outline" color="#FF8911"></v-icon>
        <div class="w-100 mx-1">
          <div>{{ log.message }}</div>
        </div>
        <v-icon  @click="closeL(idx)" icon="mdi-close"></v-icon>
      </div>

    </div>

    <div class="h-100 d-flex align-center justify-space-between" style="width: 60%;">
      <div class="d-flex justify-center align-center">
        <div class="">
          <v-img :src="mainImg" width="200" @click="addL()"></v-img>
        </div>
      </div>

      <div class="btn-container d-flex flex-column h-100">
        <v-btn class="ma-3" @click="controlMedia(0)" v-if="interviewStore.mediaToggle.video" icon="mdi-video" color="grey-lighten-1"></v-btn>
        <v-btn class="ma-3" @click="controlMedia(0)" v-else icon="mdi-video-off" color="grey-lighten-1"></v-btn>
        <v-btn class="ma-3" @click="controlMedia(1)" v-if="interviewStore.mediaToggle.audio" icon="mdi-microphone" color="grey-lighten-1"></v-btn>
        <v-btn class="ma-3" @click="controlMedia(1)" v-else icon="mdi-microphone-off" color="grey-lighten-1"></v-btn>
        <v-btn class="ma-3" @click="controlMedia(2)" v-if="interviewStore.mediaToggle.volume" icon="mdi-volume-high" color="grey-lighten-1"></v-btn>
        <v-btn class="ma-3" @click="controlMedia(2)" v-else icon="mdi-volume-off" color="grey-lighten-1"></v-btn>
        <v-btn class="ma-3" @click="openHelp" icon="mdi-help" color="grey-lighten-1"></v-btn>
      </div>
    </div>

  </div>

  <interviewMultiHelpModal />
</template>

<style scoped>
</style>