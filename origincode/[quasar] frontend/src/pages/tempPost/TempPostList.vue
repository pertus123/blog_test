<template>
  <div class="q-pa-md row justify-center" style="min-width: 400px">
    <div class="col-12 col-md-6">
      <div v-if="items.length > 0" >
        <q-list v-for="(item, index) in items" :key="index">
          <q-item>
            <q-item-section>
              <q-item-label lines="1">{{ item.title }}</q-item-label>
              <q-item-label class="q-pt-sm" caption lines="2">{{ item.content }}</q-item-label>
              <q-item-label class="q-pt-sm" caption>{{ item.create_date }}</q-item-label>
            </q-item-section>

            <q-item-section side top>
              <div class="row justify-end q-pb-sm">
                <q-btn 
                  label="수정" 
                  type="submit" 
                  color="deep-purple-8" 
                  class="text-white text-caption" 
                  v-close-popup dense 
                  @click="updateTempPost(item.tpid)"
                  style="font-family: 'Noto Sans KR', sans-serif;"/>
                <q-btn 
                  label="삭제" 
                  type="submit" 
                  color="white q-ml-sm" 
                  class="text-deep-purple-8 text-caption" 
                  v-close-popup dense 
                  @click="deleteTempPost(item.tpid)"
                  style="font-family: 'Noto Sans KR', sans-serif;"/>
              </div>
            </q-item-section>
          </q-item>
          <q-separator spaced inset />
        </q-list>
      </div>

      <div v-else>
        <div class="row justify-center">
          <q-card flat bordered class="q-pa-md bg-grey-1" style="width: 550px">
            <q-card-section>
              <div class="text-center">
                {{this.$route.params.email }}님의 임시 저장글이 없습니다.
              </div>
            </q-card-section>
            
          </q-card>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import removeMd from 'remove-markdown'
import moment from 'moment'

export default {
  data () {
    return {
      items: [],
      reload: false,
    }
  },
  methods: {
    deleteTempPost(tpid) {
      axios.delete(`http://3.36.125.70:8080/temp/delete/${tpid}`)
        .then((response) => {
          this.reload = true
        })
        .catch((error) => {
          console.log(error)
        })
    },
    updateTempPost(tpid) {
      this.$router.push({name: 'WritePost', query: {tpid: tpid}})
    },
    modifyItems() {
      for (var index in this.items) {
        const content = this.items[index].content
        var plainText = removeMd(content).split('\n').join(' ');
        plainText = plainText.split('desc{{{width="auto" height="auto"}}}').join(' ')
        this.items[index].content = plainText
        const date = moment(this.items[index].create_date).locale('ko').format('llll');
        this.items[index].create_date = date
      } 
    },
  },
  created() {
    const email = this.$route.params.email 
    axios.get(`http://3.36.125.70:8080/temp/tempListPage?email=${email}`)
      .then((response) => {
        this.items = response.data.reverse()
        this.modifyItems()
      })
      .catch((error) => {
        console.log(error)
      })
  },
  watch: {
    reload: function() {
      this.reload = false
      const email = this.$route.params.email 
      axios.get(`http://3.36.125.70:8080/temp/tempListPage?email=${email}`)
        .then((response) => {
          this.items = response.data.reverse()
          this.modifyItems()
        })
        .catch((error) => {
          console.log(error)
        })
    }
  }
}
</script>
<style lang="">
  
</style>
