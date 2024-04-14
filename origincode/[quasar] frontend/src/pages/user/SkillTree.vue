<template>
  <div>
    <mindmap :nodes="nodes" :connections="connections" :editable="true" />
  </div>
</template>

<script>
import Vue from "vue";
import VueMindmap from "vue-mindmap";
import "vue-mindmap/dist/vue-mindmap.css";
import axios from "axios";

Vue.use(VueMindmap);

export default {
  name: "MyMindmap",
  data() {
    return {
      modifyFlag: 0,
      nodes: [
        // 개발자 스킬트리 첫번째
        {
          text: "개발자 스킬트리",
          url: "",
          fx: -900.916222252976013,
          fy: -742.1641376795345,
          nodes: [],
          category: "",
        },
        {
          text: "협업툴",
          url: "",
          fx: -1365,
          fy: -800,
          nodes: [
            {
              text: "JIRA",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              category: "",
              color: "grey",
            },
            {
              text: "GIT",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              category: "",
              color: "rgba(255, 189, 10, 1.0)",
            },
            {
              text: "Github",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              category: "",
              color: "rgba(255, 189, 10, 1.0)",
            },
          ],
          category: "",
          color: "rgba(34, 205, 224, 1.0)",
        },

        {
          text: "배포환경",
          url: "",
          fx: -1400.3247731601375,
          fy: -500.23700786748157,
          nodes: [
            {
              text: "AWS",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              category: "",
              color: "grey",
            },
            {
              text: "Jenkins",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              category: "",
              color: "grey",
            },
            {
              text: "Nginx",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              category: "",
              color: "rgba(255, 189, 10, 1.0)",
            },
            {
              text: "Heroku",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              category: "",
              color: "rgba(255, 189, 10, 1.0)",
            },
          ],
          category: "",
          color: "rgba(34, 205, 224, 1.0)",
        },

        {
          text: "백엔드",
          url: "",
          fx: -1216.3247731601375,
          fy: -205.23700786748157,
          nodes: [],
          category: "",
          color: "rgba(34, 205, 224, 1.0)",
        },
        {
          text: "DB",
          url: "",
          fx: -1410.3247731601375,
          fy: 28.23700786748157,
          nodes: [
            {
              text: "MySQL",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              category: "",
              color: "grey",
            },
            {
              text: "SQLD",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              category: "",
              color: "rgba(255, 189, 10, 1.0)",
            },
            {
              text: "OCP",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              category: "",
              color: "rgba(255, 189, 10, 1.0)",
            },
          ],
          category: "",
          color: "rgba(34, 205, 224, 1.0)",
        },
        {
          text: "백엔드 프레임워크",
          url: "",
          fx: -1144.3247731601375,
          fy: 23.23700786748157,
          nodes: [
            {
              text: "SPRING",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              category: "",
              color: "grey",
            },
            {
              text: "Django",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              color: "rgba(255, 189, 10, 1.0)",
              category: "",
            },
            {
              text: "Node.js",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              color: "rgba(255, 189, 10, 1.0)",
              category: "",
            },
            {
              text: "PHP",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              color: "rgba(255, 189, 10, 1.0)",
              category: "",
            },
          ],
          category: "",
          color: "rgba(34, 205, 224, 1.0)",
        },
        {
          text: "웹 기초",
          note: "",
          url: "",
          fx: -750.5231997717085,
          fy: 21.07462866512333,
          nodes: [
            {
              text: "HTML",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              category: "",
              color: "grey",
            },
            {
              text: "CSS",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              category: "",
              color: "rgba(255, 189, 10, 1.0)",
            },
            {
              text: "JavaScript",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              category: "",
              color: "rgba(255, 189, 10, 1.0)",
            },
          ],
        },

        {
          text: "프론트 프레임워크",
          url: "",
          fx: -489.3247731601375,
          fy: 16.23700786748157,
          nodes: [
            {
              text: "Angular",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              category: "",
              color: "grey",
            },

            {
              text: "VUE",
              url: "",
              fx: 83.4768002282915,
              fy: -62.57462866512333,
              nodes: [],
              category: "",
              color: "rgba(175, 54, 242, 1.0)",
            },
            {
              text: "REACT",
              url: "",
              fx: 83.4768002282915,
              fy: -22.574628665123328,
              nodes: [],
              category: "",
              color: "rgba(36, 170, 255, 1.0)",
            },
            {
              text: "Django",
              url: "",
              fx: 83.4768002282915,
              fy: -22.574628665123328,
              nodes: [],
              category: "",
              color: "rgba(36, 170, 255, 1.0)",
            },
          ],
          category: "",
          color: "rgba(34, 205, 224, 1.0)",
        },

        {
          text: "프론트엔드",
          url: "",
          fx: -604.3247731601375,
          fy: -221.23700786748157,
          nodes: [],
          category: "",
          color: "rgba(34, 205, 224, 1.0)",
        },

        {
          text: "알고리즘",
          url: "",
          fx: -450.7839253819375,
          fy: -463.5539283546699,
          nodes: [
            {
              text: "ARRAY",
              url: "",
              fx: 113.4768002282915,
              fy: 1916.57462866512333,
              nodes: [],
              category: "",
              color: "grey",
            },
            {
              text: "STRING",
              url: "",
              fx: 83.4768002282915,
              fy: -62.57462866512333,
              nodes: [],
              category: "",
              color: "rgba(175, 54, 242, 1.0)",
            },
            {
              text: "STACK",
              url: "",
              fx: 83.4768002282915,
              fy: -22.574628665123328,
              nodes: [],
              category: "",
              color: "rgba(36, 170, 255, 1.0)",
            },
            {
              text: "QUEUE",
              url: "",
              fx: 83.4768002282915,
              fy: -22.574628665123328,
              nodes: [],
              category: "",
              color: "rgba(36, 170, 255, 1.0)",
            },
            {
              text: "Linked LIST",
              url: "",
              fx: 83.4768002282915,
              fy: -22.574628665123328,
              nodes: [],
              category: "",
              color: "rgba(36, 170, 255, 1.0)",
            },
            {
              text: "TREE",
              url: "",
              fx: 83.4768002282915,
              fy: -22.574628665123328,
              nodes: [],
              category: "",
              color: "rgba(36, 170, 255, 1.0)",
            },
            {
              text: "완전검색",
              url: "",
              fx: 83.4768002282915,
              fy: -22.574628665123328,
              nodes: [],
              category: "",
              color: "rgba(36, 170, 255, 1.0)",
            },
            {
              text: "그리디",
              url: "",
              fx: 83.4768002282915,
              fy: -22.574628665123328,
              nodes: [],
              category: "",
              color: "rgba(36, 170, 255, 1.0)",
            },
            {
              text: "분할정복",
              url: "",
              fx: 83.4768002282915,
              fy: -22.574628665123328,
              nodes: [],
              category: "",
              color: "rgba(36, 170, 255, 1.0)",
            },

            {
              text: "백트래킹",
              url: "",
              fx: 83.4768002282915,
              fy: -22.574628665123328,
              nodes: [],
              category: "",
              color: "rgba(36, 170, 255, 1.0)",
            },
            {
              text: "그래프",
              url: "",
              fx: 83.4768002282915,
              fy: -22.574628665123328,
              nodes: [],
              category: "",
              color: "rgba(36, 170, 255, 1.0)",
            },
            {
              text: "DP",
              url: "",
              fx: 83.4768002282915,
              fy: -22.574628665123328,
              nodes: [],
              category: "",
              color: "rgba(36, 170, 255, 1.0)",
            },
            {
              text: "컴퓨팅사고력",
              url: "",
              fx: 83.4768002282915,
              fy: -22.574628665123328,
              nodes: [],
              category: "",
              color: "rgba(36, 170, 255, 1.0)",
            },
            {
              text: "알고리즘 문제풀이",
              url: "",
              fx: 83.4768002282915,
              fy: -22.574628665123328,
              nodes: [],
              category: "",
              color: "rgba(36, 170, 255, 1.0)",
            },
          ],
        },

        {
          text: "프로그래밍언어",
          url: "",
          fx: -550.3247731601375,
          fy: -832.23700786748157,
          nodes: [
            {
              text: "MATLAB",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              category: "",
              color: "grey",
            },
            {
              text: "JAVA",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              category: "",
              color: "grey",
            },
            {
              text: "PYTHON",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              category: "",
              color: "rgba(255, 189, 10, 1.0)",
            },
            {
              text: "C++",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              category: "",
              color: "rgba(255, 189, 10, 1.0)",
            },
            {
              text: "C#",
              url: "",
              fx: 83.4768002282915,
              fy: -96.57462866512333,
              nodes: [],
              category: "",
              color: "rgba(255, 189, 10, 1.0)",
            },
          ],
          category: "",
          color: "rgba(34, 205, 224, 1.0)",
        },
        {
          text: "여백용",
          url: "",
          fx: -286.3247731601375,
          fy: 144.23700786748157,
          nodes: [],
          category: "",
        },
      ],
      connections: [
        {
          source: "프론트엔드",
          target: "프론트 프레임워크",
          curve: {
            x: 50.5535,
            y: 0.545,
          },
        },
        {
          source: "프론트엔드",
          target: "웹 기초",
          curve: {
            x: -50.5535,
            y: 0.545,
          },
        },
        {
          source: "개발자 스킬트리",
          target: "프론트엔드",
          curve: {
            x: 60.5535,
            y: 0.545,
          },
        },
        {
          source: "개발자 스킬트리",
          target: "배포환경",
          curve: {
            x: -330.5535,
            y: -40.545,
          },
        },
        {
          source: "백엔드",
          target: "DB",
          curve: {
            x: -100.5535,
            y: 0.545,
          },
        },
        {
          source: "백엔드",
          target: "백엔드 프레임워크",
          curve: {
            x: 50.5535,
            y: 0.545,
          },
        },

        {
          source: "개발자 스킬트리",
          target: "협업툴",
          curve: {
            x: -300.6649,
            y: -300.1111,
          },
        },
        {
          source: "개발자 스킬트리",
          target: "백엔드",
          curve: {
            x: -0.841,
            y: 49.5548,
          },
        },
        {
          source: "개발자 스킬트리",
          target: "프로그래밍언어",
          curve: {
            x: 100.841,
            y: -200.5548,
          },
        },
        {
          source: "알고리즘",
          target: "개발자 스킬트리",
          curve: {
            x: -100,
            y: -250,
          },
        },
      ],
      numberByTag: [
        { label: "Jira", number: 0 },
        { label: "Git", number: 0 },
        { label: "Github", number: 0 },
        { label: "AWS", number: 0 },
        { label: "Jenkins", number: 0 },
        { label: "Nginx", number: 0 },
        { label: "Heroku", number: 0 },
        { label: "MySQL", number: 0 },
        { label: "SQLD", number: 0 },
        { label: "OCP", number: 0 },
        { label: "SPRING", number: 0 },
        { label: "Django", number: 0 },
        { label: "Node.js", number: 0 },
        { label: "PHP", number: 0 },
        { label: "HTML", number: 0 },
        { label: "CSS", number: 0 },
        { label: "JavaScript", number: 0 },
        { label: "Angular", number: 0 },
        { label: "vue", number: 0 },
        { label: "REACT", number: 0 },
        { label: "Django", number: 0 },
        { label: "Array", number: 0 },
        { label: "String", number: 0 },
        { label: "Stack", number: 0 },
        { label: "QUEUE", number: 0 },
        { label: "Linked LIST", number: 0 },
        { label: "TREE", number: 0 },
        { label: "완전검색", number: 0 },
        { label: "그리디", number: 0 },
        { label: "분할정복", number: 0 },
        { label: "백트래킹", number: 0 },
        { label: "그래프", number: 0 },
        { label: "DP", number: 0 },
        { label: "컴퓨팅사고력", number: 0 },
        { label: "알고리즘 문제풀이", number: 0 },
        { label: "MATLAB", number: 0 },
        { label: "JAVA", number: 0 },
        { label: "Python", number: 0 },
        { label: "C++", number: 0 },
        { label: "C#", number: 0 },
      ],
    };
  },
  created() {
  
  },
  methods: {
    showAlert(tag) {
      if (tag !== undefined) {
        this.$q.notify({
          color: 'red-5',
          textColor: 'white',
          icon: 'warning',
          message: `아직 '${tag}'에 대한 글이 없습니다.`,
        });
      }
    },
    showYourSkillTree() {
      for (let i = 0; i < this.numberByTag.length; i++) {
        if (this.numberByTag[i].number > 0) {
          document.querySelectorAll(".mindmap-subnodes > div > div")[i].innerText = this.numberByTag[i].number;
          document.querySelectorAll(".mindmap-subnodes > div > div")[i].style.backgroundColor = 'red';
          document.querySelectorAll(".mindmap-subnodes > div > div")[i].style.paddingRight = '6px';
          document.querySelectorAll(".mindmap-subnodes > div > div")[i].style.paddingLeft = '6px';
          document.querySelectorAll(".mindmap-subnodes > div > div")[i].style.borderRadius = '10px';
          document.querySelectorAll(".mindmap-subnodes > div > div")[i].style.color = 'white';
          document.querySelectorAll(".mindmap-subnodes > div")[i].style.borderLeftColor = "#DBC6ED";
        }
      } 
    },
    modifyWidth() {
      if (this.modifyFlag == 0) {
        this.modifyFlag = 1
        for (let i = 0; i < document.querySelectorAll(".mindmap-subnodes").length; i++) {
          var width = Number(document.querySelectorAll(".mindmap-subnodes")[i].getAttribute('width')) + 30
          document.querySelectorAll(".mindmap-subnodes")[i].setAttribute('width', `${width}` )
          
        }
      }
    }
  },
  mounted() {
    const email = this.$route.params.email;
    for (let j = 0; j < this.numberByTag.length; j++) {
      axios
        .get(`http://i4b103.p.ssafy.io:8080/label/skillTreeLabel?email=${email}&name=${this.numberByTag[j].label}`)
        .then((response) => {
          this.numberByTag[j].number = response.data.length;
          this.showYourSkillTree()
          this.modifyWidth()
        })
        .catch((error) => {
          console.log(error);
        });
    }

    let vm = this
    for (var i = 0; i < document.querySelectorAll(".mindmap-subnodes > div").length; i++) {
      document.querySelectorAll(".mindmap-subnodes > div")[i].addEventListener('click', function(event) {
        axios.get(`http://3.36.125.70:8080/label/skillTreeLabel?email=${email}&name=${event.target.text}`)
          .then((response) => {
            if (response.data.length == 0) {
              vm.showAlert(event.target.text)
            } else {
              vm.$router.push({name: 'SearchedTag', params: {search: `${event.target.text}`, data: response.data}})
            }
          })
          .catch((error) => {
            console.log(error)
          })
        
      })
    }

    document.querySelectorAll(".mindmap-node > a")[11].style.display = "none"; 
    document.querySelectorAll(".mindmap-node > a")[0].style.color = "white"; 
    document.querySelectorAll(".mindmap-node > a")[0].style.backgroundColor = "#512da8"; 

    for (let i = 0; i < document.querySelectorAll(".mindmap-node > a").length; i++) {
      document.querySelectorAll(".mindmap-node > a")[i].style.borderRadius = "30px"; 
      // document.querySelectorAll(".mindmap-node > a")[i].style.fontWeigth = "30px"; 
    }
  

  },
};
</script>

<style>
.mindmap-node > a {
  font-family: 'Noto Sans KR', sans-serif!important;
}
.mindmap-subnode-group > a {
  cursor: pointer!important;
}

</style>
