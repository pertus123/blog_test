<template>
  <div class="q-pt-md q-pb-xl">
    <q-form @submit="submitComment" class="q-gutter-md">
      <q-input
        name="comment"
        v-model="content"
        color="deep-purple-8"
        placeholder="댓글을 작성하세요."
        filled
        :hint="error"
        clearable
      />

      <div class="row justify-end q-mt-none">
        <q-btn label="댓글 작성" type="submit" color="deep-purple-8" style="font-family: 'Noto Sans KR', sans-serif;"/>
      </div>
    </q-form>

  </div>
</template>
<script>
import axios from 'axios'
export default {
  data () {
    return {
      isLogin: this.$store.state.isLogin,
      content: '',
      error: '',
    }
  },
  methods: {
    submitComment() {
      if (this.isLogin == true) {
        if (this.content.length > 2) {
          const commentForm = {
            content: this.content,
            email: this.$store.state.userInfo.email,
            pid: this.$route.params.id
          }
          axios.post('http://i4b103.p.ssafy.io:8080/cmt/write', commentForm)
            .then(() => {
              this.content = ''
              this.error = ''
              this.$emit('createComment')
            })
            .catch((error) => {
              console.log(error)
            })
        } else {
          this.error = '댓글은 세 글자 이상 작성해주세요.'
        }
      } else {
        this.$q.notify({
          color: 'red-5',
          textColor: 'white',
          icon: 'warning',
          message: '로그인 후 이용해 주세요.',
        });
      }
    }
  },
 
}
</script>
<style>

</style>