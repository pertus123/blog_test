<template>
  <div class="q-pa-md row justify-center">
    <div class="col-12 col-md-8">
      <div class="q-pa-md">
                    
        <div class="q-gutter-y-md" >
          <div v-if="tab == '글' && tag.length > 0" class="tag gt-md q-pl-sm text-caption"> 
            <div @click="showAllTag">전체보기</div>
            <div v-for="(tagName, index) in tag" :key="index" @click="tagPost(tagName.tagName)">
              {{ tagName.tagName }}
              ({{ tagName.num }})
            </div>
          </div>
         
          <q-card>
           <q-item class="q-py-lg">
            <q-item-section avatar>
              <q-avatar size="xl">
                <img v-if="profileimage !== null" :src="profileimage"> 
                <i v-else class="fas fa-user-circle fa-2x"></i>
              </q-avatar>
            </q-item-section>

            <q-item-section>
              <q-item-label class="text-bold">{{ email }}</q-item-label>
              <q-item-label caption>{{ introduce }}</q-item-label>
            </q-item-section>
          </q-item>

          <q-separator></q-separator>

            <q-tabs
              v-model="tab"
              dense
              class="text-grey-9 tabs"
              active-color="deep-purple-8"
              indicator-color="deep-purple-8"
              narrow-indicator
              align="justify"
            >
              <q-tab @click="goTab('글')" name="글" label="글" />
              <q-tab @click="goTab('스킬트리')" name="스킬트리" label="스킬트리" />
              <q-tab @click="goTab('강의목록')" name="강의목록" label="강의목록" />
              <q-tab @click="goTab('포트폴리오')" name="포트폴리오" label="포트폴리오" />
            </q-tabs>

            <q-separator />

            <q-tab-panels v-model="tab" animated>
              <q-tab-panel name="글">
                <q-list v-for="(post, index) in posts" :key="index" @click="goDetail(post.pid)" class="post">
                  <q-item dense class="q-py-none">
                    <q-item-section>
                      <q-item-label lines="1">{{ post.title }}</q-item-label>
                    </q-item-section>
                    <q-item-section side top>    
                        <q-item-label caption lines="1">{{ post.create_date }}</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item dense class="q-py-none">
                    <q-item-section>
                      <q-item-label>
                        <q-btn 
                          v-for="(tag, index) in post.tags"
                          :key = "index" 
                          :label="tag" 
                          color="deep-purple-8" 
                          class="bg-grey-4 q-mr-sm q-mt-sm text-caption" 
                          flat
                          dense
                          outline rounded
                          style="font-family: 'Noto Sans KR', sans-serif;"/>
                      </q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item dense class="q-py-none">
                    <q-item-section>
                      <q-item-label caption lines="1">{{ post.content }}</q-item-label>
                    </q-item-section>
                  </q-item>

                  <q-separator spaced inset />

                </q-list>
              </q-tab-panel>

              <q-tab-panel name="스킬트리">
                <div class="text-caption text-grey" style="font-family: 'Noto Sans KR', sans-serif;">마우스 스크롤로 스킬트리를 확대할 수 있어요!</div>
                <skill-tree></skill-tree>
              </q-tab-panel>

              <q-tab-panel name="강의목록">
                <q-btn v-if="email == loginUserEmail" round icon="add" color="white" class="q-ml-auto text-deep-purple-8 addClass" @click="showSearchClassModal = true"/>
                <search-class-modal :showSearchClassModal="showSearchClassModal" v-on:closeModal="showSearchClassModal = false" v-on:reload="reload = true"></search-class-modal>
                <div class="row flex-center">
                  
                  <q-card class="my-card col-10 col-sm-5 q-ma-lg scale" v-for="(course, index) in courses" :key="index" @click="goClassIndex(course.gid, course.course)">
                    <q-item>
                      <q-item-section>
                        <q-item-label lines="1">{{ course.course }}</q-item-label>
                          
                          
                        <q-item-label caption>{{ course.instructor }}</q-item-label>
                      </q-item-section>
                      <q-item-section side top>
                        <q-btn v-if="email == loginUserEmail" flat dense label="삭제" class="text-grey text-caption q-ma-none" to="#" @click="deleteCourse(course.gid)"/>
                      </q-item-section>
                    </q-item>

                    <img :src="course.imageurl">
                  </q-card>
              
                </div>
            
              </q-tab-panel>

              <q-tab-panel name="포트폴리오">
                <div class="flex">
                  <div v-if="tag.length < 10" class="text-caption text-grey" style="font-family: 'Noto Sans KR', sans-serif;">태그를 10개 이상 작성하면 태그 분석이 생성됩니다!</div>
                  <q-btn v-if="email == loginUserEmail" round icon="add" color="white" class="q-ml-auto text-deep-purple-8 addClass" @click="showCreatePortfolioModal = true"/>
                </div>
                <create-portfolio 
                  :showCreatePortfolioModal="showCreatePortfolioModal" 
                  v-on:closePortfolioModal="showCreatePortfolioModal = false" 
                  v-on:reloadport="reloadport = true"></create-portfolio>
                <div v-if="tag.length >= 10" class="flex flex-center q-pb-lg">
                  <radar-chart></radar-chart>
                  <div class="q-py-xl">
                    <q-badge class="q-pl-sm q-mb-sm q-ml-xl" color="deep-purple-8">태그 상위 10개</q-badge>
                    <div class="tagPortfolio q-pl-sm q-mx-xl text-caption"> 
                      <div v-for="(tagName, index) in tag.slice(0, 10)" :key="index">
                        {{index + 1}}. {{ tagName.tagName }}
                        ({{ tagName.num }})
                      </div>
                    </div>
                  </div>
                </div>
                <line-chart></line-chart>
                <div v-if="portContent !== null" class="q-py-xl">
                  <v-md-editor v-model="portContent" mode="preview" ref="editor"/>
                </div>
              </q-tab-panel>

            </q-tab-panels>
          </q-card>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import LineChart from './LineChart.vue'
import RadarChart from './RadarChart.vue'
import SearchClassModal from '../class/SearchClassModal.vue'
import SkillTree from './SkillTree.vue'
import axios from 'axios'
import removeMd from 'remove-markdown'
import moment from 'moment'
import CreatePortfolio from './CreatePortfolio.vue'


import Vue from 'vue';
import VMdEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';
import enUS from '@kangc/v-md-editor/lib/lang/en-US';


VMdEditor.use(githubTheme);
VMdEditor.lang.use('en-US', enUS);
Vue.use(VMdEditor);

export default {
  data () {
    return {
      portContent: null,
      posts: [],
      courses: [],
      tab: '',
      email: this.$route.params.email,
      introduce: '',
      profileimage: '',
      showSearchClassModal: false,
      showCreatePortfolioModal: false,
      reload: false,
      reloadport: false,
      tag: [],
      lastTab: '',
    }
  },
  components: {
    LineChart,
    RadarChart,
    SkillTree,
    SearchClassModal,
    CreatePortfolio,
  },
  computed: {
    loginUserEmail() {
      return this.$store.getters.email
    }
  },
  methods: {
    goTab(tabName) {
      this.tab = tabName
      this.$router.push(`/profile/${this.email}/${this.tab}`)
    },
    deleteCourse(gid) {
      if (this.email == this.loginUserEmail) {
        axios.delete(`http://3.36.125.70:8080/course/courseDelete?email=${this.email}&gid=${gid}`)
          .then(() => {
            this.reload = true
          })
          .catch((error) => {
            console.log(error)
          })
      }
    },
    showAllTag() {
      axios.get(`http://3.36.125.70:8080/notice/listUserPage?email=${this.email}`)
        .then((response) => {
          this.posts = response.data.reverse()
          this.modifyItems()
        })
        .catch((error) => {
          console.log(error)
        })
    },
    tagPost(name) {
      axios.get(`http://3.36.125.70:8080/label/skillTreeLabel?email=${this.email}&name=${name}`)
        .then((response) => {
          this.posts = response.data.reverse()
          this.modifyItems()
        })
        .catch((error) => {
          console.log(error)
        })
    },
    goClassIndex(gid, course) {
      this.$router.push({name: 'ClassIndex', params: {email: this.email, coursename: course, gid: gid}})
    },
    goDetail(pid) {
      this.$router.push(`/detailpost/${pid}`)
    },
    modifyItems() {
      for (var index in this.posts) {
        const content = this.posts[index].content
        var plainText = removeMd(content).split('\n').join(' ');
        plainText = plainText.split('desc{{{width="auto" height="auto"}}}').join(' ')
        this.posts[index].content = plainText
        const date = moment(this.posts[index].create_date).locale('ko').format('llll');
        this.posts[index].create_date = date
      } 
    },
  },
  created() {
    this.tab = this.$route.params.tab
    axios.get(`http://3.36.125.70:8080/notice/listUserPage?email=${this.email}`)
      .then((response) => {
        this.posts = response.data.reverse()
        this.modifyItems()
      })
      .catch((error) => {
        console.log(error)
      })
    
    axios.get(`http://3.36.125.70:8080/course/cardlist?email=${this.email}`)
      .then((response) => {
        this.courses = response.data
      })
      .catch((error) => {
        console.log(error)
      })
      
    axios.get(`http://3.36.125.70:8080/user/profile/${this.email}`)
      .then((response) => {
        this.introduce = response.data.introduce
        this.profileimage = response.data.profileimage
      })
      .catch((error) => {
        console.log(error)
      })
    axios.get(`http://3.36.125.70:8080/label/myLabel?email=${this.email}`)
      .then((response) => {
        this.tag = response.data
      })
      .catch((error) => {
        console.log(error)
      })
    axios.get(`http://3.36.125.70:8080/portfolio/detailPage`, {params: {email: `${this.email}`}})
      .then((response) => {
        this.portContent = response.data.folio.content
      })
      .catch((error) => {
        console.log(error)
      })
    
  },
  watch: {
    reload: function() {
      axios.get(`http://3.36.125.70:8080/course/cardlist?email=${this.email}`)
        .then((response) => {
          this.courses = response.data
        })
        .catch((error) => {
          console.log(error)
        })

      this.reload = false
    },
    reloadport: function() {
      axios.get(`http://3.36.125.70:8080/portfolio/detailPage`, {params: {email: `${this.email}`}})
        .then((response) => {
          this.portContent = response.data.folio.content
        })
        .catch((error) => {
          console.log(error)
        })

      this.reloadport = false
    },
    tab: function() {
      if (this.tab !== '글') {
        axios.get(`http://3.36.125.70:8080/notice/listUserPage?email=${this.email}`)
          .then((response) => {
            this.posts = response.data.reverse()
            this.modifyItems()
          })
          .catch((error) => {
            console.log(error)
          })
      }
    },
  
  },
}
</script>
<style scoped>
.titleLimit {
  width: 13rem;
  padding-right: 5px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.addClass {
  display: block;
  margin-left: auto;
}
.post {
  cursor: pointer;
}  
.tag {
  border-left: 2px solid grey;
  top: 130px;
  position: fixed;
  right: 50px;
  font-family: 'Noto Sans KR', sans-serif;
  max-width: 200px;
  cursor: pointer;
  max-height: 500px;
  overflow: auto;
}
.tag::-webkit-scrollbar {
  width: 3px;
}
.tag::-webkit-scrollbar-thumb {
  background-color: #DCDCDC;
}
.tag::-webkit-scrollbar-track {
  background-color: white;
}  
.tagPortfolio {
  border-left: 2px solid grey;
  font-family: 'Noto Sans KR', sans-serif;
}
.scale {
  transform: scale(1);
  -webkit-transform: scale(1);
  -moz-transform: scale(1);
  -ms-transform: scale(1);
  -o-transform: scale(1);
  transition: all 0.3s ease-in-out;
}
.scale:hover { 
  transform: scale(1.03);
  -webkit-transform: scale(1.03);
  -moz-transform: scale(1.03);
  -ms-transform: scale(1.03);
  -o-transform: scale(1.03);
}
</style>