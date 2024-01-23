<script setup>
import StorageVideoList from "@/components/storage/StorageVideoList.vue";
import StorageVideoGrid from "@/components/storage/StorageVideoGrid.vue";
import { apiMethods } from "@/api/video.js";
import { useStorageStore } from "@/stores/storage.js";

const pinia = useStorageStore();

const storageDisplay = async function() {
  try {
    const result = await apiMethods.getUserVideoAll();
    pinia.storageData.value = result.data;
  } catch (error) {
    console.log(error);
  }
}

storageDisplay()
</script>

<template>
  <div v-if="pinia.display===0">
    <StorageVideoList />
  </div>
  <div v-else-if="pinia.display===1">
    <StorageVideoGrid />
  </div>
  <div v-else>

  </div>
</template>

<style scoped>
</style>
