<template>
  <el-row>
    <el-col :xs="0" :lg="24">
      <div style="width: 926px">
        <el-card class="box-card">
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>正文</el-breadcrumb-item>
          </el-breadcrumb>
          <el-divider></el-divider>
          <h3>🌴MeowSauce</h3>
          <ul>
            <li>🌏目前是一名大三在校生</li>
            <br>
            <li>📚就读于长沙民政职业技术学院</li>
            <br>
            <li>🦥拖延症晚期</li>
            <br>
            <li>🎮喜欢听歌,比较喜欢玩FPS类游戏</li>
            <br>
            <li>👊社交恐惧症,间歇性想躺平</li>
          </ul>
          <h3>🗼关于博客</h3>
          <p>后台是参照CSDN的一位博主的文章做的,你们可以去看下
            <a href="https://blog.csdn.net/Neuf_Soleil?type=blog">Evan-Nightly</a>
          </p>
          <p>前台的布局是参照一个博客网站<a href="https://zhaoq.me/">ZhaoQuinn</a>不过他(她)是用博客框架做的,我想用我学的东西实现一下
          </p>
          <p>本博客已开源,
            <a href="https://gitee.com/migratorybird2020/meow-sauce-station">开源地址</a>
          </p>
          <h3>📞联系我</h3>
          <p>邮箱: migratorybird2020@outlook.com</p>
          <h3>💼许可</h3>
          <p>除非单独说明，本站作品采用<a href="https://creativecommons.org/licenses/by-sa/4.0/deed.zh">知识共享署名-相同方式共享 4.0 国际许可协议</a>
            进行许可。
          </p>
          <h3>🌐声明</h3>
          <div class="statement">
            <div>🏆版权属于: MeowSauce</div>
            <div>🎲作品采用<a class="text-decoration-none" target="_blank"
                          href="https://creativecommons.org/licenses/by-nc-sa/4.0/deed.zh-Hans">
              知识共享署名-非商业性使用-相同方式共享 4.0 国际许可协议</a>进行许可
            </div>
          </div>
          <br>
          <br>
          <br>
        </el-card>
        <Comment :articleId="this.articleId"></Comment>
      </div>
    </el-col>
    <el-col :xs="24" :lg="0">
      <div style="width: 340px">
        <el-card class="box-cardByMobile">
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>正文</el-breadcrumb-item>
          </el-breadcrumb>
          <el-divider></el-divider>
          <h3>🌴MeowSauce</h3>
          <ul>
            <li>🌏目前是一名大三在校生</li>
            <br>
            <li>📚就读于长沙民政职业技术学院</li>
            <br>
            <li>🦥拖延症晚期</li>
            <br>
            <li>🎮喜欢听歌,比较喜欢玩FPS类游戏</li>
            <br>
            <li>👊社交恐惧症,间歇性想躺平</li>
          </ul>
          <h3>🗼关于博客</h3>
          <p>后台是参照CSDN的一位博主的文章做的,你们可以去看下
            <a href="https://blog.csdn.net/Neuf_Soleil?type=blog">Evan-Nightly</a>
          </p>
          <p>前台的布局是参照一个博客网站<a href="https://zhaoq.me/">ZhaoQuinn</a>不过他(她)是用博客框架做的,我想用我学的东西实现一下
          </p>
          <p>本博客已开源,
            <a href="https://gitee.com/migratorybird2020/meow-sauce-station">开源地址</a>
          </p>
          <h3>📞联系我</h3>
          <p>邮箱: migratorybird2020@outlook.com</p>
          <h3>💼许可</h3>
          <p>除非单独说明，本站作品采用<a href="https://creativecommons.org/licenses/by-sa/4.0/deed.zh">知识共享署名-相同方式共享 4.0 国际许可协议</a>
            进行许可。
          </p>
          <h3>🌐声明</h3>
          <div class="statement">
            <div>🏆版权属于: MeowSauce</div>
            <div>🎲作品采用<a class="text-decoration-none" target="_blank"
                          href="https://creativecommons.org/licenses/by-nc-sa/4.0/deed.zh-Hans">
              知识共享署名-非商业性使用-相同方式共享 4.0 国际许可协议</a>进行许可
            </div>
          </div>
          <br>
          <br>
          <br>
        </el-card>
        <Comment :articleId="this.articleId"></Comment>
      </div>
    </el-col>
  </el-row>
</template>

<script>
  import Comment from "./Comment";

  export default {
    name: "About",
    data() {
      return {
        articleId: 0,
        friends: [],
        circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      }
    },
    components: {Comment},
    mounted() {
      this.$emit("Page", "关于我")
      this.articleId = -1;
    },
    methods: {}
  }
</script>

<style scoped>

  .box-card {
    /*padding: 20px;*/
    width: 926px;
  }
  .box-cardByMobile{
    margin-left: 18px;
    width: 340px;
  }

  a {
    color: blue;
    text-decoration: none;
  }

  .statement {
    padding: 1rem;
    margin-top: 1.5rem;
    line-height: 1.5rem;
    border-radius: 0.25rem;
    background: #f8f9fa;
  }
</style>
