<template>
  <div class="row justify-center">
    <div class="col-10">
      <div class="q-pa-md">
        <q-input 
          class="q-pb-md title" 
          v-model="title" 
          placeholder="글 제목" 
          color="deep-purple-8" 
          style="font-family: 'Noto Sans KR', sans-serif;"/>

        <q-select
          label="태그를 입력해주세요."
          filled
          v-model="tag"
          use-input
          use-chips
          multiple
          hide-dropdown-icon
          input-debounce="0"
          new-value-mode="add-unique"
          color="deep-purple-8"
          style="font-family: 'Noto Sans KR', sans-serif;"
          class="q-py-sm"
        />

        <v-md-editor 
          left-toolbar="undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code " 
          v-model="content" 
          height="50vh"
          :disabled-menus="[]"
          @upload-image="handleUploadImage">
        </v-md-editor>

        <div class="row justify-end q-py-md">
          <!-- <q-btn label="글쓰기" type="submit" color="deep-purple-8" @click="uploadPost" style="font-family: 'Noto Sans KR', sans-serif;"/> -->
          <q-btn label="수정하기" color="deep-purple-8" @click="thumImgModal = true" style="font-family: 'Noto Sans KR', sans-serif;"/>
          <!-- <q-btn label="임시저장" type="submit" color="white"  text-color="deep-purple-8" class="q-ml-sm" @click="savePost" style="font-family: 'Noto Sans KR', sans-serif;"/> -->

          <q-dialog
            v-model="thumImgModal"
          >
            <q-card style="width: 400px; max-width: 80vw;">
              <q-card-section>
                <div class="text-h6" style="font-family: 'Noto Sans KR', sans-serif;">썸네일 고르기</div>
                <div class="text-caption text-grey" style="font-family: 'Noto Sans KR', sans-serif;">썸네일 미선택 시 기존 이미지로 업로드됩니다.</div>
              </q-card-section>

              <q-card-section class="q-pa-md justify-center flex">
                <img v-if="originalThumImg !== null" :src="originalThumImg" alt="" style="max-width: 300px; max-height: 200px">
                <img v-else :src="imgUrl" alt="" style="max-width: 300px; max-height: 200px">
                <q-file 
                class="q-pt-sm" 
                color="deep-purple-8" 
                style="max-width: 300px" 
                filled bottom-slots 
                v-model="thumImg" 
                label="썸네일을 업로드해주세요." counter
                >
                  <template v-slot:prepend>
                    <q-icon name="cloud_upload" @click.stop />
                  </template>
                  <template v-slot:append>
                    <q-icon name="close" @click="basicImg" class="cursor-pointer" />
                  </template>
                </q-file>
              </q-card-section>

              <q-card-actions align="right" class="text-white">
                <q-btn style="font-family: 'Noto Sans KR', sans-serif;" @click="updatePost" label="올리기" color="deep-purple-8" v-close-popup />
              </q-card-actions>
            </q-card>
          </q-dialog>
        </div>






      </div>
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
import axios from 'axios';


VMdEditor.use(githubTheme);

VMdEditor.lang.use('en-US', enUS);

Vue.use(VMdEditor);

export default {
  name: 'UpdatePost',
  data () {
    return {
      pid: '',
      email: '',
      title: '',
      content: '',
      likes: 0,
      thumImgModal: false,
      thumImg: null,
      originalThumImg: '',
      imgUrl: 'https://climate.onep.go.th/wp-content/uploads/2020/01/default-image.png',
      tag: null,
    }
  },
  methods: {
    updatePost() {
    
      const formData = new FormData()
      formData.append("pid", this.pid)
      formData.append("email", this.$store.state.userInfo.email)
      formData.append("title", this.title)
      formData.append("content", this.content)
      formData.append("likes", this.likes)
      formData.append("clid", 0)

      if (this.thumImg !== null) {
        formData.append("postSwitch", 1)
        formData.append("postimage", this.thumImg)
      } else {
        formData.append("postSwitch", 0)
      }
     
      axios.post('http://i4b103.p.ssafy.io:8080/notice/writePage', formData, {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      })
        .then((response) => {
          if (this.tag) {
            const postTag = {
              pid: response.data.pid,
              tags: Array.from(this.tag),
              uid: this.uid,
            }
          
            axios.post('http://3.36.125.70:8080/label/write', postTag)
              .then((response) => {
                // console.log(response)
              })
              .catch((error) => {
                console.log(error)
              })
          }

          this.$router.push('/')
        })
        .catch((error) => {
          console.log(error)
        })

    },
 
    handleUploadImage(event, insertImage, files) {
     
      const formData = new FormData()
      formData.append("img", files[0])
      
      axios.post('http://3.36.125.70:8080/notice/uploadImg', formData, {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      })
        .then((response) => {
         
          insertImage({
            url: response.data,
            desc: 'desc',
            width: 'auto',
            height: 'auto',
          });
        })
        .catch((error) => {
          console.log(error)
        })
    },
    basicImg() {
      this.thumImg = ''
      this.imgUrl = 'https://climate.onep.go.th/wp-content/uploads/2020/01/default-image.png'
    }
  },
  created() {
    const id = this.$route.params.id;
    axios.get(`http://i4b103.p.ssafy.io:8080/notice/detailPage/${id}`)
      .then((response) => {
        this.likes = response.data.likes
        this.pid = response.data.pid
        this.title = response.data.title
        this.content = response.data.content
        this.email = response.data.email
        this.originalThumImg = response.data.postimage
      })
      .catch((error) => {
        console.log(error)
      })
    axios.get(`http://3.36.125.70:8080/label/detailLabel?pid=${id}`)
      .then((response) => {
        this.tag = response.data
      })
      .catch((error) => {
        console.log(error)
      })
      

    
  },
  watch: {
    thumImg: function (v) {
      if (this.thumImg) {
        this.originalThumImg = null
        this.imgUrl = URL.createObjectURL(this.thumImg)
      }
    },
  },
}
</script>
<style scoped>
.title {
  font-size: 25px;
  font-weight: bold;
}
  
</style>