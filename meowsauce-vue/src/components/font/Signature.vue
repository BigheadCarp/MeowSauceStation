<template>
  <el-row>
    <el-col :xs="0" :lg="24">
  <el-card class="box-card">
    <div class="block">
      <el-timeline>
        <el-timeline-item
          v-for="(sign, index) in signature"
          :key="index"
          placement="top"
          :color="style[index % 5].color"
          :icon="sign.signatureIcon"
          :timestamp="'发表于'+sign.realDate">

          <h2>{{sign.signatureContent}}</h2>
        </el-timeline-item>
      </el-timeline>
    </div>
  </el-card>
    </el-col>
    <el-col :xs="24" :lg="0">
      <el-card class="box-cardByMobile">
        <div class="block">
          <el-timeline>
            <el-timeline-item
              v-for="(sign, index) in signature"
              :key="index"
              placement="top"
              :color="style[index % 5].color"
              :icon="sign.signatureIcon"
              :timestamp="'发表于'+sign.realDate"
            style="margin-left: -13px">
              <h2>{{sign.signatureContent}}</h2>
            </el-timeline-item>
          </el-timeline>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
  export default {
    name: "Signature",
    data() {
      return {
        signature: [],
        style: [{color: '#0d6efd'}, {
          color: '#6610f2'
        }, {color: '#20c997'}, {
          color: '#dc3545'
        }, {
          color: '#fd7e14'
        }]
      }
    },
    mounted() {
      this.getAllSignature()
      this.$emit('Page','碎碎念')
    },
    methods: {
      getAllSignature() {
        this.$axios.get('/api/signature/getAll').then(resp => {
          if (resp && resp.data.code === 200) {
            this.signature = resp.data.result
          }
        })
      }
    }
  }
</script>

<style scoped>
  /deep/ .el-timeline-item__node--normal {
    top: -10px;
    left: -13px;
    width: 35px;
    height: 35px;
  }

  /deep/ .el-timeline-item__icon {
    font-size: 25px;
  }

  /deep/ .el-timeline-item{
    padding-bottom: 30px;
  }
  /deep/ .el-timeline-item__timestamp{
    color: black;
  }
  .box-card {
    padding-top: 40px;
    border: 1px solid grey;
    width: 926px;
  }
  .box-cardByMobile{
    padding-top: 40px;
    border: 1px solid grey;
    width: 340px;
    margin-left: 20px;
  }
</style>
