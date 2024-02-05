import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'


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
      beforeEnter: ((to, from) => {
        const userStore = useUserStore()
        if (userStore.accessToken !== null) {
          console.log(`로그인 중`)
          return { name: 'main' }
        }
      }),
      meta: {layout: 'main'},
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/UserRegisterView.vue'),
      beforeEnter: ((to, from) => {
        const userStore = useUserStore()
        if (userStore.accessToken !== null) {
          console.log(`로그인 중`)
          return { name: 'main' }
        }
      }),
      meta: {layout: 'main'},
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: () => import('@/views/UserMyPageView.vue'),
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
    {
      path: '/meeting',
      name: 'meeting-main',
      component: () => import('@/views/MeetingMain.vue'),
      meta: {layout: 'main'},
    },
    {
      path: '/meeting/multiRoom',
      name: 'meeting-multi-room',
      component: () => import('@/views/MeetingMultiRoom.vue'),
      meta: {layout: ''},
    },
  ]
})

router.beforeEach((to, from) => {
  const userStore = useUserStore()

  if (userStore.accessToken === null) {
    if (to.name === 'mypage' || to.name === 'selfspeech-room' || to.name === 'storage-question' || to.name === 'storage-video') {
      alert('로그인이 필요합니다!')
      return { name: 'login' }
    }
  }
})

export default router