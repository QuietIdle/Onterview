<script setup>
import { ref, watch, onUnmounted } from 'vue'

const maxTime = 60
const remainingTime = ref(maxTime)
const timerId = ref(null)

const props = defineProps({
  startTimer: Boolean
})

watch(() => props.startTimer, (newVal) => {
  if (newVal === true) {
    startTimer()
  } else {
    resetTimer()
  }
})

const updateRemainingTime = function () {
  if (remainingTime.value > 0) {
    remainingTime.value--
  }
}

watch(remainingTime, (newVal) => {
  const elapsedPercentage = ((maxTime - remainingTime.value) / maxTime) * 100
  const offset = (292.5 * elapsedPercentage) / 100
  document.documentElement.style.setProperty('--offset', `${offset}px`)

  if (newVal === 0) {
    resetTimer()
  }
})

const startTimer = function () {
  if (timerId.value === null) {
    console.log("?")
    timerId.value = setInterval(updateRemainingTime, 1000)
  }
}

const resetTimer = function () {
  clearInterval(timerId.value)
  timerId.value = null
  remainingTime.value = maxTime
  // 그래프 스타일을 초기값으로 설정
  document.documentElement.style.setProperty('--offset', `292.5px`)
}

// 컴포넌트가 언마운트되면 타이머를 정리
onUnmounted(() => {
  resetTimer()
})
</script>

<template>
  <div class="base-timer">
    <svg class="base-timer__svg" viewBox="0 0 100 100" xmlns="http://www.w3.org/2000/svg">
      <g class="base-timer__circle" transform="rotate(-90 50 50)">
        <circle class="base-timer__path-background" cx="50" cy="50" r="46.5" />
        <circle class="base-timer__path-elapsed" cx="50" cy="50" r="46.5"
          :style="{ strokeDasharray: '292.5px', strokeDashoffset: 'var(--offset)' }" />
      </g>
    </svg>
    <span class="base-timer__label text-h4">
      {{ remainingTime }}
    </span>
  </div>
</template>

<style scoped>
.base-timer {
  position: relative;
  width: 300px;
  height: 300px;
}

.base-timer__circle {
  fill: none;
  stroke: none;
}

.base-timer__path-background {
  stroke-width: 7px;
  stroke: #ffffff;
  fill: none;
}

.base-timer__path-elapsed {
  stroke-width: 3px;
  stroke: #8747AE;
  fill: #ffffff;
  transition: stroke-dashoffset 1s linear;
  transform-origin: center;
  transform-box: fill-box;
}

.base-timer__label {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 20px;
  font-weight: bold;
  color: #8747AE;
}
</style>
