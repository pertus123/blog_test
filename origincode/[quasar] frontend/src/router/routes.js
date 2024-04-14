import Signup from '../pages/user/Signup.vue'
import UserInfo from '../pages/user/UserInfo.vue'
import Profile from '../pages/user/Profile.vue'
import Login from '../pages/user/Login.vue'
import Index from '../pages/Index.vue'
import WritePost from '../pages/post/WritePost.vue'
import DetailPost from '../pages/post/DetailPost.vue'
import UpdatePost from '../pages/post/UpdatePost.vue'
import SearchedPost from '../pages/post/SearchedPost.vue'
import SearchedTag from '../pages/post/SearchedTag.vue'
import TempPostList from '../pages/tempPost/TempPostList.vue'
import ClassIndex from '../pages/class/ClassIndex'

// 인증되지 않은 사용자는 로그인 페이지로 보내기
const requireAuth = (to, from, next) => {
  if (localStorage.getItem('auth-token')) return next()
  next({
    path: "/login",
    query: { redirect: to.fullPath },
    // 로그인 후 기존에 가려고 했던 경로로 이동하도록 코드 수정 요망
  })
}


const routes = [
  {
    path: '/',
    component: Index,
  },
  {
    path: '/signup',
    component: Signup,
  },
  {
    path: '/userinfo',
    component: UserInfo,
    beforeEnter: requireAuth
  },
  {
    path: '/profile/:email/:tab',
    component: Profile,
  },
  {
    path: '/login',
    component: Login,
  },
  {
    path: '/writepost',
    name: 'WritePost',
    component: WritePost,
    beforeEnter: requireAuth
  },
  {
    path: '/detailpost/:id',
    component: DetailPost,
  },
  {
    path: '/updatepost/:id',
    name: 'UpdatePost',
    component: UpdatePost,
    beforeEnter: requireAuth
  },
  {
    path: '/searchedpost/:search',
    component: SearchedPost,
  },
  {
    path: '/searchedtag/:search',
    name: 'SearchedTag',
    component: SearchedTag,
  },
  {
    path: '/temppostlist/:email',
    component: TempPostList,
    beforeEnter: requireAuth
  }, 
  {
    path: '/classindex/:email/:coursename/:gid',
    name: 'ClassIndex',
    component: ClassIndex
  },
 
  



  // Always leave this as last one,
  // but you can also remove it
  {
    path: '*',
    component: () => import('pages/Error404.vue')
  }
]

export default routes
