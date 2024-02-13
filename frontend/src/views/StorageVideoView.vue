<script setup>
import StorageVideoList from "@/components/storage/StorageVideoList.vue";
import StorageVideoGrid from "@/components/storage/StorageVideoGrid.vue";
import StorageVideoPlay from "@/components/storage/StorageVideoPlay.vue";
import { apiMethods } from "@/api/video";
import { useStorageStore } from "@/stores/storage";
import { onMounted } from "vue";

const storageStore = useStorageStore();

const storageDisplay = async function() {
  try {
    const result = await apiMethods.getUserVideoAll('SELF');
    storageStore.storageData.value = result.data;
  } catch (error) {
    console.log(error);
  }
}

onMounted(() => {
  storageDisplay()
})
</script>

<template>
  <div v-if="!storageStore.display.stream && storageStore.display.list">
    <StorageVideoList />
  </div>
  <div v-else-if="!storageStore.display.stream && !storageStore.display.list">
    <StorageVideoGrid />
  </div>
  <div v-else-if="storageStore.display.stream">
    <StorageVideoPlay />
  </div>
</template>

<style scoped>
</style>
