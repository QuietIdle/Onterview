<script setup>
import { useRouter } from 'vue-router'
import { ref } from 'vue'
import { deleteDeleteUser } from '@/api/user.js'

const router = useRouter()
const password = ref(null)

const requestDeleteUser = function () {
  const payload = {
    password: password.value
  }

  const success = function () {
    password.value = null
    router.push({ name: "home" })
  }

  const error = function () {
    alert('비밀번호가 옳지 않습니다.')
    password.value = null
  }

  deleteDeleteUser(payload, success, error)
}
</script>

<template>
  <div class="profile">
    <v-sheet width="90%" class="mx-auto my-10">
      <h3 class="mb-5">회원 탈퇴</h3>
      <p>탈퇴 시 직접 작성한 콘텐츠(동영상, 답변, 자가 진단 등)가 자동으로 삭제됩니다.</p>
      <p>탈퇴 후 해당 데이터는 복구할 수 없습니다.</p>
      <p>그래도 탈퇴를 희망하시면, 아래에 비밀번호를 입력해주세요.</p>
      <v-form class="mt-5" ref="formRef" fast-fail @submit.prevent="requestDeleteUser">
        <v-text-field v-model="password" type="password" label="비밀번호"></v-text-field>
        <div class="d-flex justify-center">
          <v-btn type="submit" class="d-flex justify-center mt-2 px-15" color="red">
            <h3>회원 탈퇴</h3>
          </v-btn>
        </div>
      </v-form>
    </v-sheet>
  </div>
</template>

<style scoped></style>