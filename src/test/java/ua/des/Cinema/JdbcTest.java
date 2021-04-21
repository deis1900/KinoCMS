package ua.des.Cinema;

import org.springframework.beans.factory.annotation.Value;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;


public class JdbcTest {

    @Value("${spring.datasource.url}")
    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/kino?allowPublicKeyRetrieval=true&useSSL=false";

    @Value(("${spring.datasource.username}"))
    private static final String username = "root";

    @Value("${spring.datasource.password}")
    private static final String password = "Password123@";

    private Connection connection;
    private Statement statement;

    //    aggregated function
    @Test
    public void getProfit() {
        String query = "SELECT SUM(price) from tickets;";
        int actual = 0;
        try {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                actual = rs.getInt("SUM(price)");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        System.out.println(actual);
        assertEquals(actual, 535);
    }


    //    RIGHT JOIN
    @Test
    public void getOldBooking() {
        int actual = 0;
        try {
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM booking RIGHT JOIN users " +
                            "ON booking.user_id = users.user_id  " +
                            "WHERE DATEDIFF(CURRENT_DATE,`create_date`)>7;"
            );
            while (rs.next()) {
                System.out.println(
                        "" + rs.getRow() + ". " +
                                "id = " + rs.getInt("user_id") +
                                "   || login  = " + rs.getString("login") +
                                "   || booking_create_date  = " + rs.getDate("create_date"));
                actual = rs.getInt("user_id");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        assertEquals(actual, 2);
    }

    //    LEFT JOIN
    @Test
    public void remindAboutPurchasedTickets() {
        int actual = 0;
        try {
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM kino.users " +
                            "INNER JOIN kino.booking " +
                            "ON users.user_id = booking.user_id " +
                            "AND kino.booking.create_date > NOW() " +
                            "AND pay = 0;"
            );
            while (rs.next()) {
                System.out.println(
                        "" + rs.getRow() + ". " +
                                "|| user_id = " + rs.getLong("user_id") +
                                "   || login  = " + rs.getString("login") +
                                "   || create_date  = " + rs.getTimestamp("create_date"));
                actual = rs.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(actual, 4);
    }


    //    LEFT JOIN
    @Test
    public void getAllFreeSeatsForAllCinema() {
        List<Integer> actual = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM kino.cinema AS C " +
                            "LEFT JOIN kino.rooms AS R ON R.cinema_id = C.id " +
                            "LEFT JOIN kino.seats AS S ON R.room_id = S.room_id " +
                            "AND S.free is null;"
            );
            while (rs.next()) {
                System.out.println(
                        "" + rs.getRow() + ". " +
                                "cinema_id = " + rs.getInt("cinema_id") +
                                "   || name  = " + rs.getString("name") +
                                "   || main_photo  = " + rs.getString("main_photo") +
                                "   || room_name = " + rs.getString("name") +
                                "   || place  = " + rs.getString("place"));
                actual.add(rs.getInt("cinema_id"));
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        List<Integer> expected = Arrays.asList(1, 1, 2, 2, 3, 3, 0);
        assertEquals(actual, expected);
    }

    //    SUB QUERY
    @Test
    public void getTopFilms() {
        List<Integer> actual = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery("" +
                    "SELECT * FROM kino.sessions AS s " +
                    "RIGHT JOIN kino.tickets AS t " +
                    "ON (SELECT COUNT(session_id) FROM kino.tickets) " +
                    "ORDER BY s.film_id;");
            while (rs.next()) {
                System.out.println(
                        "" + rs.getRow() + ". " +
                                "|| ticket_id = " + rs.getInt("id") +
                                "   || film_id  = " + rs.getString("film_film_id") +
                                "   || show_time  = " + rs.getDate("show_time"));
                actual.add(rs.getInt("film_film_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(actual.size(), 48);
    }

    //    Correlated Sub Query
    @Test
    public void getRentMovie() {
        List<Integer> actual = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery(
                    "SELECT id, show_time, seat_id,\n" +
                            "(SELECT name FROM kino.films WHERE id = film_id) AS films,\n" +
                            "(SELECT series FROM kino.seats AS s WHERE seat_id = s.id) AS series,\n" +
                            "(SELECT place FROM kino.seats AS s WHERE seat_id = s.id) AS place\n" +
                            "FROM kino.sessions \n" +
                            "WHERE (SELECT free FROM kino.seats AS s WHERE seat_id = s.id) > 0;"
            );
            while (rs.next()) {
                System.out.println(
                        "" + rs.getRow() + ". " +
                                "id = " + rs.getInt("id") +
                                "   || show_time: " + rs.getDate("show_time") +
                                "   || series: " + rs.getInt("series") +
                                "   || place: " + rs.getInt("place") +
                                "   || films: " + rs.getString("films"));
                actual.add(rs.getInt("id"));
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        expected = Arrays.asList(1, 2, 4, 5, 6, 8);
        assertEquals(actual, expected);
    }

    @BeforeClass
    public void openConnection() {
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println("Can't get connection to :" + jdbcUrl);
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
