package com.emailalert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCConnectionDao {
    private static final Logger logger = LoggerFactory.getLogger(JDBCConnectionDao.class);

    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://email-alert.cw4m6cjc0hsw.us-east-1.rds.amazonaws.com:3306/Email_Alert?useUnicode=true&characterEncoding=UTF-8&user=admin&password=Intent.BI.20";
            conn = DriverManager.getConnection(connectionUrl);
        } catch (Exception ex) {
            logger.error("Failure connecting to database: " + ex);
        }
        return conn;
    }

    public void close(ResultSet resultSet, Statement statement, Connection connect) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
            logger.error("Exception in closing resources: ", e);
        }
    }

}
