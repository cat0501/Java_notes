package poolTest;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;



@Slf4j
@Component
public class ConnectionHivePool implements InitializingBean {

    @Value("${mysql.driverClassName:org.apache.hive.jdbc.HiveDriver}")
    private String driverClassName;
    @Value("${mysql.url}")
    private String url;
    @Value("${mysql.username:root}")
    private String username;
    @Value("${mysql.password}")
    private String password;

    DruidDataSource dataSource;

    public ConnectionHivePool() {
    }

    @Override
    public void afterPropertiesSet() {
        Properties properties = new Properties();
        // 设置连接池配置参数
        properties.setProperty("driverClassName", driverClassName);
        properties.setProperty("url", url);
        properties.setProperty("username", username);
        properties.setProperty("password", password);
        try {
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
