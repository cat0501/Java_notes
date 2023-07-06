package basic.java8;

import java.sql.*;
import java.util.*;

/**
 * @author Lemonade
 * @description
 * @updateTime 2023/6/25 18:04
 */
public class Atest {
    public static void main(String[] args) {
    }

    private String impalaUrl = "jdbc:hive2://10.11.14.36:21050,10.11.14.37:21050/default;auth=noSasl;user=root";


    public void selectResult(String sql, int pageNo, int pageSize) {
        Map<String,List> resultMap = new HashMap<String,List>();
        //sql处理
        sql=sql.replace(";","");
        sql="SELECT a.* FROM ("+sql+") a order by 1 "+" LIMIT "+pageSize+" offset "+(pageNo-1)*pageSize;
        //建立连接
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            Class.forName( "org.apache.hive.jdbc.HiveDriver");
            con = DriverManager.getConnection(impalaUrl);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            //用于获取展示列表头
            List<String> columnNamesList = new ArrayList<>();
            ResultSetMetaData metaData = rs.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);
                columnNamesList.add(columnName);
            }
            resultMap.put("columnList",columnNamesList);

            //获取数据
            List<Map<String, Object>> dataList = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> returnMap = new HashMap<String, Object>();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    returnMap.put(columnNamesList.get(i),rs.getString(i + 1));
                }
                dataList.add(returnMap);
            }
            resultMap.put("dataList",dataList);
        } catch (Exception e) {
            throw new RuntimeException("查询出错，请联系管理员");
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("查询出错，请联系管理员");
            }
        }
    }

}
