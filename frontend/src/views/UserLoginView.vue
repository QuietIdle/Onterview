<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()
const email = ref('')
const password = ref('')

const toRegister = function () {
  router.push({ name: 'register' })
}

const emailRules = [
  (value) => {
    if (value) {
      return true
    } else {
      return '이메일을 입력해주세요.'
    }
  },
  (value) => {
    if (/.+@.+\..+/.test(value)) {
      return true
    } else {
      return '이메일 형식이 올바르지 않습니다.'
    }
  },
]

const passwordRules = [
  (value) => {
    if (value) {
      return true
    } else {
      return '비밀번호를 입력해주세요.'
    }
  },
]

const requestLogin = function () {

  for (const rule of emailRules) {
    const validationResult = rule(email.value)

    if (validationResult !== true) {
      alert(validationResult)
      return
    }
  }

  for (const rule of passwordRules) {
    const validationResult = rule(password.value)

    if (validationResult !== true) {
      alert(validationResult)
      return
    }
  }

  const payload = {
    email: email.value,
    password: password.value
  }

  userStore.requestLogin(payload)
}

</script>

<template>
  <div class="body d-flex">
    <div class="v-col-5 bg-indigo"></div>
    <div class="offset-1 v-col-4 mt-15">
      <div class="text-center my-10">
        <img class="mb-5" src="@/assets/logo.png" width="200px">
        <h2 class="mb-2">로그인</h2>
        <h4 class="text-grey">이메일과 비밀번호를 입력해주세요.</h4>
      </div>

      <v-sheet width="90%" class="mx-auto">
        <v-form ref="formRef" fast-fail @submit.prevent="requestLogin">

          <label for="email">이메일</label>
          <v-text-field v-model="email" label="example@onterview.com" id="email"></v-text-field>

          <label for="password">비밀번호</label>
          <v-text-field v-model="password" label="비밀번호" type="password" id="password"></v-text-field>

          <v-btn type="submit" block class="mt-2 py-5">
            <h3>로그인</h3>
          </v-btn>
        </v-form>

        <!-- <v-btn type="submit" block class="kakao mt-5 py-5">
          <div class="d-flex" justify="center">
            <img src="@/assets/kakao-logo.png" width="20px">
            <h3 class="mx-2">카카오 로그인</h3>
          </div>
        </v-btn> -->

        <p class="text-center mt-5">
          <!-- 온터뷰가 처음인가요? -->
          <!-- <v-btn variant="text"> 비밀번호 찾기 </v-btn>
          <span class="text-grey">|</span> -->
          <v-btn variant="text" @click="toRegister" style="color: #BB66FF">
            <h3> 회원가입 </h3>
          </v-btn>
        </p>

      </v-sheet>

    </div>
  </div>
</template>

<style scoped>
html {
  height: 100%;
}

a {
  color: #BB66FF;
  text-decoration: none;
}

.body {
  min-height: 100vh;
}

.kakao {
  background-color: #FEE500;
  color: #191919;
}
</style>