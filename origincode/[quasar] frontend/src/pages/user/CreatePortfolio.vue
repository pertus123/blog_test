<template>
  <div class="q-pa-md q-gutter-sm">
    <q-dialog
      v-model="showCreatePortfolioModal"
      maximized
      persistent
      transition-show="slide-up"
      transition-hide="slide-down"
    >
      <q-card class="bg-deep-purple-8">
        <q-bar>
          <q-space />
          <q-btn dense flat icon="close" class="text-white" v-close-popup @click="closeModal">
            <q-tooltip content-class="bg-white text-deep-purple-8">Close</q-tooltip>
          </q-btn>
        </q-bar>
        <div style="font-family: 'Noto Sans KR', sans-serif;" class="text-white q-pt-md text-h7 text-center">포트폴리오를 작성해주세요.</div>

        <q-card-section class="items-center" >
          <v-md-editor 
            class="editor"
            left-toolbar="undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code " 
            v-model="content" 
            :disabled-menus="[]"
            @upload-image="handleUploadImage">
          </v-md-editor>

          <div class="row justify-end q-py-md">
            <q-btn label="저장" @click="createPortfolio" type="submit" color="white" text-color="deep-purple-8" class="q-ml-sm" style="font-family: 'Noto Sans KR', sans-serif;"/>
          </div>
        </q-card-section>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import Vue from 'vue';
import VMdEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';
import enUS from '@kangc/v-md-editor/lib/lang/en-US';
import axios from 'axios'

VMdEditor.use(githubTheme);
VMdEditor.lang.use('en-US', enUS);
Vue.use(VMdEditor);

export default {
  props: [
    'showCreatePortfolioModal'
  ],
  data() {
    return {
      fid: 0,
      exist: 0,
      content: '',
      maximizedToggle: true,
      reload: false,
    }
  }, 
  methods: {
    handleUploadImage(event, insertImage, files) {
      const formData = new FormData()
      formData.append("img", files[0])
    
      axios.post('http://3.36.125.70:8080/portfolio/uploadImg', formData, {
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
    createPortfolio() {
      if (this.exist == 1) {
        const updatePortfolio = {
          email: this.$store.getters.email,
          content: this.content,
          fid: this.fid
        }
      
        axios.post('http://3.36.125.70:8080/portfolio/updatePage', updatePortfolio)
          .then((response) => {
            this.closeModal()
            this.$emit('reloadport')
          })
          .catch((error) => {
            console.log(error)
          })
        this.exist = 0
      } else {
        const createPortfolio = {
          email: this.$store.getters.email,
          content: this.content,
        }
        axios.post('http://3.36.125.70:8080/portfolio/writePage', createPortfolio)
          .then((response) => {
            this.closeModal()
            this.$emit('reloadport')
          })
          .catch((error) => {
            console.log(error)
          })
      }
      this.reload = true
    },
    closeModal() {
      this.$emit('closePortfolioModal')
    }
  },
  created() {
    const email = this.$route.params.email
    axios.get(`http://3.36.125.70:8080/portfolio/detailPage`, {params: {email: `${email}`}})
    .then((response) => {
      if (response.data.sw == 1) {
        this.fid = response.data.folio.fid
        this.exist = 1
        this.content = response.data.folio.content
      }
    })
    .catch((error) => {
      console.log(error)
    })
  },
  watch: {
    reload: function() {
      this.reload = false
      const email = this.$route.params.email
      axios.get(`http://3.36.125.70:8080/portfolio/detailPage`, {params: {email: `${email}`}})
        .then((response) => {
          if (response.data.sw == 1) {
            this.fid = response.data.folio.fid
            this.exist = 1
            this.content = response.data.folio.content
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
.editor {
  height: 75vh;
}
  
</style>