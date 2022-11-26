<template>
  <div class="outDiv">
    {{fatherMsg}}
  </div>
</template>

<script>
  export default {
    name: "contrast",
    data() {
      return {
      }
    },
    props:[
      "fatherMsg"
    ]
  }
</script>

<style scoped>
  /*最外层div 确定评论取大小*/
  .outDiv {
    background: red;
    width: 400px;
    height: 400px;
  }
</style>
