<script setup>
import { ref, onMounted } from 'vue'
import { useStorageStore } from '@/stores/storage'

const storageStore = useStorageStore()

onMounted(() => {
  storageStore.requestUserVideoAll()
})
</script>

<template>
  <!-- 전체보기, 내가 쓴 게시글 보기 토글 -->
  <v-container>
    <v-row justify="end" align="center">
      <div
        :class="{
          active: storageStore.roomType === 'self',
          inactive: storageStore.roomType !== 'self'
        }"
        @click="
          (storageStore.roomType = 'self'), storageStore.requestUserVideoAll()
        "
      >
        셀프 스피치
      </div>
      <div style="color: rgb(190, 190, 190)">|</div>
      <div
        :class="{
          active: storageStore.roomType === 'single',
          inactive: storageStore.roomType !== 'single'
        }"
        @click="
          (storageStore.roomType = 'single'),
            storageStore.requestInterviewList()
        "
      >
        1인 모의 면접
      </div>
      <div style="color: rgb(190, 190, 190)">|</div>
      <div
        :class="{
          active: storageStore.roomType === 'multi',
          inactive: storageStore.roomType !== 'multi'
        }"
        @click="
          (storageStore.roomType = 'multi'), storageStore.requestInterviewList()
        "
      >
        다인 모의 면접
      </div>
    </v-row>
  </v-container>

  <div style="background-color: #efe6ef">
    <RouterView />
  </div>
</template>

<style scoped>
.active {
  font-size: 0.9rem;
  margin: auto 10px;
}

.inactive {
  font-size: 0.9rem;
  margin: auto 10px;
  color: grey;
}

.inactive:hover {
  color: black;
}
</style>
