<script setup>
import StorageVideoList from "@/components/storage/StorageVideoList.vue";
import StorageVideoGrid from "@/components/storage/StorageVideoGrid.vue";
import { apiMethods } from "@/api/video";
import { useStorageStore } from "@/stores/storage";

const storageStore = useStorageStore();

const storageDisplay = async function() {
  try {
    const result = await apiMethods.getUserVideoAll();
    storageStore.storageData.value = result.data;
  } catch (error) {
    console.log(error);
  }
}

storageDisplay()
</script>

<template>
  <div v-if="storageStore.display===0">
    <StorageVideoList />
  </div>
  <div v-else-if="storageStore.display===1">
    <StorageVideoGrid />
  </div>
  <div v-else>

  </div>
</template>

<style scoped>
</style>
