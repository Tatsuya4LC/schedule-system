package tatsuya4lc.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tatsuya4lc.model.Appointments;
import tatsuya4lc.scheduler.Index;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * This is a class Controller for the Login window.
 * This class provides logic for the LoginView.fxml
 * <p>
 *     RUNTIME ERROR
 *     <br>
 *     onLogin()
 * </p>
 */
public class LoginController implements Initializable {

    @FXML
    private Label appTitle;

    @FXML
    private Label uID;

    @FXML
    private Label uPass;

    @FXML
    private Label zoneID;

    @FXML
    private TextField textUserID;

    @FXML
    private PasswordField textUserPassword;

    @FXML
    private Button login;

    /**
     * This is the onLogin method.
     * This method write to a file called login_activity.txt.
     * Calls a method in the Database class to authenticate user
     *
     * @param event the event that the button was pressed
     * @throws IOException Runtime Error
     */
    @FXML
    private void onLogin(ActionEvent event) throws IOException {
        FileWriter writeFile = new FileWriter("login_activity.txt", true);
        PrintWriter outFile = new PrintWriter(writeFile);
        String u_ID = textUserID.getText();
        String uPass = textUserPassword.getText();
        Index.d.getUsersSQL();

        if (Index.d.authenticateUser(u_ID, uPass)) {
            appntAlert();
            Index.switchWindow(event, 1);
            outFile.println("Login Attempt: "
                    + LocalDateTime.now() + " "
                    + u_ID + " "
                    + uPass + " "
                    + "Success");
        } else {
            outFile.println("Login Attempt: "
                    + LocalDateTime.now() + " "
                    + u_ID + " "
                    + uPass + " "
                    + "Failed");
        }

        outFile.close();
    }

    /**
     * This is the appntAlert method.
     * This method creates an Alert window
     * that alerts user if they have an appointment in 15 minutes or not
     */
    private void appntAlert() {
        boolean notFound = true;
        Appointments appointment = null;

        for (Appointments a : Index.d.getAppointmentList()) {
            if (LocalDateTime.now().plusMinutes(15).isAfter(a.getAppnt_StartDateTime())
                    && LocalDateTime.now().isBefore(a.getAppnt_StartDateTime())) {
                appointment = a;
                notFound = false;
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Index.rb.getString("IA"));

        if (notFound) {
            alert.setHeaderText(null);
            alert.setContentText(Index.rb.getString("noAppntAlert"));

        } else {
            alert.setHeaderText(Index.rb.getString("appntID") + ": " + appointment.getAppnt_ID()
                    + "\n" + Index.rb.getString("date") + " " + appointment.getAppnt_StartDateTime().toLocalDate()
                    + "\n" + Index.rb.getString("time") + " " + appointment.getAppnt_StartDateTime().toLocalTime().format(Index.formatter));
            alert.setContentText(Index.rb.getString("appntAlert"));

        }
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appTitle.setText(Index.rb.getString("loginTitle"));
        uID.setText(Index.rb.getString("loginUID"));
        uPass.setText(Index.rb.getString("loginPass"));
        zoneID.setText(Index.zID.toString());
        login.setText(Index.rb.getString("loginButton"));
    }
}
