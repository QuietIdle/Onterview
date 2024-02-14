<script setup>
import { ref, onMounted } from 'vue'
import { useStorageStore } from '@/stores/storage'

const storageStore = useStorageStore()
const roomType = ref('self')

onMounted(() => {
  storageStore.requestUserVideoAll(roomType.value)
})
</script>

<template>
  <!-- 전체보기, 내가 쓴 게시글 보기 토글 -->
  <v-container>
    <v-row justify="end" align="center">
      <div
        :class="{ active: roomType === 'self', inactive: roomType !== 'self' }"
        @click="(roomType = 'self'), storageStore.requestUserVideoAll(roomType)"
      >
        셀프 스피치
      </div>
      <div style="color: rgb(190, 190, 190)">|</div>
      <div
        :class="{
          active: roomType === 'single',
          inactive: roomType !== 'single'
        }"
        @click="
          (roomType = 'single'), storageStore.requestUserVideoAll(roomType)
        "
      >
        1인 모의 면접
      </div>
      <div style="color: rgb(190, 190, 190)">|</div>
      <div
        :class="{
          active: roomType === 'multi',
          inactive: roomType !== 'multi'
        }"
        @click="
          (roomType = 'multi'), storageStore.requestUserVideoAll(roomType)
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
