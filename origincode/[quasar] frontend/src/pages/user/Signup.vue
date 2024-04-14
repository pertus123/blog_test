<template>
  <div class="q-pa-md q-mx-auto" style="max-width: 400px">
    <q-form @submit="onSubmit" @reset="onReset" class="q-gutter-md">
      <q-input
        filled
        v-model="name"
        label="이름을 입력해주세요."
        color="deep-purple-8"
        lazy-rules
        :rules="[(val) => val && val.length > 0]"
      />

      <q-input
        filled
        v-model="email"
        label-slot
        color="deep-purple-8"
        lazy-rules
        :hint="error.email"
        :rules="[(val) => val && val.length > 0]"
      >
        <template v-slot:label>
          <div class="row items-center all-pointer-events">
            <q-icon class="q-mr-xs" size="24px" name="mail" />
            이메일을 입력해주세요.
          </div>
        </template>
      </q-input>

      <q-input
        filled
        type="password"
        v-model="password"
        label="비밀번호를 입력해주세요."
        color="deep-purple-8"
        :hint="error.password"
        lazy-rules
        :rules="[(val) => val && val.length > 0]"
      />

      <q-input
        filled
        type="password"
        v-model="passwordConfirm"
        label="비밀번호를 한 번 더 입력해주세요."
        color="deep-purple-8"
        :hint="error.passwordConfirm"
        lazy-rules
        :rules="[(val) => val && val.length > 0]"
      />

      <q-toggle v-model="accept" label="약관에 동의합니다." color="deep-purple-8"/>

      <!-- 약관 관련 코드 -->
      <q-btn label="약관 보기" @click="confirm = true" dense size="xs" flat color="deep-purple-8"/>

      <q-dialog
        v-model="confirm"
      >
        <q-card style="width: 500px">
          <q-card-section>
            <div class="text-h6">약관</div>
          </q-card-section>

          <q-card-section class="col q-pt-none">
            <div v-html="terms"></div>
          </q-card-section>

          <q-card-actions align="right" class="bg-white text-teal">
            <q-btn 
              color="deep-purple-8"
              label="확인" 
              v-close-popup />
          </q-card-actions>
        </q-card>
      </q-dialog>

      <div class="row justify-end">
        <q-btn label="회원가입" type="submit" color="deep-purple-8" style="font-family: 'Noto Sans KR', sans-serif;"/>
        <q-btn
          style="font-family: 'Noto Sans KR', sans-serif;"
          label="Reset"
          type="reset"
          color="white"
          text-color="deep-purple-8"
          class="q-ml-sm"
        />
      </div>
    </q-form>
  </div>
</template>

<script>
import * as EmailValidator from 'email-validator';
import * as PasswordValidator from 'password-validator';
import Axios from 'axios';
import terms from '!raw-loader!../../assets/terms.txt'

// 이메일 인증 코드
import firebase from 'firebase';

var firebaseConfig = {
  apiKey: 'AIzaSyA9PYvVR-o07RFJXL2nA2OvGW3NYYKhlhs',
  authDomain: 'sub-1-project.firebaseapp.com',
  projectId: 'sub-1-project',
  storageBucket: 'sub-1-project.appspot.com',
  messagingSenderId: '917578120960',
  appId: '1:917578120960:web:85afd36b788e77fa453de5',
  measurementId: 'G-HEFQKF3W08',
};
// Initialize Firebase
firebase.initializeApp(firebaseConfig);

export default {
  data() {
    return {
      terms: terms.split('\n').join('<br />'),
      name: '',
      email: '',
      password: '',
      passwordConfirm: '',
      passwordForm: new PasswordValidator(),

      error: {
        email: null,
        password: null,
        passwordConfirm: null,
      },
      accept: false,
      confirm: false,
    };
  },
  methods: {
    onSubmit() {
      if (this.accept !== true) {
        this.$q.notify({
          color: 'red-5',
          textColor: 'white',
          icon: 'warning',
          message: '약관에 동의해주세요.',
        });
      } else {
        if (
          this.error.email == null &&
          this.error.password == null &&
          this.error.passwordConfirm == null
        ) {
          const user = {
            name: this.name,
            email: this.email,
            password: this.password,
          };
          Axios.post('http://i4b103.p.ssafy.io:8080/user/signup', user)
            .then((response) => {
              // console.log(response);
              // 이메일 인증 코드
              firebase
                .auth()
                .createUserWithEmailAndPassword(user.email, user.password)
                .then((userCredential) => {})
                .then(() => {
                  firebase
                    .auth()
                    .currentUser.sendEmailVerification()
                    .then(function() {
                      // console.log('이메일 전송');
                    })
                    .catch('이메일 전송 실패');
                });

              this.$q.notify({
                color: 'green-4',
                textColor: 'white',
                icon: 'cloud_done',
                message: '이메일이 발송되었습니다. 인증을 완료해주세요!',
              });

              this.$router.push('/login');
            })
            .catch((error) => {
              this.$q.notify({
                color: 'red-5',
                textColor: 'white',
                icon: 'warning',
                message: '중복된 이메일입니다.',
              });
            });
        }
      }
    },

    onReset() {
      (this.name = ''),
        (this.email = ''),
        (this.password = ''),
        (this.passowordConfirm = ''),
        (this.accept = false);
    },
    check() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = '이메일 형식에 맞게 입력해주세요.';
      else this.error.email = null;
      if (
        this.password.length > 0 &&
        !this.passwordForm.validate(this.password)
      )
        this.error.password = '영문,숫자 포함 8 자리이상이어야 합니다.';
      else this.error.password = null;

      if (
        this.passwordConfirm.length > 0 &&
        this.passwordConfirm != this.password
      )
        this.error.passwordConfirm = '비밀번호가 일치하지 않습니다.';
      else this.error.passwordConfirm = null;
    },
  },
  watch: {
    password: function(v) {
      this.check();
    },
    email: function(v) {
      this.check();
    },
    passwordConfirm: function(v) {
      this.check();
    },
  },
  created() {
    this.passwordForm.is().min(8);
    this.passwordForm.has().digits(2);
  },
};
</script>

<style lang=""></style>
