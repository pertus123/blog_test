<template>
  <div id="q-app">
    <Header></Header>
    <router-view :key="$route.fullPath"/>
  </div>
</template>
<script>
import Header from '../src/components/Header'
export default {
  name: 'App',
  components: {
    Header,
  }
}
</script>
