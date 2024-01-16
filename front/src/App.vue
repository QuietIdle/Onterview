<script setup lang="ts">
// import VLayout from '@/components/common/VLayout.vue'
import MainNav from '@/components/common/MainNav.vue'
import { useRoute } from 'vue-router'

const route = useRoute()
</script>

<template>
  <div>
    <MainNav v-if="route.meta.layout == 'main'" />
    <RouterView />
  </div>
</template>

<style scoped></style>
