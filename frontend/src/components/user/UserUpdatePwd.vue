<script setup>
import { ref } from 'vue'
import { useUserStore } from '@/stores/user.js'

const userStore = useUserStore()
const original_password = ref(null)
const password = ref(null)
const confirm = ref(null)


const requestPwdChange = function () {

  const payload = {
    original_password: original_password.value,
    password: password.value,
    confirm: confirm.value
  }


}
</script>

<template>
  <div class="profile">
    <v-sheet width="90%" class="mx-auto my-10">
      <h3 class="mb-5">비밀번호 변경</h3>
      <v-form ref="formRef" fast-fail @submit.prevent="requestPwdChange">
        <v-text-field v-model="original_password" label="현재 비밀번호"></v-text-field>
        <v-text-field v-model="password" label="새 비밀번호"></v-text-field>
        <v-text-field v-model="confirm" label="새 비밀번호 확인"></v-text-field>
        <div class="d-flex justify-center">
          <v-btn type="submit" class="d-flex justify-center mt-2 px-15" color="success">
            <h3>비밀번호 변경</h3>
          </v-btn>
        </div>
      </v-form>
    </v-sheet>
  </div>
</template>

<style scoped></style>