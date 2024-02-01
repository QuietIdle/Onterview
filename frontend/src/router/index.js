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
      path: '/community',
      name: 'community',
      component: () => import('@/views/CommunityView.vue'),
      meta: { layout: 'main' },
      redirect: { name: "community-list" },
      children: [
        {
          path: "list",
          name: "community-list",
          component: () => import('@/components/community/CommunityList.vue')
        },
        {
          path: "detail/:articleId",
          name: "community-detail",
          component: () => import('@/components/community/CommunityDetail.vue')
        }
      ]
    },
  ]
})

export default router




