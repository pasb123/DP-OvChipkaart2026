package nl.hu.dp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    //connection link for assignment
    public static Connection connection;

    // connection method
    private static Connection getConnection() {
        String linkJB = "jdbc:postgresql://localhost:5432/ovchip";
        String username = "postgres";
        String password = "sand66";

        try {
            connection = DriverManager.getConnection(linkJB, username, password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return connection;
    };
    //close the connection
    public static void closeConnection() throws SQLException {
        if(connection != null){connection.close();
        }


    }
    //test the connection
    private static void testConnection() throws SQLException {
        try {
            ResultSet resultSet = getConnection().createStatement().executeQuery("SELECT * FROM reiziger");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("reiziger_id") + " "
                        + resultSet.getString("voorletters") + " "
                        + resultSet.getString("achternaam") + " "
                        + resultSet.getString("geboortedatum"));

            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        closeConnection();
    }
    public static void main(String[] args) throws SQLException {
        testConnection();
    }
}