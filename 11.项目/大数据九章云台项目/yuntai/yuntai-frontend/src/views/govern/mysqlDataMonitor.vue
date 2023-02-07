<template>
    <div class="app-container">
        <table class="tableCss">
            <tr style="text-align: center;">
                <th>数据库</th>
                <th>表</th>
                <th>字段</th>
                <th>空值率</th>
            </tr>
            <tr v-for="item in list" style="text-align: center;">
                <td>{{ item.databaseName }}</td>
                <td>{{ item.tableName }}</td>
                <td>{{ item.fieldName }}</td>
                <td>{{ item.fieldNullRate }}</td>
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
        api.getMySQLDataMonitor().then(response => {
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