<script setup>
import { ref } from 'vue'
import { useUserStore } from '@/stores/user.js'

const userStore = useUserStore()
const tab = ref(1)
const fileInput = ref(null)

const fileRules = [
  (value) => {
    return !value || !value.length || value[0].size < 1000000 || '파일 크기는 1MB 이하여야 합니다.'
  },
]
const selectedImage = ref(null)
const openFileInput = function () {
  fileInput.value.click()
}


const nicknameRules = [
  (value) => {
    if (value) {
      return true
    } else {
      return '닉네임을 입력해주세요.'
    }
  },
  (value) => {
    const koreanRegex = /^[가-힣]{2,8}$/

    if (koreanRegex.test(value)) {
      return true
    } else {
      return '닉네임은 2~8자의 한글이어야 합니다.'
    }
  },
  (value) => {
    if (isDuplicatedNickname.value === null) {
      return true
    } else if (isDuplicatedNickname.value === true) {
      return '사용할 수 있는 닉네임입니다.'
    } else {
      return '이미 사용 중인 닉네임입니다.'
    }
  }
]
</script>

<template>
  <div class="profile px-5">
    <h3 class="mb-5">프로필 이미지</h3>
    <v-row>
      <v-col class="v-col-2">
        <v-avatar color="surface-variant" size="100"></v-avatar>
      </v-col>
      <v-col class="v-col-9">
        <div>
          <v-file-input ref="fileInput" :rules="fileRules" accept="image/png, image/jpg, image/jpeg"
            v-model="selectedImage"></v-file-input>
          <v-btn rounded="lg" @click="openFileInput">변경</v-btn>
          <!-- v-if 적용 후 이미지가 등록되어 있을 때만 삭제 버튼 노출 -->
          <v-btn class="ma-2" rounded="lg">삭제</v-btn>
        </div>
        <h6 class="text-grey">확장자: png, jpg, jpeg / 용량: 1MB 이하</h6>

      </v-col>
    </v-row>

    <h3 class="mt-5">닉네임</h3>
    <h6 class="text-grey">한글 2~8자 이하</h6>
    <v-text-field label="닉네임" :rules="nicknameRules" v-model="userStore.nickname"></v-text-field>

  </div>
</template>

<style scoped></style>