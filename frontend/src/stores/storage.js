import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useStorageStore = defineStore('storage', () => {
    const display = ref({
        list: true,
        stream: false,
    });
    const storageData = ref([]);
    const switchDisplay = function (com) {
        if (com === 0) {
            display.value.list = !display.value.list
        }
        else if (com === 1) {
            display.value.stream = !display.value.stream
        }
    }
    
    const videoData = ref({
        feedback: "자가진단진단",
        bookmark: false,
        videoUrl: {
          saveFilename: "",
        }
      });

    return {
        display,
        storageData,
        switchDisplay,
        videoData,
    }
})