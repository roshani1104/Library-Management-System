package com.library.util;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        try (InputStream is = DbUtil.class.getClassLoader()
                                          .getResourceAsStream("config.properties")) {

            if (is == null) {
                throw new RuntimeException("config.properties not found in src/");
            }

            Properties props = new Properties();
            props.load(is);

            URL      = props.getProperty("db.url");
            USER     = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {
            throw new RuntimeException("Failed to load DB config: " + e.getMessage(), e);
        }
    }

    // Prevent object creation
    private DbUtil() {}

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get DB connection", e);
        }
    }
}