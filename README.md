![image](https://user-images.githubusercontent.com/85054218/204090842-4471c470-0b08-42f0-b124-fc4cc586f8b7.png)
---
这是一个常见的个人博客项目,能够基本实现一个博客应该完成的功能,项目比较简单,结构也比较简单,使用SpringBoot+MybatisPlus+Vue,由于项目刚刚完成,可能会有一些Bug且未充分考虑安全问题,
服务器可能随时关闭,还请谅解。<br>后台管理借用了**白卷**https://github.com/Antabot/White-Jotter,后续应该会改成若依的<br>
[博客地址](www.meowsauce.cn) www.meowsauce
# 整体效果
PC首页 <br>
![image](https://user-images.githubusercontent.com/85054218/204115412-70c26991-b7a9-41cc-aaeb-9ec1211f645f.png)
![image](https://user-images.githubusercontent.com/85054218/204115427-296269ec-e22e-4cc4-9934-9be599662e43.png)<br>
移动端首页<br>
![image](https://user-images.githubusercontent.com/85054218/204115690-8a87dd45-2b9e-489e-a104-652ee9b24a2c.png)<br>
归档页面<br>
![image](https://user-images.githubusercontent.com/85054218/204115636-c331ae32-08a9-47c8-b941-261ce0147ce9.png)
![image](https://user-images.githubusercontent.com/85054218/204115663-a645b0a2-64d3-4235-be5f-ef40857269a8.png)<br>
文章详情页<br>
![image](https://user-images.githubusercontent.com/85054218/204115727-bfb4c3e1-dddc-4c80-ba3b-9a444fc3db33.png)<br>
后台管理<br>
![image](https://user-images.githubusercontent.com/85054218/204115791-a8cb2eac-a25c-4f4b-8b8e-ab9feb97442c.png)<br>
其他页面不再一一展示<br>
## 主要技术栈<br>
### 前端<br>
1.Vue.js<br>
2.ElementUI<br>
3.axios<br>
### 后端<br>
1.SpringBoot<br>
2.MybatisPlus<br>
3.ApacheShiro<br>
4.Spring Data Redis<br>
### 数据库 <br>
1.MySQL<br>
2.Redis<br>
### 其他工具 <br>
1.阿里云对象存储OSS <br>
2.腾讯云短信验证 <br>
## 功能<br>
### 首页<br>
- 最新文章：按照发布时间来进行倒序排序展示
- 导航栏：首页、分类、归档、碎碎念、朋友们、关于我
### 后台管理页面
- 登录注册功能
- 文章管理
  + 文章发布
  + 文章编辑
  + 文章删除
- 评论功能
- 手机端适配
### 未解决Bug
文章插入图片未上传至阿里云,<br>
安全防护等级较低<br>
手机端页面展示有差异<br>
## 项目快速开始
- 执行MeowSauce.sql创建数据库
- 开启redis
- 阿里云对象存储工具类和腾讯云短信验证服务工具类的token已移除,可自行添加
- 修改Application.yml,然后启动后端项目
- 安装npm 在idea中打开meowsauce-vue输入npm run dev 启动前端页面 注使用的是Vue2
## 感谢
感谢Evan-Nightly<https://blog.csdn.net/Neuf_Soleil?type=blog>的白卷项目实战,这个项目很大程度上都是靠着白卷做起来的<br>
感谢ZhaoQuinn的博客<https://zhaoq.me/>前台页面的展示都是一点点搬过来的<br>
菜鸟一枚,轻喷谢谢
