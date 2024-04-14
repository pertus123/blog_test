<template>
  <div class="q-pa-md row justify-center">
    
    <div class="col-10 col-md-6" style="min-width: 400px;">

     

      <div class="index-border q-pl-sm gt-sm">
        <div
          class="text-grey-9 text-caption"
          v-for="anchor in index" 
          :key="anchor.id"
          :style="{ padding: `0px 0px 5px ${anchor.indent * 15}px` }"
          @click="handleAnchorClick(anchor)"
        >
          <a style="cursor: pointer">{{ anchor.title }}</a>
        </div>
      </div>


      <div class="card-index-positioning">
        <q-card class="my-card">
          <q-parallax :height="200" :speed="1.0">
            <template v-slot:media>
              <img v-if="postimage !== null" :src="postimage">
              <img v-else src="https://climate.onep.go.th/wp-content/uploads/2020/01/default-image.png">
            </template>

          </q-parallax>
          <q-card-section class="q-pa-none">
            <div class="flex justify-between">
              <div class="text-bold title text-h4 q-px-lg q-pt-lg" style="font-family: 'Noto Sans KR', sans-serif;">{{ title }}</div>
              <div v-if="this.email == this.$store.state.userInfo.email" class="flex justify-end text-grey-7 items-start">
                <q-btn flat dense label="수정" @click="updatePost"/>
                <q-btn flat dense label="삭제" @click="deletePost"/>
              </div>
            </div>
              <div class="flex justify-start items-center">
                <div class="text-bold q-pl-lg">by</div>
                <q-avatar size="md" class="q-ma-sm goProfile" @click="goProfile">
                  <img v-if="profileImage !== null" :src="profileImage"> 
                  <i v-else class="fas fa-user-circle fa-2x"></i>
                </q-avatar>
                <div class="text-bold goProfile" @click="goProfile">{{ email }}</div>
                <div class="q-pl-lg text-caption">{{ create_date }}</div>
              </div>

          <q-item class="q-py-none">
            <q-item-section side top>
              <div class="flex">
              <q-btn 
                v-for="(tag, index) in tags"
                :key = "index" 
                :label="tag" 
                type="submit" 
                color="deep-purple-8" 
                class="bg-grey-4 q-ml-sm q-mt-sm" 
                flat
                size="small"
                dense
                outline rounded
                style="font-family: 'Noto Sans KR', sans-serif;"
                @click="searchTag(tag)"/>
              </div>
            </q-item-section>
          </q-item>

          </q-card-section>
          <q-card-section class="q-pa-none">
            <v-md-editor v-model="content" mode="preview" ref="editor"/>
          </q-card-section>

          <q-card-actions align="right">
            <q-btn flat round color="red" icon="favorite" @click="likePost">
              <q-badge v-if="this.likes > 0" color="black" floating>{{ this.likes }}</q-badge>
            </q-btn>
            <q-btn flat round color="primary" icon="share" @click="prompt = true"/>
          </q-card-actions>

        </q-card>

      <div class="like-share-button gt-sm">
        <q-btn push class="block q-ma-sm" color="deep-purple-8" round icon="favorite" @click="likePost">
          <q-badge v-if="this.likes > 0" color="black" floating>{{ this.likes }}</q-badge>
        </q-btn>
        <q-btn push class="q-ma-sm" color="deep-purple-8" round icon="share" @click="prompt = true"/>
      </div>

      <q-dialog v-model="prompt">
        <q-card style="min-width: 350px">
          <q-card-section>
            <div class="text-h6 text-bold" style="font-family: 'Noto Sans KR', sans-serif;">링크 공유하기</div>
          </q-card-section>

          <q-card-section class="q-pt-none">
            <q-input dense color="deep-purple-8" v-model="location" />
          </q-card-section>

          <q-card-actions align="right" >
            <q-btn color="deep-purple-8" style="font-family: 'Noto Sans KR', sans-serif;" label="링크 복사" @click="share" v-close-popup />
          </q-card-actions>
        </q-card>
      </q-dialog>
      
      </div>

      <comment-form @createComment="createComment = true"></comment-form>
      <comment-list :createComment="createComment"></comment-list>




    </div>
  </div>

</template>
<script>
import Vue from 'vue';
import VMdEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';
import enUS from '@kangc/v-md-editor/lib/lang/en-US';
import CommentForm from '../Comment/CommentForm'
import axios from 'axios';
import VueClipboard from 'vue-clipboard2'
import moment from 'moment'
import CommentList from '../Comment/CommentList'


VMdEditor.use(githubTheme);
VMdEditor.lang.use('en-US', enUS);
Vue.use(VMdEditor);

VueClipboard.config.autoSetContainer = true
Vue.use(VueClipboard)



export default {
  components: {
    CommentForm,
    CommentList
  },
  data() {
    return {
      thumbStyle: {
        right: '4px',
        borderRadius: '5px',
        backgroundColor: 'grey',
        width: '5px',
        opacity: 0.75
      },

      barStyle: {
        right: '2px',
        borderRadius: '9px',
        backgroundColor: 'grey',
        width: '9px',
        opacity: 0.2
      },
      createComment: false,
      postimage: '',
      isLogin: this.$store.state.isLogin,
      prompt: false,
      location: window.location,
      pid: '',
      title: '',
      profileImage: '',
      email: '',
      create_date: '',
      content: '',
      likes: 0,
      index: [],
      tags: null,
    };
  },
  mounted() {
    // 목차가 작동을 안한다 노답이다 
    const id = this.$route.params.id;
    axios.get(`http://i4b103.p.ssafy.io:8080/notice/detailPage/${id}`)
      .then((response) => {
        this.postimage = response.data.postimage
        this.profileImage = response.data.profileimage
        this.pid = response.data.pid
        this.title = response.data.title
        this.content = response.data.content
        this.email = response.data.email
        this.likes = response.data.likes
        this.create_date =  moment(response.data.create_date).locale('ko').format('llll');
        const anchors = this.$refs.editor.$el.querySelectorAll(
          '.v-md-editor-preview h1,h2,h3,h4,h5,h6'
        );
    
        const index = Array.from(anchors).filter((title) => !!title.innerText.trim());
    
        if (!index.length) {
          this.index = [];
          return;
        }
        const hTags = Array.from(new Set(index.map((title) => title.tagName))).sort();
    
        this.index = index.map((el) => ({
          title: el.innerText,
          lineIndex: el.getAttribute('data-v-md-line'),
          indent: hTags.indexOf(el.tagName),
        }));


      })
      .catch((error) => {
        console.log(error)
      })
    
   
  },
  methods: {
    goProfile() {
      this.$router.push(`/profile/${this.email}/글`)
    },
    searchTag(tag) {
      this.$router.push({name: 'SearchedTag', params: {search: `${tag}`}})
    },
    handleAnchorClick(anchor) {
      const { editor } = this.$refs;
      const { lineIndex } = anchor;

      const heading = editor.$el.querySelector(
        `.v-md-editor-preview [data-v-md-line="${lineIndex}"]`
      );

      if (heading) {
        editor.previewScrollToTarget({
          target: heading,
          scrollContainer: window,
          top: 60,
        });
      }
    },
    deletePost() {
      const id = this.$route.params.id;
      if (this.email == this.$store.state.userInfo.email) {
        axios.delete(`http://i4b103.p.ssafy.io:8080/notice/deletePage/${id}`)
          .then((response) => {
            this.$router.push('/')
          })
          .catch((error) => {
            console.log(error)
          })
      } 
    },
    updatePost() {
      if (this.email == this.$store.state.userInfo.email) {
        this.$router.push(`/updatepost/${this.pid}`) 
      } 
    },
    share() {
      this.$copyText(this.location)
    },
    likePost() {
      if (this.isLogin == true) {
        const pid = this.$route.params.id;
        axios.get(`http://i4b103.p.ssafy.io:8080/notice/likeSwitch?pid=${pid}&uid=${this.$store.state.userInfo.uid}`)
          .then((response) => {
            const id = this.$route.params.id;
            axios.get(`http://i4b103.p.ssafy.io:8080/notice/detailPage/${id}`)
              .then((response) => {
                this.likes = response.data.likes
              })
              .catch((error) => {
                console.log(error)
              })
          })
          .catch((error) => {
            console.log(error)
          })
      } else {
        this.$q.notify({
          color: 'red-5',
          textColor: 'white',
          icon: 'warning',
          message: '로그인 후 이용해 주세요.',
        });
      }
    },
  },
  created() {
    const id = this.$route.params.id;
    axios.get(`http://i4b103.p.ssafy.io:8080/notice/detailPage/${id}`)
      .then((response) => {
        this.postimage = response.data.postimage
        this.profileImage = response.data.profileimage
        this.pid = response.data.pid
        this.title = response.data.title
        this.content = response.data.content
        this.email = response.data.email
        this.likes = response.data.likes
        this.create_date =  moment(response.data.create_date).locale('ko').format('llll');
      })
      .catch((error) => {
        console.log(error)
      })
    axios.get(`http://3.36.125.70:8080/label/detailLabel?pid=${id}`)
      .then((response) => {
        this.tags = response.data
      })
      .catch((error) => {
        console.log(error)
      })
  },
 

};
</script>

<style>
.goProfile {
  cursor: pointer;
}
.card-index-positioning {
  position: relative;
}
.index-border {
  border-left: 2px solid grey;
  top: 130px;
  position: fixed;
  right: 50px;
  font-family: 'Noto Sans KR', sans-serif;
  max-width: 200px;
  max-height: 500px;
  overflow: auto;
}
.index-border::-webkit-scrollbar {
  width: 3px;
}
.index-border::-webkit-scrollbar-thumb {
  background-color: #DCDCDC;
}
.index-border::-webkit-scrollbar-track {
  background-color: white;
}  
.my-card {
  width: 100%;
  margin-top: 5px;
}

.like-share-button {
  position: fixed;
  top: 130px;
  left: 150px;
}
.q-chip {
  cursor: pointer;
}

</style>

