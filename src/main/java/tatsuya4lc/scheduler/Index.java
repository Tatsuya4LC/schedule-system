package tatsuya4lc.scheduler;

import tatsuya4lc.controller.CustomerController;
import tatsuya4lc.controller.NewAppointmentController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tatsuya4lc.helper.*;
import tatsuya4lc.model.Appointments;
import tatsuya4lc.model.Customers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This is the Index class that creates the schedule management application.
 *
 */
public class Index extends Application {
    /**
     * ZoneId variable to hold system time zone
     */
    public final static ZoneId zID = ZoneId.systemDefault();
    /**
     * DateTimeFormatter variable to hold format pattern for LocalTime objects
     */
    public  final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
    /**
     * WeekFields variable to hold local week field
     */
    public final static WeekFields weekFields = WeekFields.of(Locale.getDefault());
    /**
     * Integer variable to hold local number of week
     */
    public final static int weekNumNow = LocalDateTime.now().get(weekFields.weekOfWeekBasedYear());
    /**
     * Integer variable to hold local month value
     */
    public final static int monthNumNow = LocalDateTime.now().getMonthValue();
    /**
     * Locale variable to hold system's locale for translation
     */
    public final static Locale l = Locale.getDefault();
    /**
     * ResourceBundle variable for translating to English or French
     */
    public final static ResourceBundle rb = ResourceBundle.getBundle("tatsuya4lc/lang", l);
    /**
     * final static Database object d instantiated to access method in Database class
     */
    public final static Database d = new Database();

    /**
     * This is an override method.
     * This override method prepares the first screen.
     *
     * @param stage Stage parameter used to set the stage
     * @throws IOException Exceptions that's needed to be thrown
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Index.class.getResource("/tatsuya4lc/view/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle(rb.getString("appTitle"));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is the switchWindow method.
     * This method allows for switching scenes when called.
     *
     * @param event ActionEvent parameter used to get the window for the stage
     * @param i leveraged to pick a fxml file for FXMLLoader to set the scene
     */
    public static void switchWindow(ActionEvent event, int i) {
        URL url = null;

        // switch-case for choosing fxml file
        switch(i) {
            case 1 -> url = Index.class.getResource("/tatsuya4lc/view/MainView.fxml");
            case 2 -> url = Index.class.getResource("/tatsuya4lc/view/CustomerView.fxml");
            case 3 -> url = Index.class.getResource("/tatsuya4lc/view/AppointmentView.fxml");
            case 4 -> url = Index.class.getResource("/tatsuya4lc/view/NewAppointmentView.fxml");
        }

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader mainLoader = new FXMLLoader(url);
        Scene scene;

        try {
            scene = new Scene(mainLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage.setTitle("Application");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is the switchWindow method.
     * This is a method overload that allows to create controller Objects to called public method from the class.
     * It allows modification to CustomerController and NewAppointmentController scene
     *
     * @param event ActionEvent parameter used to get the window for the stage
     * @param i leveraged to pick a fxml file for FXMLLoader to set the scene
     * @param c Customers variable to pass through when updating Customer Information
     * @param a Appointments variable to pass through when updating Appointments
     */
    public static void switchWindow(ActionEvent event, int i, Customers c, Appointments a) {
        URL url = null;

        // switch-case duplicate to allow full update or time only update for appointments
        switch(i) {
            case 2 -> url = tatsuya4lc.scheduler.Index.class.getResource("/tatsuya4lc/view/CustomerView.fxml");
            case 4 -> url = tatsuya4lc.scheduler.Index.class.getResource("/tatsuya4lc/view/NewAppointmentView.fxml");
            case 5 -> url = tatsuya4lc.scheduler.Index.class.getResource("/tatsuya4lc/view/NewAppointmentView.fxml");
        }

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader mainLoader = new FXMLLoader(url);
        Scene scene;

        try {
            scene = new Scene(mainLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage.setTitle("Application");
        stage.setScene(scene);
        stage.show();

        // switch-case for optional behaviour when changing scene
        switch (i) {
            // allows scene modification when updating customer's information
            case 2 -> {
                CustomerController customerController = mainLoader.getController();
                customerController.isModifyingCustomer(c);
            }
            // allows scene modification when updating appointment
            case 4 -> {
                NewAppointmentController newAppointmentController = mainLoader.getController();
                newAppointmentController.isModifyingAppointment(a, 0);
            }
            // allows scene modification when updating only the time aspect of the appointment
            case 5 -> {
                NewAppointmentController newAppointmentController = mainLoader.getController();
                newAppointmentController.isModifyingAppointment(a, 1);
            }
        }
    }

    /**
     * This is the main method.
     * The first method that gets called when the application runs
     *
     * @param args default argument
     */
    public static void main(String[] args) {
        // opens the connection with the database
        JDBC.openConnection();
        // launches the scene
        launch();
    }
}
