<template>
  <div class="app-container">
    <table class="tableCss">
      <tr style="text-align: center;">
        <th>数据库</th>
        <th>表</th>
        <th>字段数量</th>
        <th>无注释字段数量</th>
        <th>是否有技术负责人</th>
        <th>是否有业务负责人</th>
        <th>表是否有注释</th>
        <th>过去7天是否有产出</th>
      </tr>
      <tr v-for="item in list" style="text-align: center;">
        <td>{{ item.databaseName }}</td>
        <td>{{ item.tableName }}</td>
        <td>{{ item.fieldsNumber }}</td>
        <td>{{ item.missingCommentFieldsNumber }}</td>
        <td>{{ item.hasTechnicalOwner }}</td>
        <td>{{ item.hasBusinessOwner }}</td>
        <td>{{ item.hasTableComment }}</td>
        <td>{{ item.hasOutputLastSevenDay }}</td>
      </tr>
    </table>
  </div>
</template>

<script>
import api from '@/api/govern/govern';

export default {
  data() {
    return {
      list: []
    }
  },
  mounted() {
    api.getHiveMetadataScore().then(response => {
      this.list = response.data;
    })
  }
}
</script>

<style scoped>
.tableCss {
  width: 100%;
  margin-top: 10px;
  border: 1px solid #ebeef5;
}

table>tr>th {
  padding: 10px;
}

table>tr>td {
  padding: 10px;
}

button {
  margin: 10px;
}
</style>