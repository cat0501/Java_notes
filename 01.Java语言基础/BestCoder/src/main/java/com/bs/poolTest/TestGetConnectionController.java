package com.bs.poolTest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;

@RestController
public class TestGetConnectionController {

    @Autowired
    ConnectionHivePool connectionHivePool;

    @GetMapping("/test/connection")
    public void test(){
        Connection connection;

        try {
            connection = connectionHivePool.dataSource.getConnection();
            System.out.println(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
