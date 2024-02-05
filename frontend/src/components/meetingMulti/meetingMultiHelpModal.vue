<script setup>
import meetingMultiHelp from "@/components/meetingMulti/meetingMultiHelp.vue"
import { useMeetingMultiStore } from "@/stores/meetingMulti";

const meetingMultiStore = useMeetingMultiStore()
</script>

<template>
  <v-dialog 
    v-model="meetingMultiStore.dialog.help"
  >
    <v-card class="bg-purple-lighten-4 pa-5">
      <v-card-title class="d-flex flex-row-reverse">
        <v-btn @click="meetingMultiStore.dialog.help=false" icon="mdi-close"></v-btn>
      </v-card-title>
      <v-card-text class="bg-white">
        <div class="w-100 h-100">
          <meetingMultiHelp />
        </div>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<style scoped>

</style>