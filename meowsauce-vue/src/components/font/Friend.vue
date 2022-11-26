<template>
  <el-row>
    <el-col :xs="0" :lg="24">
      <el-card class="box-card">
        <h2>👨‍🚀 关于本站的链接信息</h2>
        <div style="padding-left: 20px;font-size: 17px">
          <p>【本站名称】 Meowsauce's Blog</p>
          <p>【本站介绍】 喵酱的小站</p>
          <p>【本站地址】
            <a href="#">https://meowsauce.cn</a>
          </p>
          <p>【本站图标】
            <a href="#">https://q1.qlogo.cn/g?b=qq&nk=3232054541&s=100</a>
          </p>
        </div>
        <h2 style="margin-top: 60px;padding: 0!important;">👨‍👨‍👦‍👦我的朋友们</h2>
        <el-card class="friends" v-for="(item,index) in friends" :key="index">
          <a @click="openFriendAddress(item.friendAddress)">
            <el-avatar :size="80" :src="item.friendIcon" class="friendHead"></el-avatar>
            <span class="friendName">{{item.friendName}}</span>
            <br>
            <span class="friendIntroduce">{{item.friendIntroduce}}</span>
          </a>
        </el-card>
        <div style="margin-top: 20px;margin-left: 20px">
          <strong>很荣幸能与贵站做链接！</strong>
          <p></p>
          <p>以上排名不分先后，谢谢大家的支持。</p>
          <p>首先要说的是，友情链接，交换的意义在友情，而不在链接，如果你是为了一定目的或者是人气而来，我觉得还是别交换了，我对贵站可能不会有多大兴趣。</p>
          <p>请先在贵站上做好本站链接，再提交贵站链接送到邮箱<a href="mailto:migratorybird2020@outlook.com">migratorybird2020@outlook.com</a>
            ，将于24小时内受理完毕。
          </p>
          <p>欢迎同类站点交换友情链接，提交链接前请确认你的网站没有违反法律、法规的内容,没有违背社会道德之内容！</p>
          <p>将不定期检查死链等无效链接，如果发现贵站取消本站链接，我也将删除你的，恕不另行通知。</p>
          <p>本站所有友链排列顺序为“随机”，所以请不要刻意要求排名顺序。</p>
          <p>是否成为友情链接与PR、Alexa排名无关。</p>
        </div>
      </el-card>
    </el-col>
    <el-col :xs="24" :lg="0">
      <el-card class="box-cardByMobile">
        <h2>👨‍🚀 关于本站的链接信息</h2>
        <div style="padding-left: 20px;font-size: 17px">
          <p>【本站名称】 Meowsauce's Blog</p>
          <p>【本站介绍】 喵酱的小站</p>
          <p>【本站地址】
            <a href="#">https://meowsauce.cn</a>
          </p>
          <p>【本站图标】
            <a href="#">https://q1.qlogo.cn/g?b=qq&nk=3232054541&s=100</a>
          </p>
        </div>
        <h2 style="margin-top: 60px;padding: 0!important;">👨‍👨‍👦‍👦我的朋友们</h2>
        <el-card class="friends" v-for="(item,index) in friends" :key="index">
          <a @click="openFriendAddress(item.friendAddress)">
            <el-avatar :size="80" :src="item.friendIcon" class="friendHead"></el-avatar>
            <span class="friendName">{{item.friendName}}</span>
            <br>
            <span class="friendIntroduce">{{item.friendIntroduce}}</span>
          </a>
        </el-card>
        <div style="margin-top: 20px;margin-left: 20px">
          <strong>很荣幸能与贵站做链接！</strong>
          <p></p>
          <p>以上排名不分先后，谢谢大家的支持。</p>
          <p>首先要说的是，友情链接，交换的意义在友情，而不在链接，如果你是为了一定目的或者是人气而来，我觉得还是别交换了，我对贵站可能不会有多大兴趣。</p>
          <p>请先在贵站上做好本站链接，再提交贵站链接送到邮箱<a href="mailto:migratorybird2020@outlook.com">migratorybird2020@outlook.com</a>
            ，将于24小时内受理完毕。
          </p>
          <p>欢迎同类站点交换友情链接，提交链接前请确认你的网站没有违反法律、法规的内容,没有违背社会道德之内容！</p>
          <p>将不定期检查死链等无效链接，如果发现贵站取消本站链接，我也将删除你的，恕不另行通知。</p>
          <p>本站所有友链排列顺序为“随机”，所以请不要刻意要求排名顺序。</p>
          <p>是否成为友情链接与PR、Alexa排名无关。</p>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
  export default {
    name: "Friend",
    data() {
      return {
        friends: [],
        circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      }
    },
    mounted() {
      this.getAllFriends()
      this.$emit('Page', '朋友们')
    },
    methods: {
      getAllFriends() {
        this.$axios.get('/api/friends/getAllFriend').then(resp => {
          if (resp && resp.data.code === 200) {
            this.friends = resp.data.result
          }
        })
      },
      openFriendAddress(address) {
        console.log(address)
        window.location.href = address
        // window.open(window.location.href,"_blank")
      }
    }
  }
</script>

<style scoped>
  /deep/ .el-card__body, .el-main {
    padding: 0;
  }

  .box-card {
    padding: 20px 20px 30px 20px;
    width: 926px;
  }
  .box-cardByMobile{
    margin-left: 20px;
    padding: 20px 20px 30px 20px;
    width: 300px;
  }

  a {
    color: blue;
    text-decoration: none;
  }

  .friends {
    margin-left: 20px;
    margin-bottom: 10px;
    display: inline-block;
    width: 276px;
    height: 106px;
  }

  .friendHead {
    margin-top: 14px;
    margin-left: 11px
  }

  .friendName {
    padding-left: 16px;
    position: absolute;
    margin-top: 31px;
    font-weight: bold
  }

  .friendIntroduce {
    float: right;
    margin-top: -38px;
    margin-right: 99px;
    font-size: 14px
  }

  a {
    text-decoration: none;
    color: black;
    cursor: pointer;
  }
</style>
