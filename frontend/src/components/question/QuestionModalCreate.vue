<script setup>
import { ref } from 'vue'

const dialog = ref(false)
</script>

<template>
  <div>
    <v-dialog v-model="dialog" width="auto">
      <template v-slot:activator="{ props }">
        <v-btn v-bind="props" class="ml-3" variant="elevated" color="blue-accent-2">새 폴더</v-btn>
      </template>
      <v-card class="px-6 pt-3" width="1000">
        <v-card-text>새 폴더 추가</v-card-text>
        <v-text-field
          variant="solo"
          placeholder="추가할 폴더명을 작성해주세요"
          class="pb-6"
        ></v-text-field>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn variant="text" color="grey-darken-1" @click="dialog = false">취소</v-btn>
          <v-btn variant="text" color="deep-purple-accent-4" @click="dialog = false">추가</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<style scoped></style>
