<template>
<div>
  <div class="flex justify-center">
    <div class="q-gutter-y-md" style="max-width: 400px">
      <q-tabs
        v-model="tab"
        narrow-indicator
        dense
        align="justify"
        class="text-grey-8"
      >
        <q-tab name="최신순" label="최신순" style="font-family: 'Noto Sans KR', sans-serif;" />
        <q-tab name="인기순" label="인기순" style="font-family: 'Noto Sans KR', sans-serif;" />
      </q-tabs>
    </div>
  </div>
  <div v-if="tab == '최신순'">
    <post-list></post-list>
  </div>
  <div v-else>
    <post-list-like></post-list-like>
  </div>
</div>
</template>
<script>
import PostList from '../pages/post/PostList'
import PostListLike from '../pages/post/PostListLike'
export default {
  components: {
    PostList,
    PostListLike
  },
  data () {
    return {
      tab: '최신순'
    }
  }
}
</script>
<style>

  
</style>