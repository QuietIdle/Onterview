<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const articleId = ref(route.params.articleId)

onMounted(() => {
  console.log(articleId.value)
})
</script>

<template>
  <div>{{ articleId }}</div>
</template>

<style scoped></style>
