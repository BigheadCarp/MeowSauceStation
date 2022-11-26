<template>
  <div style="text-align: center;height: 130px">
    <el-divider></el-divider>
    <div>Copyright @2022&nbsp;MeowSauce's &nbsp;Blog</div>
    <a href="https://beian.miit.gov.cn/#/Integrated/index">
      <div style="margin-top: 20px;margin-bottom: 20px">湘ICP备2021017952号</div>
      <div style="margin-top: 20px"></div>
    </a>
      <div> 本站已运行&nbsp;{{getTime()}}天</div>

  </div>
</template>

<script>
  export default {
    name: "Foot",
    methods: {
      getTime(){
        let localTime = document.getElementById('showtime');
        let now = new Date()
        // setTimeout(time, 1000);
        let Local = now.getFullYear() + "-" + (now.getMonth() + 1) + "-" + now.getDate() ;
        let show = this.DateDiff("2022-10-22",Local);
        return show
      },
      DateDiff(sDate1,sDate2){
        let  aDate,  oDate1,  oDate2,  iDays
        aDate  =  sDate1.split("-")
        oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    //转换为12-18-2002格式
        aDate  =  sDate2.split("-")
        oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])
        iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数
        return  iDays
      }
    }
  }
</script>

<style scoped>
</style>
