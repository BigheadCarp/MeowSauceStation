<template>
  <el-row>
    <el-col :xs="0" :lg="24">
      <div style="width: 926px">
        <el-card class="box-card">
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/index' }">é¦é¡µ</el-breadcrumb-item>
            <el-breadcrumb-item>æ­£æ</el-breadcrumb-item>
          </el-breadcrumb>
          <el-divider></el-divider>
          <h3>ð´MeowSauce</h3>
          <ul>
            <li>ðç®åæ¯ä¸åå¤§ä¸å¨æ ¡ç</li>
            <br>
            <li>ðå°±è¯»äºé¿æ²æ°æ¿èä¸ææ¯å­¦é¢</li>
            <br>
            <li>ð¦¥æå»¶çææ</li>
            <br>
            <li>ð®åæ¬¢å¬æ­,æ¯è¾åæ¬¢ç©FPSç±»æ¸¸æ</li>
            <br>
            <li>ðç¤¾äº¤ææ§ç,é´æ­æ§æ³èººå¹³</li>
          </ul>
          <h3>ð¼å³äºåå®¢</h3>
          <p>åå°æ¯åç§CSDNçä¸ä½åä¸»çæç« åç,ä½ ä»¬å¯ä»¥å»çä¸
            <a href="https://blog.csdn.net/Neuf_Soleil?type=blog">Evan-Nightly</a>
          </p>
          <p>åå°çå¸å±æ¯åç§ä¸ä¸ªåå®¢ç½ç«<a href="https://zhaoq.me/">ZhaoQuinn</a>ä¸è¿ä»(å¥¹)æ¯ç¨åå®¢æ¡æ¶åç,ææ³ç¨æå­¦çä¸è¥¿å®ç°ä¸ä¸
          </p>
          <p>æ¬åå®¢å·²å¼æº,
            <a href="https://gitee.com/migratorybird2020/meow-sauce-station">å¼æºå°å</a>
          </p>
          <h3>ðèç³»æ</h3>
          <p>é®ç®±: migratorybird2020@outlook.com</p>
          <h3>ð¼è®¸å¯</h3>
          <p>é¤éåç¬è¯´æï¼æ¬ç«ä½åéç¨<a href="https://creativecommons.org/licenses/by-sa/4.0/deed.zh">ç¥è¯å±äº«ç½²å-ç¸åæ¹å¼å±äº« 4.0 å½éè®¸å¯åè®®</a>
            è¿è¡è®¸å¯ã
          </p>
          <h3>ðå£°æ</h3>
          <div class="statement">
            <div>ðçæå±äº: MeowSauce</div>
            <div>ð²ä½åéç¨<a class="text-decoration-none" target="_blank"
                          href="https://creativecommons.org/licenses/by-nc-sa/4.0/deed.zh-Hans">
              ç¥è¯å±äº«ç½²å-éåä¸æ§ä½¿ç¨-ç¸åæ¹å¼å±äº« 4.0 å½éè®¸å¯åè®®</a>è¿è¡è®¸å¯
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
            <el-breadcrumb-item :to="{ path: '/index' }">é¦é¡µ</el-breadcrumb-item>
            <el-breadcrumb-item>æ­£æ</el-breadcrumb-item>
          </el-breadcrumb>
          <el-divider></el-divider>
          <h3>ð´MeowSauce</h3>
          <ul>
            <li>ðç®åæ¯ä¸åå¤§ä¸å¨æ ¡ç</li>
            <br>
            <li>ðå°±è¯»äºé¿æ²æ°æ¿èä¸ææ¯å­¦é¢</li>
            <br>
            <li>ð¦¥æå»¶çææ</li>
            <br>
            <li>ð®åæ¬¢å¬æ­,æ¯è¾åæ¬¢ç©FPSç±»æ¸¸æ</li>
            <br>
            <li>ðç¤¾äº¤ææ§ç,é´æ­æ§æ³èººå¹³</li>
          </ul>
          <h3>ð¼å³äºåå®¢</h3>
          <p>åå°æ¯åç§CSDNçä¸ä½åä¸»çæç« åç,ä½ ä»¬å¯ä»¥å»çä¸
            <a href="https://blog.csdn.net/Neuf_Soleil?type=blog">Evan-Nightly</a>
          </p>
          <p>åå°çå¸å±æ¯åç§ä¸ä¸ªåå®¢ç½ç«<a href="https://zhaoq.me/">ZhaoQuinn</a>ä¸è¿ä»(å¥¹)æ¯ç¨åå®¢æ¡æ¶åç,ææ³ç¨æå­¦çä¸è¥¿å®ç°ä¸ä¸
          </p>
          <p>æ¬åå®¢å·²å¼æº,
            <a href="https://gitee.com/migratorybird2020/meow-sauce-station">å¼æºå°å</a>
          </p>
          <h3>ðèç³»æ</h3>
          <p>é®ç®±: migratorybird2020@outlook.com</p>
          <h3>ð¼è®¸å¯</h3>
          <p>é¤éåç¬è¯´æï¼æ¬ç«ä½åéç¨<a href="https://creativecommons.org/licenses/by-sa/4.0/deed.zh">ç¥è¯å±äº«ç½²å-ç¸åæ¹å¼å±äº« 4.0 å½éè®¸å¯åè®®</a>
            è¿è¡è®¸å¯ã
          </p>
          <h3>ðå£°æ</h3>
          <div class="statement">
            <div>ðçæå±äº: MeowSauce</div>
            <div>ð²ä½åéç¨<a class="text-decoration-none" target="_blank"
                          href="https://creativecommons.org/licenses/by-nc-sa/4.0/deed.zh-Hans">
              ç¥è¯å±äº«ç½²å-éåä¸æ§ä½¿ç¨-ç¸åæ¹å¼å±äº« 4.0 å½éè®¸å¯åè®®</a>è¿è¡è®¸å¯
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
      this.$emit("Page", "å³äºæ")
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
