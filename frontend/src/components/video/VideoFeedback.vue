<script setup>
import { useSelfSpeechStore } from '@/stores/selfSpeech';

const selfSpeechStore = useSelfSpeechStore();
</script>

<template>
  <div>{{ selfSpeechStore.videoData.feedback }}</div>
</template>

<style scoped></style>
