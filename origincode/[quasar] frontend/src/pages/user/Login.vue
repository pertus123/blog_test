<template>
  <div class="q-pa-md q-mx-auto" style="max-width: 400px">
    <q-form @submit="login" @reset="onReset" class="q-gutter-md">
      <q-input
        filled
        v-model="email"
        label-slot
        lazy-rules
        color="deep-purple-8"
        :hint="emailError"
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
        lazy-rules
        :rules="[(val) => val && val.length > 0]"
      />

      <div class="row justify-end">
        <q-btn label="로그인" type="submit" color="deep-purple-8" style="font-family: 'Noto Sans KR', sans-serif;"/>
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
import axios from 'axios';
import firebase from 'firebase';

export default {
  data() {
    return {
      email: '',
      password: '',
      emailError: '',
    };
  },

  methods: {
    login() {
      const credentials = {
        email: this.email,
        password: this.password,
      };
      // firebase
      //   .auth()
      //   .signInWithEmailAndPassword(this.email, this.password)
      //   .then((response) => {
          // if (response.user.emailVerified) {
          // 이메일 인증을 안해도 로그인 가능하도록 임의 설정
            axios
            .post('http://i4b103.p.ssafy.io:8080/user/login', credentials)
            .then((response) => {
              const token = response.data['auth-token'];
              localStorage.setItem('auth-token', token);
              this.$store.dispatch('login', token);
              this.$store.dispatch('sendPassword', this.password);
              this.$router.push('/');
            })
            .catch((error) => {
              console.log(error);
              this.$q.notify({
                color: 'red-5',
                textColor: 'white',
                icon: 'warning',
                message: '이메일과 비밀번호를 확인해주세요.',
              });
            });
          // } else {
          //   this.$q.notify({
          //       color: 'red-5',
          //       textColor: 'white',
          //       icon: 'warning',
          //       message: '이메일 인증을 완료해주세요.',
          //     });
          // }
        // })
        // .catch(() => {
        //   this.$q.notify({
        //     color: 'red-5',
        //     textColor: 'white',
        //     icon: 'warning',
        //     message: '이메일과 비밀번호를 확인해주세요.',
        //   });
        // })
        
    },
    onReset() {
      this.email = '';
      this.password = '';
    },

    check() {
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.emailError = '이메일 형식에 맞게 입력해주세요.';
      else this.emailError = '';
    },
  },
  watch: {
    email: function() {
      this.check();
    },
  },
};
</script>
<style></style>
