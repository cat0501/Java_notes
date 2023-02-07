<template>
  <!--头信息-->
  <div class="app-container">
    <div>
      <el-row :gutter="24" class="el-row">
        <el-col :span="24" class="el-card">
          <div class="grid-content bg-purple">
            <div label-width="30px" size="mini" style=" text-align:left;">
              {{ nowTime }}
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <el-row :gutter="40" class="el-row">
      <el-col :span="12" class="el-card">
        <!--省市热力图-->
        <!--:today="today"参数的含义：指在子页面需要此参数，加上此可以进行传递，子页面需要通过
        props:{
          today:''
        },
        获取
        -->
        <province ref="province" :today="today" />
        <keywordStats ref="keywordStats" :today="today" />
      </el-col>
      <el-col :span="12" class="el-card">
        <div class="grid-content bg-purple">
          <!--品类销售额占比-->
          <category3 ref="category3" :today="today" />
          <!--品牌销售排行-->
          <trademark ref="trademark" :today="today" />
        </div>
      </el-col>
    </el-row>
    <!--热门商品销售-->
  </div>
</template>

<script>
import category3 from '@/views/statistics/real/category3'
import trademark from '@/views/statistics/real/trademark'
import province from '@/views/statistics/real/province'
import visitor from '@/views/statistics/real/visitor'
import keywordStats from '@/views/statistics/real/keywordStats'

export default {
  // 注册组件
  components: {
    category3, trademark, province, visitor, keywordStats
  },
  data() {
    return {
      nowTime: '',
      // 传到子页面的参数
      today: 20221219,
      second: 1,
      timer: null, //定时器名称
    }
  },
  mounted() {
    this.nowTimes();
    this.timer = setInterval(() => {
      setTimeout(this.refresh, 0)
    }, 1000 * 60)
  },
  methods: {
    // // 加载banner列表数据
    dateChange() {
      this.refresh();
    },
    //显示当前时间（年月日时分秒）
    timeFormate(timeStamp) {
      let year = new Date(timeStamp).getFullYear();
      let month = new Date(timeStamp).getMonth() + 1 < 10 ? "0" + (new Date(timeStamp).getMonth() + 1) : new Date(timeStamp).getMonth() + 1;
      let date = new Date(timeStamp).getDate() < 10 ? "0" + new Date(timeStamp).getDate() : new Date(timeStamp).getDate();
      let hh = new Date(timeStamp).getHours() < 10 ? "0" + new Date(timeStamp).getHours() : new Date(timeStamp).getHours();
      let mm = new Date(timeStamp).getMinutes() < 10 ? "0" + new Date(timeStamp).getMinutes() : new Date(timeStamp).getMinutes();
      let ss = new Date(timeStamp).getSeconds() < 10 ? "0" + new Date(timeStamp).getSeconds() : new Date(timeStamp).getSeconds();
      this.nowTime = year + "年" + month + "月" + date + "日" + " " + hh + ":" + mm + ':' + ss;
      this.today = year + "" + month + "" + date;
    },
    nowTimes() {
      this.timeFormate(new Date());
      setInterval(this.nowTimes, 1000);
      this.clear()
    },
    clear() {
      clearInterval(this.nowTimes)
      this.nowTimes = null;
    },
    radioChange() {
      this.refresh();
    },
    refresh() {
      this.$refs.category3.init()
      this.$refs.spu.init()
      this.$refs.trademark.init()
      this.$refs.province.init()
      this.$refs.visitor.init()
      this.$refs.midStatsHr.init()
      this.$refs.keywordStats.init()
    }
  },
  beforeDestroy() {
    clearInterval(this.timer);
    this.timer = null;
  }
}
</script>

