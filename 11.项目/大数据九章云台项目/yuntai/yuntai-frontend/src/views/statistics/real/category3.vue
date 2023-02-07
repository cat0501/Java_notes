<template>
  <el-row :gutter="12" class="el-row">
    <el-col :span="24" class="el-card">
      <div id="categoryChart" style="width: 100%; height: 500px" />
    </el-col>
  </el-row>
</template>

<script>
import * as echarts from 'echarts';
import api from "@/api/statistics/real/realtime";
export default {
  mounted() {
    this.init()
  },
  data() {
    return {
      cData: [],
    };
  },
  props: {
    today: ''
  },
  methods: {
    init() {
      this.setChartData();
      api.getProductStatsGroupByCategory3(this.today).then((response) => {
        this.cData = response.data;
        this.setChartData();
      })
        .catch((response) => {
          console.log("失败" + response);
        });
    },
    setChartData() {
      const option = {
        title: {
          text: "品类的销售额占比",
          subtext: this.dateRange,
          left: "left",
        },
        tooltip: {//提示框，可以在全局也可以在
          trigger: 'item',  //提示框的样式
          formatter: "{a} <br/>{b}: {c} ({d}%)",
          color: '#000', //提示框的背景色
          textStyle: { //提示的字体样式
            color: "black",
          }
        },
        legend: {  //图例
          orient: 'vertical',  //图例的布局，竖直    horizontal为水平
          x: 'right',//图例显示在右边
          data: ['平板电脑', '手机', '香水', '唇部'],
          textStyle: {    //图例文字的样式
            color: '#333',  //文字颜色
            fontSize: 12    //文字大小
          }
        },
        series: [
          {
            name: '商品类别',
            type: 'pie', //环形图的type和饼图相同
            radius: ['50%', '70%'],//饼图的半径，第一个为内半径，第二个为外半径
            avoidLabelOverlap: false,
            color: ['#D1FBEF', '#F9D858', '#4CD0DD', '#DF86F0'],
            label: {
              normal: {  //正常的样式
                show: true,
                position: 'left'
              },
              emphasis: { //选中时候的样式
                show: true,
                textStyle: {
                  fontSize: '20',
                  fontWeight: 'bold'
                }
              }
            },  //提示文字
            labelLine: {
              normal: {
                show: false
              }
            },
            data: this.cData
          }
        ]
      };
      // 基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(document.getElementById("categoryChart"));

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },
  },
};
</script>

