package ua.des.Cinema;

import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.*;

import static org.testng.Assert.assertEquals;

public class PostgresJdbctest {
    @Value("${postgres.datasource.url}")
    private static String jdbcUrl="jdbc:postgresql://localhost:5432/audience";

    @Value(("${postgres.datasource.username}"))
    private static String username="root";

    @Value("${postgres.datasource.password}")
    private static String password="secret123";

    private Connection connection;
    private Statement statement;

    @Test
    public void createTable() {
        String query = "CREATE TABLE accous (" +
                " user_id serial PRIMARY KEY, " +
                " username VARCHAR ( 50 ) UNIQUE NOT NULL, " +
                " password VARCHAR ( 50 ) NOT NULL, " +
                " email VARCHAR ( 255 ) UNIQUE NOT NULL, " +
                " created_on TIMESTAMP NOT NULL, " +
                " last_login TIMESTAMP  " +
                ");";
        int actual = 0;
        try {
            ResultSet rs = statement.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        System.out.println(actual);
        assertEquals(actual, 535);


    }@Test
    public void dropTable() {
        String query = "DROP TABLE accounts;";
        int actual = 0;
        try {
            ResultSet rs = statement.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        System.out.println(actual);
        assertEquals(actual, 535);
    }

    @BeforeClass
    public void openConnection() {
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (Exception e) {
            System.out.println("Can't get connection to :" + jdbcUrl);
            e.printStackTrace();
        }
        try{
            statement = connection.createStatement();
        } catch (Exception e){
            System.out.println("Can't get statement to :" + jdbcUrl);
            e.printStackTrace();
        }
    }

    @AfterClass
    public void closeConnection() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("While close statement " + e.getMessage());
            e.printStackTrace();
        }
    }
}
