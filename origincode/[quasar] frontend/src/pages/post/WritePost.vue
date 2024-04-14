<template>
  <div class="row justify-center writepost">
    <div class="col-10">
      <div class="q-pa-md">
        <q-input 
          class="q-pb-md title" 
          v-model="title" 
          placeholder="글 제목" 
          :rules="[val => !!val ]"
          style="font-family: 'Noto Sans KR', sans-serif;" 
          color="deep-purple-8"/>

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
          <q-btn label="글쓰기" color="deep-purple-8" @click="thumImgModal = true" style="font-family: 'Noto Sans KR', sans-serif;"/>
          <q-btn label="임시저장" type="submit" color="white"  text-color="deep-purple-8" class="q-ml-sm" @click="savePost" style="font-family: 'Noto Sans KR', sans-serif;"/>

          <q-dialog
            v-model="thumImgModal"
          >
            <q-card style="width: 400px; max-width: 80vw;">
              <q-card-section>
                <div class="text-h6" style="font-family: 'Noto Sans KR', sans-serif;">썸네일 고르기</div>
                <div class="text-caption text-grey" style="font-family: 'Noto Sans KR', sans-serif;">썸네일 미선택 시 기본이미지로 업로드됩니다.</div>
              </q-card-section>

              <q-card-section class="q-pa-md justify-center flex">
                <img v-if="originalThumImg !== '' && originalThumImg !== null" :src="originalThumImg" alt="" style="max-width: 300px; max-height: 200px">
                <img v-else :src="imgUrl" alt="" style="max-width: 300px; max-height: 200px">
                <q-file class="q-pt-sm" color="deep-purple-8" style="max-width: 300px" filled bottom-slots v-model="thumImg" label="썸네일을 업로드해주세요." counter>
                  <template v-slot:prepend>
                    <q-icon name="cloud_upload" @click.stop />
                  </template>
                  <template v-slot:append>
                    <q-icon name="close" @click="basicImg" class="cursor-pointer" />
                  </template>
                </q-file>
              </q-card-section>

              <q-card-actions align="right" class="text-white">
                <q-btn style="font-family: 'Noto Sans KR', sans-serif;" @click="uploadPost" label="올리기" color="deep-purple-8" v-close-popup />
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
  name: 'WritePost',
  data () {
    return {
      title: '',
      content: '',
      thumImgModal: false,
      thumImg: null,
      imgUrl: 'https://climate.onep.go.th/wp-content/uploads/2020/01/default-image.png',
      tag: [],
      clid: 0,
      tpid: 0,
      originalThumImg: "",
    }
  },
  computed: {
    uid() {
      return this.$store.getters.uid
    }
  },
  methods: {
    uploadPost() {
      const formData = new FormData()
      formData.append("email", this.$store.state.userInfo.email)
      formData.append("title", this.title)
      formData.append("content", this.content)
      formData.append("clid", this.clid)

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

          if (this.tag.length > 0) {
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

      if (this.$route.query.tpid) {
        const tpid = this.$route.query.tpid
        axios.delete(`http://3.36.125.70:8080/temp/delete/${tpid}`)
          .then((response) => {
            // console.log(response)
          })
          .catch((error) => {
            console.log(error)
          })
      }
    },
    savePost() {
      const tempPost = {
        email: this.$store.state.userInfo.email,
        title: this.title,
        content: this.content,
        postimage: this.originalThumImg,
        tpid: this.tpid,
        tags: this.tag
      }

      axios.post('http://3.36.125.70:8080/temp/write', tempPost)
        .then((response) => {

          this.$router.push(`/temppostlist/${this.$store.state.userInfo.email}`)
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
  watch: {
    thumImg: function (v) {
      if (this.thumImg) {
        this.imgUrl = URL.createObjectURL(this.thumImg)
      }
    },
  },
  created() {
    if (this.$route.query.main && this.$route.query.course) {
      this.tag = [this.$route.query.main, this.$route.query.course]
      this.clid = this.$route.params.clid
    } else if (this.$route.query.tpid) {
      const id = this.$route.query.tpid
      axios.post(`http://3.36.125.70:8080/temp/detail?tpid=${id}`)
        .then((response) => {
          this.title = response.data.data[0].title
          this.email = response.data.data[0].email
          this.content = response.data.data[0].content
          this.tpid = response.data.data[0].tpid
          this.originalThumImg = response.data.data[0].postimage
          this.tag = response.data.tags
        })
        .catch((error) => {
          console.log(error)
        })
    }
  },
}
</script>
<style>
.writepost {
  min-width: 400px;
}
.title {
  font-size: 25px;
  font-weight: bold;
}
.vue-tags-input {
  font-family: 'Noto Sans KR', sans-serif;
}
.vue-tags-input .ti-input {
  border: none;
  padding: 0;
}
.vue-tags-input .ti-input .ti-tags .ti-tag {
  background-color: #512da8;
  border-radius: 3px;
}



</style>

