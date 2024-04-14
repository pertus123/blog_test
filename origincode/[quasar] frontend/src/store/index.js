import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'
import jwt_decode from "jwt-decode"


// import example from './module-example'

Vue.use(Vuex)

/*
 * If not building with SSR mode, you can
 * directly export the Store instantiation;
 *
 * The function below can be async too; either use
 * async/await or return a Promise which resolves
 * with the Store instance.
 */

export default function (/* { ssrContext } */) {
  const Store = new Vuex.Store({
    state: {
      isLogin: false, 
      userInfo: {
        uid: "",
        name: "",
        email: "",
        introduce: "",
        password: "",
        profileimage: null,
      },
    },
    getters: {
      profileimage: state => {
        return state.userInfo.profileimage
      },
      uid: state => {
        return state.userInfo.uid
      },
      email: state => {
        return state.userInfo.email
      },
      isLogin: state => {
        return state.isLogin
      }
    },
    mutations: {
      LOGIN: function (state, token) {
        let decode = jwt_decode(token)
        state.userInfo.name = decode.user.name
        state.userInfo.email = decode.user.email
        state.userInfo.uid = decode.user.uid
        state.userInfo.introduce = decode.user.introduce
        state.userInfo.profileimage = decode.user.profileimage
        state.isLogin = true
      },
      SEND_PASSWORD: function (state, password) {
        state.userInfo.password = password
      },
      LOGOUT: function (state) {
        state.userInfo.name = ""
        state.userInfo.email = ""
        state.userInfo.uid = ""
        state.userInfo.introduce = ""
        state.userInfo.password = ""
        state.userInfo.profileImg = null
        state.isLogin = false
      },
      SET_PROFILE_IMAGE: function(state, profileimage) {
        state.userInfo.profileimage = profileimage
      },
    },
    actions: {
      login: function ({ commit }, token) {
        commit('LOGIN', token)
      },
      sendPassword: function ({ commit }, password) {
        commit('SEND_PASSWORD', password)
      },
      logout: function ({ commit }) {
        commit('LOGOUT')
      },
      setProfileImage: function({ commit }, profileimage) {
        commit('SET_PROFILE_IMAGE', profileimage)
      },
    },

    plugins: [
      createPersistedState(),
    ],

    // enable strict mode (adds overhead!)
    // for dev mode only
    strict: process.env.DEBUGGING
  })
  return Store
}
