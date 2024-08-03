package ca.ucalgary.ensf380;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {

    // Private Data Members Initialization To Hold Values Required To Access The SQL Database
    private static final String URL = "jdbc:mysql://localhost:3306/subway"; // The Location Of The Database
    private static final String USERNAME = "root"; // The Username For Database Access
    private static final String PASSWORD = "Soumik_15/"; // The Password For Database Access

    // Public Getter Method To Initialize And Return A Connection To The SQL Database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // Main Method To Test The Connection To The SQL Database
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            if (connection != null) {
                System.out.println("Connected to the database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method To Fetch Advertisements From The Database
    public List<Advertisement> fetchAdvertisements() throws SQLException {
        List<Advertisement> ads = new ArrayList<>();
        String query = "SELECT * FROM advertisements";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Advertisement ad = new Advertisement();
                ad.setId(resultSet.getInt("id"));
                ad.setAdName(resultSet.getString("ad_name"));
                ad.setAdType(resultSet.getString("ad_type"));
                ad.setAdFile(resultSet.getBytes("ad_file"));
                ads.add(ad);
            }
        } finally {
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        return ads;
    }

    // Method To Disconnect The Database Connection
    public void disconnect(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
