<template>
  <el-row :gutter="24" class="el-row">
    <el-col :span="24" class="el-card">
      <div id="keywordStatsChart" style="width: 100%; height: 280px" />
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
      limit: 20,
      cData: [],
    };
  },
  props: {
    today: ''
  },
  methods: {
    // 加载banner列表数据
    init() {
      this.setChartData();
      api.getKeywordStats(this.today, this.limit).then((response) => {
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
          text: '热词图'
        },
        series: [{
          type: 'wordCloud',
          gridSize: 20,
          sizeRange: [12, 50],
          rotationRange: [-90, 90],
          shape: 'pentagon',
          textStyle: {
            normal: {
              color: function () {
                return 'rgb(' + [
                  Math.round(Math.random() * 160),
                  Math.round(Math.random() * 160),
                  Math.round(Math.random() * 160)
                ].join(',') + ')';
              }
            },
            emphasis: {
              shadowBlur: 10,
              shadowColor: '#333'
            }
          },
          data: this.cData
        }]
      }
      // 基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(document.getElementById("keywordStatsChart"));
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },
  },
};
</script>

