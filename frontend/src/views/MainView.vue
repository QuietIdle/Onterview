<script setup>
import introduceImage1 from '@/assets/main/introduceImage1.png'
import introduceImage2 from '@/assets/main/introduceImage2.png'
import introduceImage3 from '@/assets/main/introduceImage3.png'

const images = [introduceImage1, introduceImage2, introduceImage3]
const titles = ['Onterview', '셀프 스피치', '모의 면접']
const contents = [
  '우리의 서비스는 탁월하다',
  '혼자서도 면접 경험을 쌓자',
  '여럿이서 면접 연습 경험도 쌓아보자'
]
</script>

<template>
  <div>
    <v-carousel cycle height="94vh" hide-delimiter-background show-arrows="hover">
      <v-carousel-item v-for="(image, i) in images" :key="i" :src="image" cover>
        <v-sheet
          height="100%"
          style="background: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5))"
        >
          <div class="d-flex fill-height flex-column justify-center" style="margin-left: 10%">
            <div class="text-h3 text-white">{{ titles[i] }}</div>
            <div class="text-p text-white mt-3">{{ contents[i] }}</div>
          </div>
        </v-sheet>
      </v-carousel-item>
    </v-carousel>
  </div>
</template>

<style scoped></style>
