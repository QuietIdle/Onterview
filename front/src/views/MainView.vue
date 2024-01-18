<script setup>
import introduceImage1 from '@/assets/main/introduceImage1.png'
import introduceImage2 from '@/assets/main/introduceImage2.png'
import introduceImage3 from '@/assets/main/introduceImage3.png'
</script>

<template>
  <div>
    <v-carousel hide-delimiters>
      <v-carousel-item :src="introduceImage1" cover></v-carousel-item>

      <v-carousel-item :src="introduceImage2" cover></v-carousel-item>

      <v-carousel-item :src="introduceImage3" cover></v-carousel-item>
    </v-carousel>
  </div>
</template>

<style scoped></style>
