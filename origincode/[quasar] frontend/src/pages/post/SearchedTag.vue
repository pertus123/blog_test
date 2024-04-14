<template>
  <div class="q-pa-md">
    <q-infinite-scroll @load="onLoad" :offset="550">
    
    <!-- <div class="text-center text-h5" style="font-family: 'Noto Serif KR', serif;">#{{this.$route.params.search}} 검색결과</div> -->
    <div class="row justify-center q-gutter-sm">
      <div
        v-for="(loadedItem, index) in loadedItems"
        :key="index"
        class="q-pa-sm caption flex flex-center"
      >
        <router-link :to="`/detailpost/${loadedItem.pid}`" style="text-decoration: none; color:black">
        <q-card class="my-card scale">
          <img v-if="loadedItem.thumbimg !== null" :src="loadedItem.thumbimg" width="300px" height="150px">

          <img v-else src="https://climate.onep.go.th/wp-content/uploads/2020/01/default-image.png" width="300px" height="150px">
          <div>
            <q-badge color="deep-purple-8" floating>
              <div class="tagLimit">#{{ search }}</div>
            </q-badge>
          </div>

          <div class="card-content">
          <q-card-section>
            <div class="text-subtitle1 text-bold titleLimit">{{ loadedItem.title }}</div>
          </q-card-section>

          <q-card-section class="q-pt-none">
            <div class="text-body2 contentLimit">{{ loadedItem.content }}</div>
            <div class="flex text-grey-7 q-pt-sm">
              <div class="text-caption q-pr-sm">{{ loadedItem.create_date }}</div>
              <div v-if="loadedItem.commentCnt > 0" class="text-caption">· 댓글 {{ loadedItem.commentCnt }}개</div>
            </div>
          </q-card-section>
          </div>
          
          <q-separator/>

          <q-card-actions class="flex justify-between">
            <div class="flex items-center">
              <q-avatar size="sm">
                <img v-if="loadedItem.profileimage !== null" :src="loadedItem.profileimage"> 
                <i v-else class="fas fa-user-circle fa-2x"></i>
              </q-avatar>
              <div class="text-caption q-pl-sm">by {{ loadedItem.email }}</div>
            </div>
             <div class="flex items-center">
              <q-icon name="favorite" v-if="loadedItem.isLike == 1" color="red"/>
              <q-icon name="favorite" v-else color="grey-7"/>
              <div v-if="loadedItem.likes > 0" class="text-caption text-grey-7">{{ loadedItem.likes }}</div>
            </div>
          </q-card-actions>
          
        </q-card>
        </router-link>
      </div>
    </div>

    <template v-slot:loading>
      <div class="row justify-center q-my-md">
        <q-spinner-dots color="deep-purple-8" size="40px" />
      </div>
    </template>

  </q-infinite-scroll>
  </div>
</template>


<script>
import axios from 'axios'
import removeMd from 'remove-markdown'
import moment from 'moment'



export default {
  name: 'SearchedTag',
  data () {
    return {
      items: [],
      itemsNum: 0,
      loadedItems: [],
      search: '',
    }
  },
  computed: {
    uid () {
      return this.$store.getters.uid
    },
  },
  methods: {
    onLoad (index, done) {
      const search = this.$route.params.search;
      axios.get(`http://3.36.125.70:8080/label/pageByLabel?tagName=${search}&uid=${this.uid}`)
        .then(() => {
          setTimeout(() => {
            if (this.items.length > 4) {
              
              this.loadedItems.push(this.items.pop(), this.items.pop(), this.items.pop(), this.items.pop())
              done()
            } else {
              for (var i = 0; i < this.items.length; i++) {
                this.loadedItems.push(this.items[this.items.length-1-i])
              }
              done(true)
            }
          }, 100)

        })
        .catch((error) => {
          console.log(error)
        })
    },
    modifyItems() {
      for (var index in this.items) {
        const content = this.items[index].content
        const plainText = removeMd(content).split('\n').join(' ');
        this.items[index].content = plainText
        const date = moment(this.items[index].create_date).locale('ko').format('llll');
        this.items[index].create_date = date

      } 
    }
  },
  created() {
    const search = this.$route.params.search;
    const data = this.$route.params.data;
    this.search = search
    
    if (data) {
      this.items = data
      this.itemsNum = data.length
      this.modifyItems()
    } else {
      axios.get(`http://3.36.125.70:8080/label/pageByLabel?tagName=${search}&uid=${this.uid}`)
        .then((response) => {
          console.log(response)
          this.items = response.data
          this.itemsNum = response.data.length
          this.modifyItems()

        })
        .catch((error) => {
          console.log(error)
        })
      }
  },
 

}
</script>
<style lang="sass" scoped>
.my-card
  width: 100%
  max-width: 300px

.card-content
  height: 160px

.titleLimit 
  max-width: 280px
  padding-right: 5px
  overflow: hidden
  text-overflow: ellipsis
  white-space: nowrap

.tagLimit 
  max-width: 280px
  padding-right: 5px
  overflow: hidden
  text-overflow: ellipsis
  white-space: nowrap

.contentLimit 
  overflow: hidden
  text-overflow: ellipsis
  display: -webkit-box
  -webkit-line-clamp: 3
  -webkit-box-orient: vertical
  word-wrap:break-word
  line-height: 1.4em
  height: 4.2em

.scale 
  transform: scale(1)
  -webkit-transform: scale(1)
  -moz-transform: scale(1)
  -ms-transform: scale(1)
  -o-transform: scale(1)
  transition: all 0.3s ease-in-out  

.scale:hover 
  transform: scale(1.03)
  -webkit-transform: scale(1.03)
  -moz-transform: scale(1.03)
  -ms-transform: scale(1.03)
  -o-transform: scale(1.03)
  
</style>












