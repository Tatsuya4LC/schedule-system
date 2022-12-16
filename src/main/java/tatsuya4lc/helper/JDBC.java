package tatsuya4lc.helper;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This class is an Abstract class.
 * This class enables connection with SQL database.
 * <p>
 *     RUNTIME ERROR
 *     <br>
 *     openConnection(), closeConnection()
 * </p>
 */
public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static final String password = "Passw0rd!"; // Password
    private static Connection connection;  // Connection Interface

    /**
     * This is the openConnection method.
     * It opens connection with the database.
     */
    public static void openConnection() {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e) {
            //System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     *
     * @return connection
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * This is the closeConnection method.
     * It closes the connection with the database.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e) {
            //System.out.println("Error:" + e.getMessage());
            //Do nothing
        }
    }
}
