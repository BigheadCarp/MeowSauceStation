<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="11">
        <el-card class="updateMsg">
          <div slot="header">
            <span>更新信息</span>
          </div>
          <el-timeline v-for="(value,index) in pigeonhole" :key="index">
            <el-timeline-item :timestamp="value.pigeonholeDate" placement="top">
              <h4>{{value.pigeonholeContent}}</h4>
            </el-timeline-item>
          </el-timeline>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="blogMsg">
          <div slot="header">
            <span>博客信息</span>
          </div>
          <div>
            <el-row style="font-weight: bold">
              <el-col :lg="8">
                <span>文章</span>
              </el-col>
              <el-col :lg="8">
                <span>评论</span>
              </el-col>
              <el-col :lg="8">
                <span>标签</span>
              </el-col>
            </el-row>
          </div>
          <div style="margin-top: 10px">
            <el-row>
              <el-col :lg="8">
                <span>{{totalDTO.articleTotal}}</span>
              </el-col>
              <el-col :lg="8">
                <span>{{totalDTO.commentTotal}}</span>
              </el-col>
              <el-col :lg="8">
                <span>{{totalDTO.labelTotal}}</span>
              </el-col>
            </el-row>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    name: "index",
    data() {
      return {
        totalDTO: [],
        pigeonhole: []
      }
    },
    mounted() {
      this.getAllUpdateMsg()
      this.getTotal()
    },
    methods: {
      getAllUpdateMsg() {
        this.$axios.get('/api/pigeonhole/getAllUpdateMsg').then(resp => {
          if (resp && resp.data.code === 200) {
            this.pigeonhole = resp.data.result
          }
        })
      },
      getTotal() {
        this.$axios.get('/api/pigeonhole/getTotal').then(resp => {
          if (resp && resp.data.code === 200) {
            this.totalDTO = resp.data.result
          }
        })
      },
    }
  }
</script>

<style scoped>
  .updateMsg {
    width: 480px;
  }

  .blogMsg {
    width: 480px;
  }
</style>
