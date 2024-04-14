<template>
  <div class="q-pa-md header">
    <q-toolbar class="bg-deep-purple-8 text-white shadow-2 rounded-borders" >
        <q-btn stretch flat class="text-h6 text-lowercase" to="/">
          <i class="far fa-chart-bar fa-1x q-pr-sm"></i>
          <div>stat</div>
          <div class="text-weight-bold q-pl-sm">overflow</div>
        </q-btn>

      <q-space />
      <q-input dark dense standout v-model="search" input-class="text-right" class="q-ml-md gt-sm" @keypress.enter="searchPost">
        <template v-slot:append >
            <q-btn flat round dense icon="search" class="q-mr-xs" @click="searchPost"/>
        </template>
      </q-input>
     
        <!-- 화면이 sm보다 큰 경우 -->
      <q-tabs v-model="tab" shrink >
        <div class="gt-xs flex">
       
        <div v-if="this.$store.state.isLogin == true" class="flex">
          <router-link class="tabs" to="/writepost"><q-tab name="글쓰기" label="글쓰기" /></router-link>
          <q-btn flat class="items-center">
            <q-avatar size="md">
              <img v-if="profileimage !== null" :src="profileimage"> 
              <i v-else class="fas fa-user-circle fa-2x"></i>
            </q-avatar>
            <div class="q-pa-sm">▾</div>
            <q-menu>
              <q-list style="min-width: 120px">
                <q-item clickable v-close-popup>
                  <q-item-section>
                    <router-link style="text-decoration: none; color:black;" to="/userinfo">회원정보 수정</router-link>
                  </q-item-section>
                </q-item>
                <q-item clickable v-close-popup>
                  <q-item-section>
                    <router-link style="text-decoration: none; color:black;" :to="`/temppostlist/${email}`">임시 글</router-link>
                  </q-item-section>
                </q-item>
                <q-item clickable v-close-popup>
                  <q-item-section @click="goProfile">
                    내 프로필
                  </q-item-section>
                </q-item>
                <q-item clickable v-close-popup>
                  <q-item-section @click="logout">로그아웃</q-item-section>
                </q-item>
              </q-list>
            </q-menu>
          </q-btn>
        </div>

        <div v-else class="flex gt-sm">
          <router-link class="tabs" to="/login"><q-tab name="로그인" label="로그인" /></router-link>
          <router-link class="tabs" to="/signup"><q-tab name="회원가입" label="회원가입" /></router-link>
        </div>
        </div>

        <div class="xs flex">
          <div v-if="this.$store.state.isLogin == true">
            <q-btn flat round dense icon="menu" />
            <q-menu>
              <q-list style="min-width: 120px">
                <q-item clickable v-close-popup>
                  <q-item-section>
                    <router-link class="tabs" to="/writepost" style="text-decoration: none; color:black;" >글쓰기</router-link>
                  </q-item-section>
                </q-item>
                <q-item clickable v-close-popup>
                  <q-item-section @click="goProfile">
                    내 프로필
                  </q-item-section>
                </q-item>
                <q-item clickable v-close-popup>
                  <q-item-section>
                    <router-link style="text-decoration: none; color:black;" to="/userinfo">회원정보 수정</router-link>
                  </q-item-section>
                </q-item>
                <q-item clickable v-close-popup>
                  <q-item-section>
                    <router-link style="text-decoration: none; color:black;" :to="`/temppostlist/${email}`">임시 글</router-link>
                  </q-item-section>
                </q-item>
                <q-item clickable v-close-popup>
                  <q-item-section @click="logout">로그아웃</q-item-section>
                </q-item>
              </q-list>
            </q-menu>
          </div>
          <div v-else class="flex">
            <router-link class="tabs" to="/login"><q-tab name="로그인" label="로그인" /></router-link>
            <router-link class="tabs" to="/signup"><q-tab name="회원가입" label="회원가입" /></router-link>
          </div>

        </div>
        
      </q-tabs>
    </q-toolbar>
  </div>
</template>
<script>

export default {
  data () {
    return {
      location: window.location,
      tab: '',
      search: '',
      profileimage: null,
    }
  },
  computed: {
    profileimageChange () {
      return this.$store.getters.profileimage
    },
    email() {
      return this.$store.getters.email
    },
    isLogin() {
      return this.$store.getters.isLogin
    }
  },
  methods: {
    goProfile() {
      this.$router.push(`/profile/${this.email}/글`)
    },
    logout() {
      localStorage.removeItem('auth-token')
      this.$store.dispatch('logout')
      this.$router.push('/')
    },
    searchPost() {
      if (this.search !== "") {
        this.$router.push(`/searchedpost/${this.search}`).catch(error => {
          if(error.name === "NavigationDuplicated") {
            location.reload();
          }
        });
        this.search = ""
      }
    }
  },
  created() {
    if (this.$store.state.userInfo.profileimage !== null) {
      this.profileimage = this.$store.state.userInfo.profileimage
    }
  },
  watch: {
    profileimageChange: function() {
      this.profileimage = this.profileimageChange
    },
    $route(to, from) {
      if (this.isLogin == true && to.path !== '/writepost' && to.path != from.path) {
        this.tab = ''
      } 
    }
  },
 
}
</script>

<style scoped>
.header {
  font-family: 'Noto Sans KR', sans-serif;
  padding-bottom: 40px;
}
.tabs {
  color: white;
  text-decoration: none;
}
</style>