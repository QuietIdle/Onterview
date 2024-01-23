import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useStorageStore = defineStore('storage', () => {
    const display = ref(0);
    const storageData = ref([]);
    const switchDisplay = function () {
        display.value = (display.value + 1) % 2;
      }

    return {
        display,
        storageData,
        switchDisplay,
    }
})