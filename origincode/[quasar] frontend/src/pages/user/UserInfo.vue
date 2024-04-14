<template>
  <div class="q-pa-md q-mx-auto" style="max-width: 400px">

    <q-form
      @submit="updateInfo"
      class="q-gutter-md"
    >
    
      <q-input
        filled
        v-model="name"
        label="이름을 입력해주세요."
        color="deep-purple-8"
        lazy-rules
        :rules="[ val => val && val.length > 0 ]"
      />

      <q-input 
        filled v-model="email" 
        label-slot
        disable readonly
        color="deep-purple-8"
        hint="이메일은 변경할 수 없습니다.">
        <template v-slot:label>
          <div class="row items-center all-pointer-events">
            <q-icon class="q-mr-xs" size="24px" name="mail" />
          </div>
        </template>
      </q-input>
      
      <q-input
        v-model="introduce"
        filled
        type="textarea"
        color="deep-purple-8"
        label="자기소개를 입력해주세요."
      />

      <q-file 
        style="max-width: 368px"
        filled bottom-slots 
        v-model="profileimage" 
        label="업로드" 
        color="deep-purple-8"
        counter max-files="1">
        <template v-slot:before>
          <q-avatar>
            <img v-if="imgUrl !== null" :src="imgUrl">
            <i v-else class="fas fa-user-circle fa-2x"></i>
          </q-avatar>
        </template>

        <template v-slot:append>
          <q-icon v-if="profileimage !== null" name="close" @click.stop="profileimage = null" class="cursor-pointer" />
          <q-icon name="create_new_folder" @click.stop />
        </template>

        <template v-slot:hint>
          프로필 사진을 업로드해주세요.
        </template>
      </q-file>

      <q-input 
        v-model="password" 
        filled 
        :type="isPwd ? 'password' : 'text'" 
        :hint="error.password"
        color="deep-purple-8"
        lazy-rules
        :rules="[(val) => val && val.length > 0]">
        <template v-slot:append>
          <q-icon
            :name="isPwd ? 'visibility_off' : 'visibility'"
            class="cursor-pointer"
            @click="isPwd = !isPwd"
          />
        </template>
      </q-input>

    
      <div>
        <q-btn label="수정완료" type="submit" color="deep-purple-8" style="font-family: 'Noto Sans KR', sans-serif;"/>
        <q-btn label="탈퇴하기" text-color="deep-purple-8" color="white" class="q-ml-sm" @click="confirm = true" style="font-family: 'Noto Sans KR', sans-serif;"/>
      </div>
    </q-form>

    <q-dialog v-model="confirm" persistent>
      <q-card style="width: 300px">
        <q-card-section class="row items-center">
          <q-avatar icon="signal_wifi_off" color="deep-purple-8" text-color="white" />
          <span class="q-ml-sm" style="font-family: 'Noto Sans KR', sans-serif;">정말 탈퇴하시겠습니까?</span>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn label="취소" color="deep-purple-8" v-close-popup style="font-family: 'Noto Sans KR', sans-serif;"/>
          <q-btn label="탈퇴하기" text-color="deep-purple-8" color="white" v-close-popup @click="deleteInfo" style="font-family: 'Noto Sans KR', sans-serif;"/>
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import * as PasswordValidator from "password-validator"
import { mapState } from 'vuex'
import axios from 'axios'


export default {
  computed: {
    ...mapState(["userInfo"]),
  },
  data () {
    return {
      uid: "",
      name: "",
      email: "",
      password: this.$store.state.userInfo.password,
      isPwd: true,
      introduce: "",
      profileimage: null,
      imgUrl: null,
      passwordForm: new PasswordValidator(),

      error: {
        password: "",
      },
      confirm: false,
    }
  },

  methods: {
    updateInfo () {
      
      if (!this.error.password) {
        
        const formData = new FormData()
        formData.append("uid", this.uid)
        formData.append("email", this.email)
        formData.append("name", this.name)
        if (this.introduce) {
          formData.append("introduce", this.introduce)
        }

        if(this.password) {
          formData.append("password", this.password)
        }
      
        if(this.profileimage != null){
        formData.append("profileimage", this.profileimage)
        formData.append("profileSwitch", 1)
        }
        else{
          formData.append("profileSwitch", 0)
        }
        
        axios
          .post('http://i4b103.p.ssafy.io:8080/user/updateInfo', formData, {
            headers: {
              "Content-Type": "multipart/form-data"
            }
          })
          .then((response) => {
            this.$store.dispatch('setProfileImage', response.data);
            this.$q.notify({
              color: 'green-4',
              textColor: 'white',
              icon: 'cloud_done',
              message: '회원정보 수정완료'
            })
            this.$router.push("/")
          })
          .catch((error) => {
            console.log(error)
          })
      }
      else {
        this.$q.notify({
          color: 'red-5',
          textColor: 'white',
          icon: 'warning',
          message: '양식에 맞게 입력해주세요.'
        })
      }
    },
    check() {
      if (
        this.password.length > 0 &&
        !this.passwordForm.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = "";
    },
    deleteInfo() {
      axios.delete(`http://i4b103.p.ssafy.io:8080/user/delete/${this.$store.state.userInfo.email}`)
        .then((response) => {
          this.$store.dispatch('logout')
          this.$router.push("/")
        })
        .catch((error) => {
          console.log(error)
        })
    },
  },
  watch: {
    password: function (v) {
      this.check();
    },
    profileimage: function (v) {
      if (this.profileimage) {
        this.imgUrl = URL.createObjectURL(this.profileimage)
      }
    },
  },
  created () {
    axios.get(`http://i4b103.p.ssafy.io:8080/user/profile/${this.$store.state.userInfo.email}`)
      .then((response) => {
        this.uid = response.data.uid
        this.email = response.data.email
        this.name = response.data.name
        this.introduce = response.data.introduce
        if (response.data.profileimage) {
          this.imgUrl = response.data.profileimage
        }
      })
      .catch((error) => {
        console.log(error)
      })
    this.passwordForm.is().min(8)      
    this.passwordForm.has().digits(2)
  }
}
</script>

<style lang="">
  
</style>