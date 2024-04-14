<template>
  <div class="q-pa-md row justify-center" v-if="gotData == true">
    <div class="col-12 col-md-6">
      <!-- <div class="coursetitle text-h6 q-pl-sm q-my-lg">{{ this.courseTitle }}</div> -->
      <q-list bordered class="rounded-borders bg-grey-3">
        <q-expansion-item
          v-for="(main, index) in category" :key="index"
          expand-separator
          :label="main[0].maincategory"
          default-opened
        >
        <q-list separator class="bg-white">
          <q-item v-ripple v-for="(middle, index) in main.slice(1)" :key="index">
            <q-item-section class="q-pl-md">{{ middle.middlecategory }}</q-item-section>
            <q-item-section side top v-if="email == $route.params.email">
              <q-btn 
                v-if="middle.pid == 0"
                label="강의노트 작성" 
                type="submit" 
                color="deep-purple-8" 
                class="text-white text-caption" 
                v-close-popup 
                style="font-family: 'Noto Sans KR', sans-serif;"
                @click="note(middle)"/>
              <q-btn 
                v-else
                label="강의노트 보기" 
                type="submit" 
                color="white" 
                class="text-deep-purple-8 text-caption" 
                v-close-popup 
                style="font-family: 'Noto Sans KR', sans-serif;"
                @click="note(middle)"/>
            </q-item-section>
            <q-item-section side top v-else>
              <q-btn 
                v-if="middle.pid !== 0"
                label="강의노트 구경" 
                type="submit" 
                color="deep-purple-8" 
                class="text-white text-caption" 
                v-close-popup 
                style="font-family: 'Noto Sans KR', sans-serif;"
                @click="note(middle)"/>
            </q-item-section>
          </q-item>

        </q-list>

        </q-expansion-item>
        
      </q-list>
    </div>
  </div>
</template>
<script>
import axios from 'axios'

export default {
  name: 'ClassIndex',
  data() {
    return {
      courseTitle: '',
      category: [],
      gotData: false
    }
  },
  computed: {
    email() {
      return this.$store.getters.email
    }
  },
  methods: {
    note(info) {
      if (info.pid == 0) {
        this.$router.push({name: 'WritePost', query: {main: `${info.middlecategory}`, course: this.courseTitle}, params: {clid: info.clid}})
      } else {
        this.$router.push(`/detailpost/${info.pid}`)
      }
    } 
  },
  created() {
    const email = this.$route.params.email
    const gid = this.$route.params.gid
    this.courseTitle = this.$route.params.coursename

    axios.get(`http://3.36.125.70:8080/course/courseList?email=${email}&gid=${gid}`)
      .then((response) => {
        this.category = response.data
        this.gotData = true
      })
      .catch((error) => {
        console.log(error)
      })
  }
  
}
</script>
<style>
.coursetitle {
  border-left: solid 5px #512da8;
  font-family: 'Noto Sans KR', sans-serif;
}
  
</style>