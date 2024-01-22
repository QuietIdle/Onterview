<script setup>
// import 'vue3-dropdown-navbar/preflight.css'
import {
  TheDropDownNavbar,
  TheDropDownMenu,
  TheDropDownItem,
  TheDropDownNavbarLogo
} from 'vue3-dropdown-navbar'
import { ref } from 'vue'

import logo from '@/assets/logo.png'

const dropdownMenu = ref(null)
</script>

<template>
  <TheDropDownNavbar>
    <template #logo>
      <TheDropDownNavbarLogo>
        <RouterLink to="/">
          <v-img class="logo" :width="150" aspect-ratio="16/9" cover :src="logo"></v-img>
        </RouterLink>
      </TheDropDownNavbarLogo>
    </template>
    <TheDropDownItem>
      <RouterLink to="/selfspeech"> 셀프 스피치 </RouterLink>
    </TheDropDownItem>
    <TheDropDownItem>
      <RouterLink to="/"> 모의 면접 </RouterLink>
    </TheDropDownItem>
    <TheDropDownItem>
      <RouterLink to="/"> 커뮤니티 </RouterLink>
    </TheDropDownItem>
    <TheDropDownMenu text="보관함" ref="dropdownMenu">
      <TheDropDownItem>
        <RouterLink to="/storage/question"> 면접 문항 목록 </RouterLink>
      </TheDropDownItem>
      <TheDropDownMenu text="녹화 영상 목록">
        <TheDropDownItem>
          <RouterLink to="/storage/video"> 셀프 스피치 </RouterLink>
        </TheDropDownItem>
        <TheDropDownItem>
          <RouterLink to="/"> 1인 모의 면접 </RouterLink>
        </TheDropDownItem>
        <TheDropDownItem>
          <RouterLink to="/"> 다인 모의 면접 </RouterLink>
        </TheDropDownItem>
      </TheDropDownMenu>
    </TheDropDownMenu>
    <v-btn variant="tonal" color="deep-purple-accent-4">
      <RouterLink to="/login"> Login </RouterLink>
    </v-btn>
    <TheDropDownMenu text="마이페이지" ref="dropdownMenu">
      <v-list>
        <v-list-item :prepend-avatar="logo" title="관리자님" subtitle="환영합니다"> </v-list-item>
      </v-list>
      <TheDropDownItem>나의 정보 수정 </TheDropDownItem>
      <TheDropDownItem>Sign out</TheDropDownItem>
    </TheDropDownMenu>

    <div class="mr-4"></div>
  </TheDropDownNavbar>
</template>

<style>
.logo {
  margin-left: 20%;
}

ul {
  list-style-type: none; /* 불릿 제거 */
  /* padding: 0; 일부 브라우저에서 기본적으로 적용된 패딩 제거 */
}

a {
  text-decoration: none; /* 링크 밑줄 제거 */
  color: black; /* 링크 색상, 필요에 따라 조정 */
}

.dd-nav-p-4 {
  padding: 0.5rem !important;
}
</style>
