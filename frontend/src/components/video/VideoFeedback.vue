<script setup>
import { ref } from 'vue';
import { useSelfSpeechStore } from '@/stores/selfSpeech.js';

const pinia = useSelfSpeechStore();
</script>

<template>
  <div>{{ pinia.videoData.feedback }}</div>
</template>

<style scoped></style>
