<script setup>
import logo from '@/assets/logo.png'
import { useRouter } from 'vue-router'
import { RouterLink } from 'vue-router'

const router = useRouter()

const toLoginPage = function () {
  router.push({ name: "login" })
}
</script>

<template>
  <div>
    <v-card>
      <v-row>
        <!-- 이미지 열 -->

        <v-col cols="12" md="3" align-self="center">
          <RouterLink to="/">
            <v-img class="logo" :width="150" aspect-ratio="16/9" cover :src="logo"></v-img>
          </RouterLink>
        </v-col>

        <!-- 탭 열 -->
        <v-col cols="12" md="6" align-self="end">
          <v-tabs color="deep-purple-accent-4" align-tabs="center">
            <RouterLink to="/selfspeech">
              <v-tab :value="1">셀프 스피치</v-tab>
            </RouterLink>
            <v-tab :value="2">모의 면접</v-tab>
            <v-tab :value="3">보관함</v-tab>
            <v-tab :value="4">커뮤니티</v-tab>
          </v-tabs>
        </v-col>

        <v-col cols="12" md="3" align-self="center" class="d-flex justify-center">
          <!-- 로그인 -->
          <v-btn variant="tonal" color="deep-purple-accent-4" @click="toLoginPage">Login</v-btn>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<style scoped>
.v-tab.v-tab.v-btn {
  font-size: large;
}

.logo {
  margin: 1%;
  margin-left: 20%;
}
</style>
