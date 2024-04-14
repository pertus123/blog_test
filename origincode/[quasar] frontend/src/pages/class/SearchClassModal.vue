<template lang="">
  <div class="q-pa-md q-gutter-sm">

    <q-dialog v-model="showSearchClassModal" persistent>
      <q-card style="width: 500px;">
        <q-card-section>
          <div class="text-h6" style="font-family: 'Noto Sans KR', sans-serif;">강의 검색하기</div>
        </q-card-section>

        <div class="wrap q-px-md">
          <div id="app" 
              v-on:keyup.down="selectValue('down')"
              v-on:keyup.up="selectValue('up')">
            <div class="search">
              <q-input 
                class="s q-px-none" 
                @keyup="setSearchQuery($event)" 
                :value="searchQuery" 
                style="font-family: 'Noto Sans KR', sans-serif;" 
                placeholder="강의를 검색해주세요." 
                filled 
                type="search" 
                color="deep-purple-8">
                <template v-slot:append>
                  <q-icon name="search" />
                </template>
              </q-input>
              <ul class="r" tabindex="0" 
                v-bind:class="{ show: isActive }"
                v-on:mouseover="removeValue">
                <li tabindex="-1" 
                  v-for="(el, index) in filterList.slice(0,11)" 
                  :key="index"
                  v-on:click="changeValue(el.course)"
                  v-on:keyup.enter="selectValue('enter', el.course)">
                  <div class="titleLimit" style="font-family: 'Noto Sans KR', sans-serif;" >{{ el.course }}</div>
                </li>
              </ul>
            </div>
            <q-card class="my-card scale q-mx-auto q-my-sm selectedCourseInfo" @click="goDetail">
              <q-item>
                <q-item-section>
                  <q-item-label class="selectedCourseTitle cardTitleLimit">title</q-item-label>
                  <q-item-label class="selectedCourseInstructor" caption>Subhead</q-item-label>
                </q-item-section>
              </q-item>

              <img class="selectedCourseImg" src="https://climate.onep.go.th/wp-content/uploads/2020/01/default-image.png">
            </q-card>

          </div>
        </div>

        <q-card-actions align="right">
          <q-btn label="강의 추가" color="deep-purple-8" style="font-family: 'Noto Sans KR', sans-serif;"  @click="addCourse" v-close-popup />
          <q-btn label="취소" color="white" @click="close" style="font-family: 'Noto Sans KR', sans-serif;" class="text-deep-purple-8" v-close-popup />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>
<script>
import axios from 'axios'

export default {
  props: [
    'showSearchClassModal'
  ],
  data() {
    return {
      isActive: false,
      searchQuery: '',
      classes: [],
      filterList: [],
    }
  },
  computed: {
    uid () {
      return this.$store.getters.uid
    },
  },
  methods: {
    goDetail() {
      var vm = this
      const selectedCourse = this.filterList.filter(function(el) {
        if (el.course == vm.searchQuery) {
          return el
        }
      })
      window.open(selectedCourse[0].courseurl)
    },
    addCourse() {
      this.$emit('closeModal')
      this.searchQuery = ''
      
      var str = document.querySelector('.selectedCourseTitle').innerText
      axios.get(`http://3.36.125.70:8080/course/createCourse`, {params: {
        coursetype: 1,
        keyword: str,
        uid: this.uid
      }})
        .then(() => {
          this.$emit('reload')
        })
        .catch((error) => {
          switch (error.response.status) {
            case 406:
              this.$q.notify({
                color: 'red-5',
                textColor: 'white',
                icon: 'warning',
                message: '이미 등록한 강의입니다.',
              });
              break;
          }
        })

    },
    setSearchQuery(event) {
      document.querySelector('.wrap').style.paddingBottom = '200px'
      document.querySelector('.search').style.marginBottom = '0px'
      this.searchQuery = event.target.value
      if (document.querySelector('.r').style.display === 'none') {
        document.querySelector('.r').style.display = 'block'
      }
      if (document.querySelector('.selectedCourseInfo').style.display === 'block') {
        document.querySelector('.selectedCourseInfo').style.display = 'none'
      }
    },
    close() {
      this.$emit('closeModal')
      this.searchQuery = ''
    },
    changeValue(str) {
      this.isActive = false;
      this.searchQuery = str;
      const selectedCourse = this.filterList.filter(function(el) {
        if (el.course == str) {
          return el
        }
      })
      document.querySelector('.r').style.display = 'none'
      document.querySelector('.selectedCourseInfo').style.display = 'block'
      document.querySelector('.selectedCourseTitle').innerText = str
      document.querySelector('.selectedCourseInstructor').innerText = selectedCourse[0].instructor
      document.querySelector('.selectedCourseImg').src = selectedCourse[0].imageurl
      document.querySelector('.search').style.marginBottom = '20px'
      document.querySelector('.wrap').style.paddingBottom = '0px'

    },
    selectValue(keycode, str) {
      if (this.isActive === true) {
        const hasClass = document.querySelector('.r').classList.contains('key');
        if (keycode === 'down') {
          if (!hasClass) {
            const thisEl = document.querySelectorAll('.r li')[0];
            document.querySelector('.r').classList.add('key');
            thisEl.classList.add('sel');
            thisEl.focus();
          } else {
            const lastEl = document.querySelector('.r li:last-child');
            const thisEl = document.querySelector('.r li.sel');
            const nextEl = thisEl.nextElementSibling;
            if (!lastEl.classList.contains('sel')) {
              thisEl.classList.remove('sel');
              nextEl.classList.add('sel');
              nextEl.focus();
            }
          }
        }
        if (keycode === 'up' && hasClass) {
          const firstEl = document.querySelectorAll('.r li')[0];
          const thisEl = document.querySelector('.r li.sel');
          const prevEl = thisEl.previousElementSibling;
          if (!firstEl.classList.contains('sel')) {
            thisEl.classList.remove('sel');
            prevEl.classList.add('sel');
            prevEl.focus();
          } else {
            document.querySelector('.s').focus();
          }
        }
        if (keycode === 'enter' && hasClass) {
          this.changeValue(str);
        }
      }
    },
    removeValue() {
      if (document.querySelector('.r').classList.contains('key')) {
        document.querySelector('.r').classList.remove('key');
        document.querySelector('.r li.sel').classList.remove('sel');
      }
    },
   
  },
  created() {
    axios.get('http://3.36.125.70:8080/course/keywordList?coursetype=1')
      .then((response) => {
        this.classes = response.data
      })
      .catch((error) => {
        console.log(error)
      })
  },
  watch: {
    searchQuery: function() {
      const str = this.searchQuery.toLowerCase()
      const reg = /[^-.!가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9|\s]/.test(str);
      if (reg === false && str !== '' && str !== ' ') {
        this.isActive = true;
        this.filterList = this.classes.filter((el) => {
          return el.course.toLowerCase().match(str);     
        });
      } else {
        this.isActive = false;
      }
    }
  }
   
  
}
</script>
<style lang="scss" scoped>
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
.cardTitleLimit {
  width: 380px;
  padding-right: 5px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.selectedCourseInfo {
  text-decoration: none;
  display: none;
  width: 400px;
}
.titleLimit {
  width: 430px;
  padding-right: 5px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.wrap {
  display: table;
  padding-bottom: 200px;
  width: 100%;
  height: 100%;
}
#app {
  display: table-cell;
  vertical-align: middle;
  text-align: center;
  .search {
    position: relative;
    margin: 0 auto;
    width: 100%;
    max-width: 600px;
    .s {
      padding-top: 10px;
      width: 100%;
      max-width: 600px;
      height: 60px;
      font-size: 16px;
    }
    .r {
      padding-left: 0;
      display: none;
      position: absolute;
      left: 0;
      top: 60px;
      width: 100%;
      height: 156px;
      overflow-y: auto;
      &.show {
        display: block;
      }
      li {
        list-style: none;
        margin-top: -1px;
        padding: 0 20px;
        width: 100%;
        height: 40px;
        background-color: #fff;
        // box-sizing: border-box;
        outline: none;
        font-size: 16px;
        line-height: 40px;
        cursor: pointer;
        &:hover, &.sel {
          background-color: darken(#fff, 5%);
        }
      }
    }
    p {
      padding: 10px 0;
      text-align: right;
      font-size: 12px;
    }
  }
}

</style>