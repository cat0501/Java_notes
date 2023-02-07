<template>
  <el-row :gutter="24" class="el-row">
    <el-col :span="24" class="el-card">
      <div class="grid-content bg-purple">
        <div label-width="80px" size="mini" style="margin-top: 60px;text-align:center;">
          <div class="trend-overview-date">今日总交易额</div>
          <div class="trend-overview-number">{{ dauTotal }}</div>
        </div>
      </div>
      <div id="mapChart" style="width: 100%; height: 400px" />
    </el-col>
  </el-row>
</template>

<script>
import * as echarts from 'echarts';
import axios from "axios";
import api from "@/api/statistics/real/realtime";
export default {
  // 生命周期函数：内存准备完毕，页面渲染成功
  mounted() {
    this.init();
    this.getGMV();
  },

  data() {
    return {
      mapJson: {},
      dauTotal: 0,
      areaData: [],
      min: 0,
      max: 0
    };
  },
  props: {
    today: ''
  },
  methods: {
    initData(areaData) {
      if (areaData && areaData.length > 0) {
        this.min = areaData[0]['value'];
        this.max = areaData[areaData.length - 1]['value'];
      }
    },
    init() {
      api.getProvinceStats(this.today).then(response => {
        this.areaData = response.data;
        this.initData(this.areaData);
        this.setChartData();
      }).catch(response => {
        console.log('失败' + response);
      })
    },
    getGMV() {
      api.getGMV(this.today).then(response => {
        this.dauTotal = response.data;
      }).catch(response => {
        console.log('失败' + response);
      })
    },
    setChartData() {
      // 基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(document.getElementById("mapChart"));

      myChart.showLoading();

      axios.get("china.json").then((response) => {
        this.mapJson = response.data;

        myChart.hideLoading();

        echarts.registerMap("CHN", this.mapJson);
        const option = {
          title: {
            text: "各省份交易统计",
            left: "center",
          },
          tooltip: {
            trigger: "item",
            showDelay: 0,
            transitionDuration: 0.2,
            formatter: function (params) {
              var value = (params.value + "").split(".");
              value = value[0].replace(/(\d{1,3})(?=(?:\d{3})+(?!\d))/g, "$1,");
              return params.seriesName + "<br/>" + params.name + ": " + value;
            },
          },
          visualMap: {
            left: "right",
            min: this.min,
            max: this.max,
            inRange: {
              color: ["#FACE9C", "#B6292B"]
            },
            text: ["High", "Low"], // 文本，默认为数值文本
            calculable: true,
          },
          toolbox: {
            show: true,
            //orient: 'vertical',
            left: "right",
            top: "top",
            feature: {
              dataView: { readOnly: false },
              restore: {},
              saveAsImage: {},
            },
          },
          series: [
            {
              type: "map",
              roam: true,
              map: "CHN",
              emphasis: {
                label: {
                  show: true,
                },
              },
              data: this.areaData,
            },
          ],
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
      });
    }
  }
};
</script>

