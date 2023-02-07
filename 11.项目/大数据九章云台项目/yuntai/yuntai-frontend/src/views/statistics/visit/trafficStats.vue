<template>
  <div>
    <span slot="title_slot">
      各渠道流量统计
    </span>

    <el-table height="350" :data="tableData" style="width: 100%;font-size: 15px"
      :header-cell-style="{ background: '#eef1f6', color: '#606266' }">
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="channel" label="渠道">
        <template slot-scope="scope">
          <div v-html="scope.row.channel"></div>
        </template>
      </el-table-column>
      <el-table-column prop="uvCount" label="访客人数" width="150">
      </el-table-column>
      <el-table-column prop="avgDurationSec" label="会话平均停留时长" width="300">
      </el-table-column>
      <el-table-column prop="avgPagCount" label="会话平均浏览页面数" width="300">
      </el-table-column>
      <el-table-column prop="svCount" label="会话数" width="150">
      </el-table-column>
      <el-table-column prop="bounceRate" label="跳出率" width="150">
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <el-pagination :current-page="page" :total="total" :page-size="limit" :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      style="padding: 30px 0; text-align: center;" layout="sizes, prev, pager, next, jumper, ->, total, slot"
      @current-change="fetchData" @size-change="changeSize" />
  </div>
</template>

<script>
import api from "@/api/statistics/visit/visit";

export default {
  data() {
    return {
      itemName: "",
      date: "",
      total: 0, // 数据库中的总记录数
      page: 1, // 默认页码
      limit: 10, // 每页记录数
      tableData: [],
      searchObj: {
        recentDays: this.$parent.recentDays,
        dt: this.$parent.curDate
      }
    }
  },
  // 生命周期函数：内存准备完毕，页面渲染成功
  mounted() {
    this.init()
  },
  methods: {
    init() {
      this.searchObj.recentDays = this.$parent.recentDays
      this.searchObj.dt = this.$parent.curDate
      this.fetchData(this.page)
    },
    // 当页码发生改变的时候
    changeSize(size) {
      console.log(size)
      this.limit = size
      this.fetchData(1)
    },

    // 加载banner列表数据
    fetchData(page = 1) {
      console.log('翻页。。。' + page);
      // 异步获取远程数据（ajax）
      this.page = page;
      /**
       * 各渠道流量统计接口
       */
      api.getTrafficStats(this.page, this.limit, this.searchObj)
        .then((response) => {
          this.tableData = response.data.records;
          this.total = response.data.total;
        })
        .catch((response) => {
          console.log("失败" + response)
        })
    }
  }
}
</script>

<style scoped>
em {
  font-style: normal;
  color: red;
}
</style>
