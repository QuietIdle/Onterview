<script setup>
import { ref, watch } from 'vue';

const remainingTime = ref(60);

const updateRemainingTime = () => {
  if (remainingTime.value > 0) {
    remainingTime.value--;
  }
};

watch(remainingTime, () => {
  const elapsedPercentage = ((60 - remainingTime.value) / 60) * 100;
  const offset = (292.5 * elapsedPercentage) / 100;
  document.documentElement.style.setProperty('--offset', `${offset}px`);
});

setInterval(updateRemainingTime, 1000);
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
    <span class="base-timer__label">
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
  stroke: #BB66FF;
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
  color: black;
}
</style>
