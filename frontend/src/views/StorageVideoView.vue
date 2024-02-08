<script setup>
import { apiMethods } from '@/api/video'
import { useStorageStore } from '@/stores/storage'
import { onMounted } from 'vue'

const storageStore = useStorageStore()

const storageDisplay = async function () {
  try {
    const result = await apiMethods.getUserVideoAll()
    storageStore.storageData.value = result.data
  } catch (error) {
    console.log(error)
  }
}

onMounted(() => {
  storageDisplay()
})
</script>

<template>
  <!-- 전체보기, 내가 쓴 게시글 보기 토글 -->
  <v-container>
    <v-row justify="end">
      <v-btn density="compact" variant="plain" :href="'#셀프스피치'"
        >셀프 스피치</v-btn
      >
      <div style="color: rgb(190, 190, 190)">|</div>
      <v-btn density="compact" variant="plain" :href="'#1인모의면접'"
        >1인 모의 면접</v-btn
      >
      <div style="color: rgb(190, 190, 190)">|</div>
      <v-btn density="compact" variant="plain" :href="'#다인모의면접'"
        >다인 모의 면접</v-btn
      >
    </v-row>
  </v-container>

  <div style="background-color: #efe6ef">
    <RouterView />
  </div>
</template>

<style scoped></style>
