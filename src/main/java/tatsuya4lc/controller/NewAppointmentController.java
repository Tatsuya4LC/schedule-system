package tatsuya4lc.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tatsuya4lc.model.*;
import tatsuya4lc.scheduler.Index;

import java.net.URL;
import java.time.*;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * This is a class Controller for the New/Update Appointment window.
 * This class provides logic for the NewAppointmentView.fxml
 * <p>
 *     LOGICAL ERROR
 *     <br>
 *     onAppntSave()
 * </p>
 */
public class NewAppointmentController implements Initializable {

    boolean updateAppnt = false, changeTime = false;

    int appnt_ID;
    @FXML
    private Label newAppntType;

    @FXML
    private DatePicker appntEndDate;

    @FXML
    private DatePicker appntStartDate;

    @FXML
    private Button newAppntCancel;

    @FXML
    private Label newAppntContact;

    @FXML
    private Label newAppntCustomerID;

    @FXML
    private Label newAppntDescription;

    @FXML
    private Label newAppntEnd;

    @FXML
    private Label newAppntID;

    @FXML
    private Label newAppntLocation;

    @FXML
    private Label newAppntMainTitle;

    @FXML
    private Button newAppntSave;

    @FXML
    private Label newAppntStart;

    @FXML
    private Label newAppntTitle;

    @FXML
    private Label newAppntUserID;

    @FXML
    private TextField textAppntDescription;

    @FXML
    private TextField textAppntID;

    @FXML
    private TextField textAppntTitle;

    @FXML
    private TextField textAppntType;

    @FXML
    private ComboBox<String> textLocation;

    @FXML
    private ComboBox<String> textContact;

    @FXML
    private ComboBox<String> textCusID;

    @FXML
    private ComboBox<String> textUserID;

    @FXML
    private ComboBox<String> appntStartTime;

    @FXML
    private ComboBox<String> appntEndTime;

    @FXML
    private Label zoneID;

    /**
     * This is the onAppntCancel method.
     * This method changes the scene to the previous scene.
     *
     * @param event the event that the button was pressed
     */
    @FXML
    void onAppntCancel(ActionEvent event) {
        Index.switchWindow(event, 3);
    }

    /**
     * This is the onAppntSave method.
     * This method retrieves data from the TextFields, DatePicker, ComboBox to add/update Appointments.
     * Checks for null values
     * <p>
     *     lambda is used to create Predicates that simplifies condition for if statements
     *     that checks for any logical errors
     * </p>
     * @param event
     */
    @FXML
    void onAppntSave(ActionEvent event) {
        boolean logicError = false, empty = false;
        int con_ID = 0, cus_ID = 0, u_ID = 0;
        LocalDate startDate = null, endDate= null;
        LocalTime startTime= null, endTime = null;
        String appnt_Title = null, appnt_Description = null, appnt_Location = null, appnt_Type = null;

        try {
            appnt_Title = textAppntTitle.getText();
            appnt_Description = textAppntDescription.getText();
            appnt_Location = textLocation.getValue();
            appnt_Type = textAppntType.getText();
            String appnt_Contact = textContact.getValue();
            startDate = appntStartDate.getValue();
            startTime = LocalTime.parse(appntStartTime.getValue(), Index.formatter);
            endDate = appntEndDate.getValue();
            endTime = LocalTime.parse(appntEndTime.getValue(), Index.formatter);
            cus_ID = Integer.parseInt(textCusID.getValue());
            u_ID = Integer.parseInt(textUserID.getValue());

            for (Contacts c : Index.d.getContactList()) {
                if (c.getName().equals(appnt_Contact)) {
                    con_ID = c.getId();
                    break;
                }
            }
        } catch (Exception e) {
            logicError = true;
            empty = true;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(Index.rb.getString("newAppntBlank"));
            alert.setHeaderText(Index.rb.getString("newAppntBlankInfo"));
            alert.setContentText(null);

            alert.showAndWait();
        }


        if (!logicError) {
            LocalDate finalStartDate = startDate;
            LocalDate finalEndDate = endDate;
            LocalTime finalStartTime = startTime;
            LocalTime finalEndTime = endTime;
            int finalCon_ID = con_ID;
            int i = Index.d.getTimeSchedule().size() - 1;
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime)
                    .atZone(Index.zID)
                    .withZoneSameInstant(ZoneId.of("America/New_York"))
                    .toLocalDateTime();
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime)
                    .atZone(Index.zID)
                    .withZoneSameInstant(ZoneId.of("America/New_York"))
                    .toLocalDateTime();
            LocalDateTime scheduleStartTime = LocalDateTime.of(LocalDate.now(), Index.d.getTimeSchedule().get(0))
                    .atZone(Index.zID)
                    .withZoneSameInstant(ZoneId.of("America/New_York"))
                    .toLocalDateTime();
            LocalDateTime scheduleEndTime = LocalDateTime.of(LocalDate.now(), Index.d.getTimeSchedule().get(i))
                    .atZone(Index.zID)
                    .withZoneSameInstant(ZoneId.of("America/New_York"))
                    .toLocalDateTime();
            Duration duration = Duration.between(startDateTime, endDateTime);

            // Predicate that checks for appointment conflict with scheduled appointments
            Predicate<Appointments> dateTimePredicate = (appointments)
                    -> ((LocalDateTime.of(finalStartDate, finalStartTime).plusMinutes(1).isAfter(appointments.getAppnt_StartDateTime())
                    && LocalDateTime.of(finalEndDate, finalEndTime).minusMinutes(1).isBefore(appointments.getAppnt_EndDateTime()))
                    || (appointments.getAppnt_StartDateTime().isAfter(LocalDateTime.of(finalStartDate, finalStartTime)))
                    && (appointments.getAppnt_StartDateTime().isBefore(LocalDateTime.of(finalEndDate, finalEndTime)))
                    || (appointments.getAppnt_EndDateTime().isAfter(LocalDateTime.of(finalStartDate, finalStartTime)))
                    && appointments.getAppnt_EndDateTime().isBefore(LocalDateTime.of(finalEndDate, finalEndTime)))
                    && appointments.getCon_ID() == finalCon_ID;
            // Predicate that checks if end time is within business hours
            Predicate<LocalDateTime> endTimePredicate = (localDateTime)
                    -> localDateTime.plusMinutes(14).isBefore(scheduleEndTime);
            // Predicate that checks if start time is within business hours
            Predicate<LocalDateTime> startTimePredicate = (localDateTime)
                    -> localDateTime.minusMinutes(14).isAfter(scheduleStartTime);

            if ((duration.isNegative()
                    || duration.isZero()
                    || !endTimePredicate.test(startDateTime)
                    || !startTimePredicate.test(endDateTime))
                    && !changeTime && !updateAppnt) {
                logicError = true;
            } else if ((updateAppnt || changeTime) && (duration.isNegative() || duration.isZero())) {
                logicError = true;
            }

            for (Appointments a : Index.d.getAppointmentList()) {
                if (dateTimePredicate.test(a) && !changeTime && !updateAppnt) {
                    logicError = true;
                }
            }
        }

        if (logicError && !empty) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(Index.rb.getString("newAppntConflict"));
            alert.setHeaderText(Index.rb.getString("newAppntConflictInfo"));
            alert.setContentText(null);

            alert.showAndWait();
        } else if (!empty){
            if (!updateAppnt) {
                Index.d.addAppointmentSQL(new Appointments(0, cus_ID, u_ID, con_ID, appnt_Title,
                        appnt_Description, appnt_Location, appnt_Type, null,
                        LocalDateTime.of(startDate, startTime), LocalDateTime.of(endDate, endTime)));
            } else {
                Index.d.updateAppointmentSQL(new Appointments(appnt_ID, cus_ID, u_ID, con_ID, appnt_Title,
                        appnt_Description, appnt_Location, appnt_Type, null,
                        LocalDateTime.of(startDate, startTime), LocalDateTime.of(endDate, endTime)));
            }

            Index.switchWindow(event, 3);
        }
    }

    /**
     * This is the isModifyingAppointment method.
     * This method is called when updating Appointment.
     *
     * @param a Appointments object to extract information from
     * @param i to indicate that only Appointment time is modifiable
     */
    public void isModifyingAppointment(Appointments a, int i) {
        updateAppnt = true;
        appnt_ID = a.getAppnt_ID();
        newAppntMainTitle.setText(Index.rb.getString("mainAppntButton"));
        newAppntSave.setText(Index.rb.getString("customerUpdateButton"));
        textAppntID.setText(String.valueOf(appnt_ID));
        textAppntTitle.setText(a.getAppnt_Title());
        textAppntDescription.setText(a.getAppnt_Description());
        textLocation.getSelectionModel().select(a.getAppnt_Location());
        textContact.getSelectionModel().select(a.getAppnt_Contact());
        textAppntType.setText(a.getAppnt_Type());
        appntStartDate.setValue(a.getAppnt_StartDateTime().toLocalDate());
        appntStartTime.getSelectionModel().select(a.getAppnt_StartDateTime().format(Index.formatter));
        appntEndDate.setValue(a.getAppnt_EndDateTime().toLocalDate());
        appntEndTime.getSelectionModel().select(a.getAppnt_EndDateTime().format(Index.formatter));
        textCusID.getSelectionModel().select(String.valueOf(a.getCus_ID()));
        textUserID.getSelectionModel().select(String.valueOf(a.getU_ID()));

        if (i == 1) {
            changeTime = true;
            textAppntTitle.setDisable(true);
            textAppntDescription.setDisable(true);
            textLocation.setDisable(true);
            textContact.setDisable(true);
            textAppntType.setDisable(true);
            appntStartDate.setDisable(true);
            appntEndDate.setDisable(true);
            textCusID.setDisable(true);
            textUserID.setDisable(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textAppntID.setText("Auto-generated");
        zoneID.setText(Index.zID.toString());
        newAppntMainTitle.setText(Index.rb.getString("newAppntMainTitle"));
        newAppntID.setText(Index.rb.getString("appntID") + " :");
        newAppntTitle.setText(Index.rb.getString("appntTitle") + " :");
        newAppntDescription.setText(Index.rb.getString("appntDescription") + " :");
        newAppntLocation.setText(Index.rb.getString("appntLocation") + " :");
        newAppntContact.setText(Index.rb.getString("appntContact") + " :");
        newAppntType.setText(Index.rb.getString("appntType") + " :");
        newAppntStart.setText(Index.rb.getString("appntStart") + " :");
        newAppntEnd.setText(Index.rb.getString("appntEnd") + " :");
        newAppntCustomerID.setText(Index.rb.getString("appntCustomerID") + " :");
        newAppntUserID.setText(Index.rb.getString("appntUserID") + " :");
        newAppntSave.setText(Index.rb.getString("customerAddButton"));
        newAppntCancel.setText(Index.rb.getString("customerCancelButton"));


        ObservableList<String> timeList = FXCollections.observableArrayList();
        for (LocalTime l : Index.d.getTimeSchedule()) {
            timeList.add(l.format(Index.formatter));
        }
        appntStartTime.setItems(timeList);
        appntEndTime.setItems(timeList);

        ObservableList<String> conList = FXCollections.observableArrayList();
        for (Contacts c : Index.d.getContactList()) {
            conList.add(c.getName());
        }

        conList.sort(Comparator.naturalOrder());
        textContact.setItems(conList);

        ObservableList<String> cusList = FXCollections.observableArrayList();
        for (Customers c : Index.d.getCustomerList()) {
            cusList.add(String.valueOf(c.getCus_ID()));
        }

        textCusID.setItems(cusList);

        ObservableList<String> uList = FXCollections.observableArrayList();
        for (Users u : Index.d.getUserList()) {
            uList.add(String.valueOf(u.getId()));
        }

        textUserID.setItems(uList);

        ObservableList<String> list = FXCollections.observableArrayList();
        for (Locations l : Index.d.getLocationList()) {
            list.add(l.getLocationName());
        }
        Comparator<String> comparator = Comparator.naturalOrder();
        list.sort(comparator);

        textLocation.setItems(list);
    }
}
