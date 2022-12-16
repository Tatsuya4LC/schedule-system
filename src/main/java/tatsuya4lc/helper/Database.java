package tatsuya4lc.helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import tatsuya4lc.scheduler.Index;
import tatsuya4lc.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Comparator;

/**
 * This is the Database class.
 * This class holds several ObservableList that contains information retrieved from the database.
 * This class also contains methods that interact with the connected database.
 * <p>
 *     RUNTIME ERROR
 *     <br>
 *     init(), getUsersSQL(), getCustomerSQL(), addCustomerSQL(), udpateCustomerSQL(), deleteCustomerSQL(),
 *     getAppointmentSQL(), addAppointmentSQL(), updateAppointmentSQL(), deleteAppointmentSQL(), deleteAssociatedAppntSQL()
 * </p>
 */
public class Database {

    private ObservableList<Users> userList = FXCollections.observableArrayList();
    private ObservableList<Customers> customerList = FXCollections.observableArrayList();
    private ObservableList<Locations> locationList = FXCollections.observableArrayList();
    private ObservableList<String> countryList = FXCollections.observableArrayList();
    private ObservableList<Contacts> contactList = FXCollections.observableArrayList();
    private ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();
    private static final ObservableList<LocalTime> timeSchedule = FXCollections.observableArrayList();
    private static final ObservableList<Months> monthList = FXCollections.observableArrayList();
    private static final ObservableList<String> weekList = FXCollections.observableArrayList();

    /**
     * This method adds the Months in for the ObservableList monthList
     */
    private void setMonthList() {
        monthList.add(new Months(1, "January"));
        monthList.add(new Months(2, "February"));
        monthList.add(new Months(3, "March"));
        monthList.add(new Months(4, "April"));
        monthList.add(new Months(5, "May"));
        monthList.add(new Months(6, "June"));
        monthList.add(new Months(7, "July"));
        monthList.add(new Months(8,"August"));
        monthList.add(new Months(9, "September"));
        monthList.add(new Months(10, "October"));
        monthList.add(new Months(11, "November"));
        monthList.add(new Months(12, "December"));
    }

    /**
     * This method adds value to the ObservableList weekList
     */
    private void setWeekList() {
        weekList.add(0, "Last Week");
        weekList.add(1, "This Week");
        weekList.add(2, "Next Week");
    }

    /**
     *
     * @return monthList
     */
    public ObservableList<Months> getMonthList() {
        return monthList;
    }

    /**
     *
     * @return weekList
     */
    public ObservableList<String> getWeekList() {
        return weekList;
    }

    /**
     * This method adds a timetable set in best 8am-10pm EST then change to local time before
     * added to the ObservableList timeSchedule.
     */
    private void setTimeSchedule() {
        for (int i = 8; i <= 22; i++) {
            timeSchedule.add(LocalDateTime.of(LocalDate.now(), LocalTime.of(i,0))
                    .atZone(ZoneId.of("America/New_York"))
                    .withZoneSameInstant(ZoneOffset.systemDefault())
                    .toLocalTime());

            if (i < 22) {
                timeSchedule.add(LocalDateTime.of(LocalDate.now(), LocalTime.of(i,15))
                        .atZone(ZoneId.of("America/New_York"))
                        .withZoneSameInstant(ZoneOffset.systemDefault())
                        .toLocalTime());
                timeSchedule.add(LocalDateTime.of(LocalDate.now(), LocalTime.of(i,30))
                        .atZone(ZoneId.of("America/New_York"))
                        .withZoneSameInstant(ZoneOffset.systemDefault())
                        .toLocalTime());
                timeSchedule.add(LocalDateTime.of(LocalDate.now(), LocalTime.of(i,45))
                        .atZone(ZoneId.of("America/New_York"))
                        .withZoneSameInstant(ZoneOffset.systemDefault())
                        .toLocalTime());
            }
        }
    }

    /**
     *
     * @return timeSchedule
     */
    public ObservableList<LocalTime> getTimeSchedule() {
        return timeSchedule;
    }

    /**
     *
     * @return userList
     */
    public ObservableList<Users> getUserList() {
        return userList;
    }

    /**
     *
     * @return contactList
     */
    public ObservableList<Contacts> getContactList() {
        return contactList;
    }

    /**
     *
     * @return countryList
     */
    public ObservableList<String> getCountryList() {
        return countryList;
    }

    /**
     * This is the init method.
     * Used to initialize contactList, locationList, and countryList
     * by retrieving data from a database.
     */
    public void init() {
        setWeekList();
        setMonthList();
        setTimeSchedule();
        getAppointmentSQL();
        String sql = "SELECT * FROM CONTACTS";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            String a, b;
            int i;

            while (rs.next()) {
                a = rs.getString("Contact_Name");
                i = rs.getInt("Contact_ID");

                contactList.add(new Users(i, a, null));
            }

            sql = """
                SELECT *
                FROM FIRST_LEVEL_DIVISIONS
                INNER JOIN COUNTRIES
                ON (FIRST_LEVEL_DIVISIONS.COUNTRY_ID = COUNTRIES.COUNTRY_ID)""";

            ps = JDBC.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                a = rs.getString("Division");
                b = rs.getString("Country");
                i = rs.getInt("Division_ID");

                locationList.add(new Locations(a, b, i));
            }

            sql = "SELECT * FROM COUNTRIES";

            ps = JDBC.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                a = rs.getString("Country");

                countryList.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        countryList.sort(Comparator.naturalOrder());
    }

    /**
     * This is the getUserSQL method.
     * This method retrieves user information from the database.
     */
    public void getUsersSQL() {
        String sql = "SELECT * FROM USERS";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int u_ID = rs.getInt("User_ID");
                String uName = rs.getString("User_Name");
                String pass = rs.getString("Password");

                userList.add(new Users(u_ID, uName, pass));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is the authenticateUser method.
     * This method authenticate user by comparing the uName and uPass value
     * to the User information retrieved from the database
     *
     * @param uName variable for Username
     * @param uPass variable for User Password
     * @return boolean
     */
    public boolean authenticateUser(String uName, String uPass) {
        for (Users u : userList) {
            if (u.getName().equals(uName) && u.getPassword().equals(uPass)) {
                init();
                return true;
            } else if (u.getName().equals(uName) && !u.getPassword().equals(uPass)) {
                // Alert in case of password does not match
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(Index.rb.getString("AIE"));
                alert.setHeaderText(null);
                alert.setContentText(Index.rb.getString("wrongPass"));
                alert.showAndWait();
                return false;
            }
        }

        // Alert in case of username not found
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(Index.rb.getString("AIE"));
        alert.setHeaderText(null);
        alert.setContentText(Index.rb.getString("notFound"));
        alert.showAndWait();
        return false;
    }

    /**
     * This is the getCustomerSQL method.
     * This method retrieves Customer Information from the database.
     */
    public void getCustomerSQL() {
        customerList.clear();
        String sql = """
                    SELECT *
                    FROM CUSTOMERS
                    INNER JOIN FIRST_LEVEL_DIVISIONS
                    ON (CUSTOMERS.DIVISION_ID = FIRST_LEVEL_DIVISIONS.DIVISION_ID)
                    INNER JOIN COUNTRIES
                    ON (FIRST_LEVEL_DIVISIONS.COUNTRY_ID = COUNTRIES.COUNTRY_ID)""";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int cus_ID = rs.getInt("Customer_ID");
                int l_ID = rs.getInt("Division_ID");
                String cus_Name = rs.getString("Customer_Name");
                String cus_Address = rs.getString("Address");
                String cus_Location = rs.getString("Division");
                String cus_Country = rs.getString("Country");
                String cus_PostalCode = rs.getString("Postal_Code");
                String cus_Phone = rs.getString("Phone");

                customerList.add(new Customers(cus_ID, cus_Name, cus_Address, cus_Location, cus_Country, cus_PostalCode, cus_Phone, l_ID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Comparator<Customers> comparator = Comparator.comparing(Customers::getCus_ID);
        customerList.sort(comparator);
    }

    /**
     *
     * @return customerList
     */
    public ObservableList<Customers> getCustomerList() {
        return customerList;
    }

    /**
     * This is the addCustomerSQL method.
     * This method adds new Customer Information to the database.
     *
     * @param c Customers object containing information to add to the database
     */
    public void addCustomerSQL(Customers c) {
        String sql = """
                    INSERT INTO CUSTOMERS
                    (Customer_Name, Address, Postal_Code, Phone, Division_ID)
                    VALUES
                    ( ?, ?, ?, ?, ?)""";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, c.getCus_Name());
            ps.setString(2, c.getCus_Address());
            ps.setString(3, c.getCus_PostalCode());
            ps.setString(4, c.getCus_Phone());
            ps.setInt(5, c.getL_ID());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is the updateCustomerSQL method.
     * This method updates Customer Information in the database.
     *
     * @param c Customers object containing information to add to the database
     */
    public void updateCustomerSQL(Customers c) {
        String sql = """
                    UPDATE CUSTOMERS
                    SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ?
                    WHERE
                    Customer_ID = ?""";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, c.getCus_Name());
            ps.setString(2, c.getCus_Address());
            ps.setString(3, c.getCus_PostalCode());
            ps.setString(4, c.getCus_Phone());
            ps.setInt(5, c.getL_ID());
            ps.setInt(6, c.getCus_ID());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  This is the deleteCustomerSQL method.
     *  This method deletes Customer's Information in the database.
     *
     * @param c Customers object containing information to add to the database
     */
    public void deleteCustomerSQL(Customers c) {
        String sql = """
                DELETE FROM CUSTOMERS
                WHERE
                Customer_ID = ?""";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, c.getCus_ID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getCustomerSQL();
    }

    /**
     *
     * @return locationList
     */
    public ObservableList<Locations> getLocationList() {
        return locationList;
    }

    /**
     * This is the getAppointmentSQL method.
     * This method retrieves Appointment data from the database.
     */
    public void getAppointmentSQL() {
        appointmentList.clear();
        String sql = "SELECT * FROM APPOINTMENTS";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int appnt_ID = rs.getInt("Appointment_ID");
                int cus_ID = rs.getInt("Customer_ID");
                int u_ID = rs.getInt("User_ID");
                int con_ID = rs.getInt("Contact_ID");
                String appnt_Title = rs.getString("Title");
                String appnt_Description = rs.getString("Description");
                String appnt_Location = rs.getString("Location");
                String appnt_Type = rs.getString("Type");
                LocalDateTime appnt_StartDateTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appnt_EndDateTime = rs.getTimestamp("End").toLocalDateTime();
                String appnt_Contact = "";

                for (Contacts c : contactList){
                    if (c.getId() == con_ID) {
                        appnt_Contact = c.getName();
                        break;
                    }
                }


                appointmentList.add(new Appointments(appnt_ID, cus_ID, u_ID, con_ID, appnt_Title, appnt_Description,
                                    appnt_Location, appnt_Type, appnt_Contact, appnt_StartDateTime, appnt_EndDateTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return appointmentList
     */
    public ObservableList<Appointments> getAppointmentList() {
        return appointmentList;
    }

    /**
     * This is the addAppointmentSQl method.
     * This method adds new Appointment to the database.
     *
     * @param a Appointments objects containing information to add to the database
     */
    public void addAppointmentSQL(Appointments a) {
        String sql = """
                    INSERT INTO APPOINTMENTS
                    (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID)
                    VALUES
                    ( ?, ?, ?, ?, ?, ?, ?, ?, ?)""";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, a.getAppnt_Title());
            ps.setString(2, a.getAppnt_Description());
            ps.setString(3, a.getAppnt_Location());
            ps.setString(4, a.getAppnt_Type());
            ps.setTimestamp(5, Timestamp.valueOf(a.getAppnt_StartDateTime()));
            ps.setTimestamp(6, Timestamp.valueOf(a.getAppnt_EndDateTime()));
            ps.setInt(7, a.getCus_ID());
            ps.setInt(8, a.getU_ID());
            ps.setInt(9, a.getCon_ID());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is the updateAppointmentSQl method.
     * This method updates AppoinTment in the database.
     *
     * @param a Appointments objects containing information to add to the database
     */
    public void updateAppointmentSQL(Appointments a) {
        String sql = """
                    UPDATE APPOINTMENTS
                    SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?
                    , Customer_ID = ?, User_ID = ?, Contact_ID = ?
                    WHERE
                    Appointment_ID = ?""";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, a.getAppnt_Title());
            ps.setString(2, a.getAppnt_Description());
            ps.setString(3, a.getAppnt_Location());
            ps.setString(4, a.getAppnt_Type());
            ps.setTimestamp(5, Timestamp.valueOf(a.getAppnt_StartDateTime()));
            ps.setTimestamp(6, Timestamp.valueOf(a.getAppnt_EndDateTime()));
            ps.setInt(7, a.getCus_ID());
            ps.setInt(8, a.getU_ID());
            ps.setInt(9, a.getCon_ID());
            ps.setInt(10, a.getAppnt_ID());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is the deleteAppointmentSQL method.
     * This method deletes Appointment in the database.
     *
     * @param a Appointments objects containing information to add to the database
     */
    public void deleteAppointmentSQL(Appointments a) {
        String sql = """
                    DELETE FROM APPOINTMENTS
                    WHERE
                    Appointment_ID = ?""";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, a.getAppnt_ID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getAppointmentSQL();
    }

    /**
     * This is the deleteAssociatedAppntSQL method.
     * This method deletes associated Appointment when trying to delete Customer's Information
     * that has scheduled appointment.
     *
     * @param i Customer ID that needs to be removed
     */
    public void deleteAssociatedAppntSQL(int i) {
        String sql = """
                    DELETE FROM APPOINTMENTS
                    WHERE
                    Customer_ID = ?""";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, i);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
