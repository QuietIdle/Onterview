import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: () => import('@/views/MainView.vue'),
      meta: {layout: 'main'},
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/UserLoginView.vue'),
      meta: {layout: 'main'},
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/UserRegisterView.vue'),
      meta: {layout: 'main'},
    },
    {
      path: '/selfspeech',
      name: 'selfspeech-main',
      component: () => import('@/views/SelfSpeechMainView.vue'),
      meta: {layout: 'main'},
    },
    {
      path: '/selfspeech/room',
      name: 'selfspeech-room',
      component: () => import('@/views/SelfSpeechRoomView.vue'),
      meta: {layout: ''},
    },
    {
      path: '/storage/question',
      name: 'storage-question',
      component: () => import('@/views/StorageQuestionView.vue'),
      meta: {layout: 'main'},
    },
    {
      path: '/storage/video',
      name: 'storage-video',
      component: () => import('@/views/StorageVideoView.vue'),
      meta: {layout: 'main'},
    },
  ]
})

export default router




