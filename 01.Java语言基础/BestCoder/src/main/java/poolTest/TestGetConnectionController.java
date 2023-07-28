package poolTest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;

@RestController
public class TestGetConnectionController {

    @Autowired
    ConnectionHivePool connectionHivePoolConfig;

    @GetMapping("/test/connection")
    public void test(){
        Connection connection;

        try {
            connection = connectionHivePoolConfig.dataSource.getConnection();
            System.out.println(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
