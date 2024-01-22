<script setup>
import { ref } from 'vue'
import { useUserStore } from '@/stores/user.js'
import { patchChangeUserPwd } from '@/api/user.js'

const userStore = useUserStore()
const original = ref(null)
const password = ref(null)
const confirm = ref(null)
const dialog = ref(false)

const closeDialog = function () {
  dialog.value = false
}

const requestUserPwdChange = function () {

  const payload = {
    original: original.value,
    password: password.value,
    confirm: confirm.value
  }

  const success = function (response) {
    // 모달 - 비밀번호 변경을 완료했습니다.
    dialog.value = true

    // 입력되어 있는 데이터 지우기
    original.value = null
    password.value = null
    confirm.value = null
  }

  const error = function (response) {
    // 리턴되는 값에 따라 적용
  }

  patchChangeUserPwd(payload, success, error)
}
</script>

<template>
  <div class="profile">
    <v-sheet width="90%" class="mx-auto my-10">
      <h3 class="mb-5">비밀번호 변경</h3>
      <v-form ref="formRef" fast-fail @submit.prevent="requestUserPwdChange">
        <v-text-field v-model="original" label="현재 비밀번호"></v-text-field>
        <v-text-field v-model="password" label="새 비밀번호"></v-text-field>
        <v-text-field v-model="confirm" label="새 비밀번호 확인"></v-text-field>
        <div class="d-flex justify-center">
          <v-btn type="submit" class="d-flex justify-center mt-2 px-15" color="success">
            <h3>비밀번호 변경</h3>
          </v-btn>
        </div>
      </v-form>
    </v-sheet>

    <v-dialog v-model="dialog" width="500">
      <template v-slot:default="{ dialog }">
        <v-card title="Dialog">
          <v-card-text>비밀번호 변경을 완료했습니다.</v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>

            <v-btn text="닫기" @click="closeDialog"></v-btn>
          </v-card-actions>
        </v-card>
      </template>
    </v-dialog>
  </div>
</template>

<style scoped></style>