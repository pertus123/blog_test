<template>
  <div>
    <div v-for="comment in comments" :key="comment.cid">
      <q-separator/>
      
      <div class="flex q-pt-md justify-between">
        <div class="flex">
          <q-avatar @click="goProfile(comment.email)" size="md" class="q-mr-md goProfile">
            <img v-if="comment.profileimage !== null" :src="comment.profileimage"> 
            <i v-else class="fas fa-user-circle fa-2x"></i>
          </q-avatar>
          <div>
            <div class="text-bold goProfile" @click="goProfile(comment.email)">
              {{ comment.email }}
            </div>
            <div class="text-grey-7 text-caption">
              {{ comment.create_date }}
            </div>
            <div class="q-py-md" :id="`content-${comment.cid}`">
              {{ comment.content }}
            </div>
            
          </div>
        </div>
        <div v-if="comment.email == userEmail" class="text-grey-7 items-start">
          <q-btn flat dense label="수정" @click="showUpdateForm(comment.cid)"/>
          <q-btn flat dense label="삭제" @click="deleteComment(comment.cid)"/>
        </div>        

        <q-form  @submit="updateComment(comment.cid)" :id="`update-${comment.cid}`" style="display: none; width: 100%; margin-left: 48px">
          <q-input
            v-model="content"
            :value="comment.content"
            name="comment"
            color="deep-purple-8"
            filled
            :hint="error"
            class="q-pb-sm"
          />

          <div class="row justify-end q-pb-sm">
            <q-btn 
              label="댓글 수정" 
              type="submit" 
              color="deep-purple-8" 
              class="text-white text-caption" 
              v-close-popup dense 
              style="font-family: 'Noto Sans KR', sans-serif;"/>
            <q-btn family
              label="취소" 
              color="white q-ml-sm" 
              class="text-deep-purple-8 text-caption" 
              v-close-popup 
              dense
              @click="closeUpdateForm(comment.cid)"
              style="font-family: 'Noto Sans KR', sans-serif;"/>
          </div>
        </q-form>
      </div>

    </div>

  </div>
</template>
<script>
import axios from 'axios'
import moment from 'moment'


export default {
  props: [
    'createComment',
  ],
  data() {
    return {
      userEmail: this.$store.state.userInfo.email,
      error: '',
      content: '',
      comments: [],
      reload: false,
    }
  },
  methods: {
    goProfile(email) {
      this.$router.push(`/profile/${email}`)
    },
    deleteComment(cid) {
      const pid = this.$route.params.id
      axios.delete(`http://i4b103.p.ssafy.io:8080/cmt/delete?cid=${cid}&pid=${pid}`)
        .then(() => {
          this.reload = true
        })
        .catch((error) => {
          console.log(error)
        })
    
    },
    updateComment(cid) {
      if (this.content.length > 2) {
        const updateForm = document.getElementById(`update-${cid}`)
        updateForm.style.display = 'none'
        var vm = this
        this.comments.forEach( function(comment) {
          if (comment.cid == cid) {
            const updateComment = {
              cid: cid,
              pid: comment.pid,
              content: vm.content,
              email: comment.email,
            }
            axios.put('http://i4b103.p.ssafy.io:8080/cmt/update/', updateComment)
              .then(() => {
                vm.content = ''
                vm.error = ''
                vm.reload = true
                document.querySelector(`#content-${cid}`).style.display = 'block'
              })
              .catch((error) => {
                console.log(error)
              })
          }
        })
      } else {
        this.error = '댓글은 세 글자 이상 작성해주세요.'
      }
    },
    showUpdateForm(cid) {
      const updateForm = document.getElementById(`update-${cid}`)
      
      if (updateForm.style.display == 'none') {
        updateForm.style.display = 'block';
        document.querySelector(`#content-${cid}`).style.display = 'none'
      } else {
        updateForm.style.display = 'none';
        document.querySelector(`#content-${cid}`).style.display = 'block'
      }
    },
    closeUpdateForm(cid) {
      this.error = ''
      this.content = ''
      document.querySelector(`#content-${cid}`).style.display = 'block'
      const updateForm = document.getElementById(`update-${cid}`)
      updateForm.style.display = 'none';
    }
   
  
  },
  created() {
    const pid = this.$route.params.id
    axios.get(`http://i4b103.p.ssafy.io:8080/cmt/detail/${pid}`)
      .then((response) => {
        this.comments = response.data
        for (var index in this.comments) {
          const date = moment(this.comments[index].create_date).locale('ko').format('llll');
          this.comments[index].create_date = date
        } 
      })
      .catch((error) => {
        console.log(error)
      })
  },
  watch: {
    reload: function() {
      this.reload = false
      const pid = this.$route.params.id

      axios.get(`http://i4b103.p.ssafy.io:8080/cmt/detail/${pid}`)
      .then((response) => {
        this.comments = response.data
        for (var index in this.comments) {
          const date = moment(this.comments[index].create_date).locale('ko').format('llll');
          this.comments[index].create_date = date
        } 
      })
      .catch((error) => {
        console.log(error)
      })

    },
    createComment: function() {
      const pid = this.$route.params.id

      axios.get(`http://i4b103.p.ssafy.io:8080/cmt/detail/${pid}`)
        .then((response) => {
          this.comments = response.data
          for (var index in this.comments) {
            const date = moment(this.comments[index].create_date).locale('ko').format('llll');
            this.comments[index].create_date = date
          } 
        })
        .catch((error) => {
          console.log(error)
        })
      
    }
  }

}
</script>
<style scoped>
.goProfile {
  cursor: pointer;
}

  
</style>