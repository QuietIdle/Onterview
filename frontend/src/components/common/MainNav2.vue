<script setup>
import 'vue3-dropdown-navbar/preflight.css'
import {
  TheDropDownNavbar,
  TheDropDownMenu,
  TheDropDownItem,
  TheDropDownDivideBlock,
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
    <TheDropDownItem>셀프 스피치</TheDropDownItem>
    <TheDropDownItem>모의 면접</TheDropDownItem>
    <TheDropDownItem>커뮤니티</TheDropDownItem>
    <TheDropDownMenu text="보관함" ref="dropdownMenu">
      <TheDropDownItem>면접 문항 목록</TheDropDownItem>
      <TheDropDownMenu text="녹화 영상 목록">
        <TheDropDownItem>셀프 스피치</TheDropDownItem>
        <TheDropDownItem>1인 모의 면접</TheDropDownItem>
        <TheDropDownItem>다인 모의 면접</TheDropDownItem>
      </TheDropDownMenu>
    </TheDropDownMenu>
    <TheDropDownMenu text="마이페이지" ref="dropdownMenu">
      <TheDropDownItem>내 정보 수정</TheDropDownItem>
      <TheDropDownDivideBlock>
        <TheDropDownItem>Sign out</TheDropDownItem>
      </TheDropDownDivideBlock>
    </TheDropDownMenu>
  </TheDropDownNavbar>
</template>

<style scoped>
.logo {
  margin-left: 20%;
}
</style>
