package jdbc;

import com.clickhouse.jdbc.ClickHouseDataSource;

import java.sql.*;
import java.util.Properties;
import java.util.Random;

public class ClickhouseJdbcTest {

    public static void main(String[] args) {

        int nextInt = new Random().nextInt();

        String url = "jdbc:clickhouse://10.11.14.31:28123/bitnei_ods?&session_id="+nextInt;
        System.out.println(url);
        String user = "default";
        String password = "default";
        String catalog = "bitnei_ods";

        // Properties properties = new Properties();
        // properties.setProperty("url", url);
        // properties.setProperty("user", user);
        // properties.setProperty("password", password);
        // properties.setProperty("catalog", catalog);

        Connection conn = null;

        try {
            // 创建 JDBC 连接
            conn = DriverManager.getConnection(url, user, password);

            // ClickHouseDataSource dataSource = new ClickHouseDataSource(url, properties);
            // try (Connection conn = dataSource.getConnection("default", "default");
            //      Statement stmt = conn.createStatement()) {
            //     stmt.executeQuery("USE bitnei_ods");
            //     ResultSet rs = stmt.executeQuery("DESCRIBE path04");
            //     while (rs.next()) {
            //          System.out.println(rs.getString("name"));
            //          System.out.println(rs.getString("type"));
            //      }
            // }


            // // 获取数据库元数据
            // DatabaseMetaData metaData = conn.getMetaData();
            // // 获取所有表名
            // ResultSet tables = metaData.getTables(null, catalog, null, new String[]{"TABLE"});
            // // 遍历表名并输出
            // while (tables.next()) {
            //     String tableName = tables.getString("TABLE_NAME");
            //     System.out.println(tableName);
            // }

            Statement statement = conn.createStatement();
            statement.execute("USE bitnei_ods");
            ResultSet rs = statement.executeQuery("DESCRIBE path04");
            while (rs.next()) {
                System.out.println(rs.getString("name"));
                System.out.println(rs.getString("type"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭 JDBC 连接
            // if (conn != null) {
            //     try {
            //         conn.close();
            //     } catch (SQLException e) {
            //         e.printStackTrace();
            //     }
            // }
        }
    }
}
