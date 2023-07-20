package PoolTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author cat
 * @description
 * @date 2023/7/20 21:26
 */

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class TestGetConnection {

    public static void main(String[] args) {
        SpringApplication.run(TestGetConnection.class, args);
    }

    //@Test
    //public void test1(){
    //    Connection connection;
    //    try {
    //        connection = connectionHivePool.getConnection();
    //        System.out.println(connection);
    //    } catch (SQLException e) {
    //        throw new RuntimeException(e);
    //    }
    //
    //}

}
