package tatsuya4lc.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tatsuya4lc.model.Appointments;
import tatsuya4lc.model.Contacts;
import tatsuya4lc.model.Months;
import tatsuya4lc.scheduler.Index;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/**
 * This is a class Controller for the Appointment window.
 * This class provides logic for the AppointmentView.fxml.
 */
public class AppointmentController implements Initializable {

    @FXML
    private Label appntTitle;

    @FXML
    private Tab appntWeekly;

    @FXML
    private Tab appntMonthly;

    @FXML
    private Button addButtonWeekly;

    @FXML
    private Button addButtonMonthly;

    @FXML
    private Button modifyButtonWeekly;

    @FXML
    private Button modifyButtonMonthly;

    @FXML
    private Button deleteButtonWeekly;

    @FXML
    private Button deleteButtonMonthly;

    @FXML
    private Label totalAppnt;

    @FXML
    private TextField totalNum;

    @FXML
    private TableView<Appointments> appntTableWeekly;

    @FXML
    private TableView<Appointments> appntTableMonthly;

    @FXML
    private TableColumn<Appointments, Integer> colAppntIDWeekly;
    @FXML
    private TableColumn<Appointments, Integer> colAppntIDMonthly;

    @FXML
    private TableColumn<Appointments, String> colAppntTitleWeekly;

    @FXML
    private TableColumn<Appointments, String> colAppntTitleMonthly;

    @FXML
    private TableColumn<Appointments, String> colAppntDescriptionWeekly;

    @FXML
    private TableColumn<Appointments, String> colAppntDescriptionMonthly;

    @FXML
    private TableColumn<Appointments, String> colAppntLocationWeekly;

    @FXML
    private TableColumn<Appointments, String> colAppntLocationMonthly;

    @FXML
    private TableColumn<Appointments, String> colAppntContactWeekly;

    @FXML
    private TableColumn<Appointments, String> colAppntContactMonthly;

    @FXML
    private TableColumn<Appointments, String> colAppntTypeWeekly;

    @FXML
    private TableColumn<Appointments, String> colAppntTypeMonthly;

    @FXML
    private TableColumn<Appointments, Timestamp> colAppntStartWeekly;

    @FXML
    private TableColumn<Appointments, Timestamp> colAppntStartMonthly;

    @FXML
    private TableColumn<Appointments, Timestamp> colAppntEndWeekly;

    @FXML
    private TableColumn<Appointments, Timestamp> colAppntEndMonthly;

    @FXML
    private TableColumn<Appointments, Integer> colCustomerIDWeekly;

    @FXML
    private TableColumn<Appointments, Integer> colCustomerIDMonthly;

    @FXML
    private TableColumn<Appointments, Integer> colUserIDWeekly;

    @FXML
    private TableColumn<Appointments, Integer> colUserIDMonthly;

    @FXML
    private Button changeAppntTimeWeekly;

    @FXML
    private Button changeAppntTimeMonthly;

    @FXML
    private Button goBackButtonWeekly;

    @FXML
    private Button goBackButtonMonthly;

    @FXML
    private ComboBox<String> textType;

    @FXML
    private ComboBox<String> monthFilter;

    @FXML
    private ComboBox<String> weekFilter;
    @FXML
    private ComboBox<String> filterContacts;

    /**
     * This is the onAddAppnt method.
     * This method opens the NewAppointmentView scene
     * allowing user to add new Appointment
     * @param event the event that the button was pressed
     */
    @FXML
    void onAddAppnt(ActionEvent event) {
        Index.switchWindow(event, 4);
    }

    /**
     * This is the onModifyAppnt method.
     * This method is for the modify button.
     * checks if an item is selected to prevent runtime error for both Weekly and Monthly tab
     * then calls for a method from Index to change scene
     * and opens the menu for modifying an Appointment
     *
     * @param event the event that the button was pressed
     */
    @FXML
    void onModifyAppnt(ActionEvent event) {
        if (!appntTableWeekly.getSelectionModel().isEmpty()) {
            Appointments a = appntTableWeekly.getSelectionModel().getSelectedItem();
            Index.switchWindow(event, 4, null, a);
            appntTableWeekly.getSelectionModel().clearSelection();
        } else if (!appntTableMonthly.getSelectionModel().isEmpty()) {
            Appointments a = appntTableMonthly.getSelectionModel().getSelectedItem();
            Index.switchWindow(event, 4, null, a);
            appntTableMonthly.getSelectionModel().clearSelection();
        }
    }

    /**
     * This is the onDeleteAppnt method.
     * This method deletes an Appointment by sending the selected
     * appointment to a method in the Database class
     *
     * Includes an Alert confirmation to before proceeding to delete
     */
    @FXML
    void onDeleteAppnt() {
        Appointments a = null;
        boolean delete = true;

        if (!appntTableWeekly.getSelectionModel().isEmpty()) {
            a = appntTableWeekly.getSelectionModel().getSelectedItem();
        } else if (!appntTableMonthly.getSelectionModel().isEmpty()) {
            a = appntTableMonthly.getSelectionModel().getSelectedItem();
        } else {
            delete = false;
        }

        if (delete) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(Index.rb.getString("appntCancellation"));
            alert.setHeaderText(Index.rb.getString("appntID") + ": " + a.getAppnt_ID() + "\n"
                    + Index.rb.getString("appntType") +": " + a.getAppnt_Type());
            alert.setContentText(Index.rb.getString("appntConfirm"));

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Index.d.deleteAppointmentSQL(a);
            }
        }

        appntTableWeekly.getSelectionModel().clearSelection();
        appntTableMonthly.getSelectionModel().clearSelection();
        modifyButtonWeekly.setDisable(true);
        deleteButtonWeekly.setDisable(true);
        modifyButtonMonthly.setDisable(true);
        deleteButtonMonthly.setDisable(true);
    }

    /**
     * This is the onChangeAppntTime method.
     * This method allows the user to change only the appointment times.
     *
     * @param event the event that the button was pressed
     */
    @FXML
    void onChangeAppntTime(ActionEvent event) {
        if (!appntTableWeekly.getSelectionModel().isEmpty()) {
            Appointments a = appntTableWeekly.getSelectionModel().getSelectedItem();
            Index.switchWindow(event, 5, null, a);
            appntTableWeekly.getSelectionModel().clearSelection();
        } else if (!appntTableMonthly.getSelectionModel().isEmpty()) {
            Appointments a = appntTableMonthly.getSelectionModel().getSelectedItem();
            Index.switchWindow(event, 5, null, a);
            appntTableMonthly.getSelectionModel().clearSelection();
        }
    }

    /**
     * This is the onGoBackAppnt method.
     * This method allows the user to go back to the Customer Information scene.
     *
     * @param event the event that the button was pressed
     */
    @FXML
    void onGoBackAppnt(ActionEvent event) {
        Index.switchWindow(event, 1);
    }

    /**
     * This is the onSelection method.
     * This method set the Modify, Cancel and Change Appointment Time button to be enabled
     * when an item is selected
     */
    @FXML
    void onSelection() {
        if (!appntTableWeekly.getSelectionModel().isEmpty()) {
            modifyButtonWeekly.setDisable(false);
            deleteButtonWeekly.setDisable(false);
            changeAppntTimeWeekly.setDisable(false);
        }

        if (!appntTableMonthly.getSelectionModel().isEmpty()) {
            modifyButtonMonthly.setDisable(false);
            deleteButtonMonthly.setDisable(false);
            changeAppntTimeMonthly.setDisable(false);
        }
    }

    /**
     * This is the mouseClickDeselect method.
     * This method set certain button to be disabled
     * when an item is deselected
     */
    @FXML
    void mouseClickDeselect() {
        appntTableWeekly.getSelectionModel().clearSelection();
        appntTableMonthly.getSelectionModel().clearSelection();
        modifyButtonWeekly.setDisable(true);
        modifyButtonMonthly.setDisable(true);
        deleteButtonWeekly.setDisable(true);
        deleteButtonMonthly.setDisable(true);
        changeAppntTimeWeekly.setDisable(true);
        changeAppntTimeMonthly.setDisable(true);
    }

    /**
     * This is the onTabChangeWeek method.
     * This method set certain button to be disabled
     * when changing tab
     */
    @FXML
    void onTabChangeWeek() {
        if (!appntTableWeekly.getSelectionModel().isEmpty()) {
            appntTableWeekly.getSelectionModel().clearSelection();
            modifyButtonWeekly.setDisable(true);
            deleteButtonWeekly.setDisable(true);
            changeAppntTimeWeekly.setDisable(true);
        }
    }

    /**
     * This is the onTabChangeMonth method.
     * This method set certain button to be disabled
     * when changing tab
     */
    @FXML
    void onTabChangeMonth() {
        if (!appntTableMonthly.getSelectionModel().isEmpty()) {
            appntTableMonthly.getSelectionModel().clearSelection();
            modifyButtonMonthly.setDisable(true);
            deleteButtonMonthly.setDisable(true);
            changeAppntTimeMonthly.setDisable(true);
        }
    }

    /**
     * This is the Override initialize method.
     * This method runs when scene changes to AppointmentView.fxml
     * <p>
     *     This method contains a listener when filtered by week, month, type and contacts.
     *     This method leverages lambda to create a FilteredList for the listener
     *     allowing user to view different reports on the screen.
     * </p>
     *
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Index.d.getAppointmentSQL();
        totalAppnt.setText(Index.rb.getString("appntTotal"));
        textType.setPromptText(Index.rb.getString("appntTypeFilter"));
        weekFilter.setPromptText(Index.rb.getString("appntWeekFilter"));
        monthFilter.setPromptText(Index.rb.getString("appntMonthFilter"));
        filterContacts.setPromptText(Index.rb.getString("appntContactFilter"));
        weekFilter.setItems(Index.d.getWeekList());
        ObservableList<String> list = FXCollections.observableArrayList();

        for (Months m : Index.d.getMonthList()) {
            list.add(m.getName());
        }

        monthFilter.setItems(list);

        ObservableList<String> conList = FXCollections.observableArrayList("All");

        for (Contacts c : Index.d.getContactList()) {
            conList.add(c.getName());
        }

        filterContacts.setItems(conList);

        FilteredList<Appointments> filteredListWeek = new FilteredList<>(Index.d.getAppointmentList(), appointments
                -> appointments.getAppnt_StartDateTime().get(Index.weekFields.weekOfWeekBasedYear()) == Index.weekNumNow);

        FilteredList<Appointments> filteredListMonth = new FilteredList<>(Index.d.getAppointmentList(), appointments
                -> appointments.getAppnt_StartDateTime().getMonthValue() == Index.monthNumNow);

        colAppntIDWeekly.setCellValueFactory(new PropertyValueFactory<>("appnt_ID"));
        colAppntTitleWeekly.setCellValueFactory(new PropertyValueFactory<>("appnt_Title"));
        colAppntDescriptionWeekly.setCellValueFactory(new PropertyValueFactory<>("appnt_Description"));
        colAppntLocationWeekly.setCellValueFactory(new PropertyValueFactory<>("appnt_Location"));
        colAppntContactWeekly.setCellValueFactory(new PropertyValueFactory<>("appnt_Contact"));
        colAppntTypeWeekly.setCellValueFactory(new PropertyValueFactory<>("appnt_Type"));
        colAppntStartWeekly.setCellValueFactory(new PropertyValueFactory<>("appnt_StartDateTime"));
        colAppntEndWeekly.setCellValueFactory(new PropertyValueFactory<>("appnt_EndDateTime"));
        colCustomerIDWeekly.setCellValueFactory(new PropertyValueFactory<>("cus_ID"));
        colUserIDWeekly.setCellValueFactory(new PropertyValueFactory<>("u_ID"));

        appntTableWeekly.setItems(filteredListWeek);

        colAppntIDMonthly.setCellValueFactory(new PropertyValueFactory<>("appnt_ID"));
        colAppntTitleMonthly.setCellValueFactory(new PropertyValueFactory<>("appnt_Title"));
        colAppntDescriptionMonthly.setCellValueFactory(new PropertyValueFactory<>("appnt_Description"));
        colAppntLocationMonthly.setCellValueFactory(new PropertyValueFactory<>("appnt_Location"));
        colAppntContactMonthly.setCellValueFactory(new PropertyValueFactory<>("appnt_Contact"));
        colAppntTypeMonthly.setCellValueFactory(new PropertyValueFactory<>("appnt_Type"));
        colAppntStartMonthly.setCellValueFactory(new PropertyValueFactory<>("appnt_StartDateTime"));
        colAppntEndMonthly.setCellValueFactory(new PropertyValueFactory<>("appnt_EndDateTime"));
        colCustomerIDMonthly.setCellValueFactory(new PropertyValueFactory<>("cus_ID"));
        colUserIDMonthly.setCellValueFactory(new PropertyValueFactory<>("u_ID"));

        appntTableMonthly.setItems(filteredListMonth);
        totalNum.setText(String.valueOf(appntTableMonthly.getItems().size()));

        appntTitle.setText(Index.rb.getString("appntMainTitle"));
        appntWeekly.setText(Index.rb.getString("appntWeeklyTab"));
        addButtonWeekly.setText(Index.rb.getString("appntAddButton"));
        modifyButtonWeekly.setText(Index.rb.getString("appntModifyButton"));
        deleteButtonWeekly.setText(Index.rb.getString("appntDeleteButton"));
        colAppntIDWeekly.setText(Index.rb.getString("appntID"));
        colAppntTitleWeekly.setText(Index.rb.getString("appntTitle"));
        colAppntDescriptionWeekly.setText(Index.rb.getString("appntDescription"));
        colAppntLocationWeekly.setText(Index.rb.getString("appntLocation"));
        colAppntContactWeekly.setText(Index.rb.getString("appntContact"));
        colAppntTypeWeekly.setText(Index.rb.getString("appntType"));
        colAppntStartWeekly.setText(Index.rb.getString("appntStart"));
        colAppntEndWeekly.setText(Index.rb.getString("appntEnd"));
        colCustomerIDWeekly.setText(Index.rb.getString("appntCustomerID"));
        colUserIDWeekly.setText(Index.rb.getString("appntUserID"));
        changeAppntTimeWeekly.setText(Index.rb.getString("appntChangeButton"));
        goBackButtonWeekly.setText(Index.rb.getString("appntGoBackButton"));

        appntTitle.setText(Index.rb.getString("appntMainTitle"));
        appntMonthly.setText(Index.rb.getString("appntMonthlyTab"));
        addButtonMonthly.setText(Index.rb.getString("appntAddButton"));
        modifyButtonMonthly.setText(Index.rb.getString("appntModifyButton"));
        deleteButtonMonthly.setText(Index.rb.getString("appntDeleteButton"));
        colAppntIDMonthly.setText(Index.rb.getString("appntID"));
        colAppntTitleMonthly.setText(Index.rb.getString("appntTitle"));
        colAppntDescriptionMonthly.setText(Index.rb.getString("appntDescription"));
        colAppntLocationMonthly.setText(Index.rb.getString("appntLocation"));
        colAppntContactMonthly.setText(Index.rb.getString("appntContact"));
        colAppntTypeMonthly.setText(Index.rb.getString("appntType"));
        colAppntStartMonthly.setText(Index.rb.getString("appntStart"));
        colAppntEndMonthly.setText(Index.rb.getString("appntEnd"));
        colCustomerIDMonthly.setText(Index.rb.getString("appntCustomerID"));
        colUserIDMonthly.setText(Index.rb.getString("appntUserID"));
        changeAppntTimeMonthly.setText(Index.rb.getString("appntChangeButton"));
        goBackButtonMonthly.setText(Index.rb.getString("appntGoBackButton"));

        ObservableList<String> typeList = FXCollections.observableArrayList("All");
        HashMap<String, String> hashMap = new HashMap<>();
        for (Appointments a : Index.d.getAppointmentList()) {
            hashMap.put(a.getAppnt_Type(), null);
        }
        typeList.addAll(hashMap.keySet());
        typeList.sort(Comparator.naturalOrder());
        textType.setItems(typeList);

        // leverages lambda to create a FilteredList when using weekFilter ComboxBox
        weekFilter.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldFilter, newFilter) -> {
                    int i = Index.weekNumNow;

                    if (weekFilter.getValue().contains("Last Week")) {
                        i = Index.weekNumNow - 1;
                    } else if (weekFilter.getValue().contains("Next Week")) {
                        i = Index.weekNumNow + 1;
                    }

                    int finalI = i;
                    FilteredList<Appointments> filteredWeek = new FilteredList<>(Index.d.getAppointmentList(), appointments
                            -> appointments.getAppnt_StartDateTime().get(Index.weekFields.weekOfWeekBasedYear())
                            == finalI);
                    if (!Objects.isNull(textType.getValue()) && !Objects.isNull(filterContacts.getValue())) {
                        if (Objects.equals(textType.getValue(), "All")) {
                            FilteredList<Appointments> allTypeWeek = new FilteredList<>(filteredWeek, appointments
                                    -> appointments.getAppnt_Contact().contains(filterContacts.getValue()));

                            appntTableWeekly.setItems(allTypeWeek);
                        } else if (Objects.equals(filterContacts.getValue(), "All")) {
                            FilteredList<Appointments> allContactWeek = new FilteredList<>(filteredWeek, appointments
                                    -> appointments.getAppnt_Type().contains(textType.getValue()));

                            appntTableWeekly.setItems(allContactWeek);
                        } else {
                            FilteredList<Appointments> typeWeek = new FilteredList<>(filteredWeek, appointments
                                    -> appointments.getAppnt_Contact().contains(filterContacts.getValue()));

                            FilteredList<Appointments> contactWeek = new FilteredList<>(typeWeek, appointments
                                    -> appointments.getAppnt_Type().contains(textType.getValue()));

                            appntTableWeekly.setItems(contactWeek);
                        }

                        if (Objects.equals(textType.getValue(), "All")
                                && Objects.equals(filterContacts.getValue(), "All")) {
                            appntTableWeekly.setItems(filteredWeek);
                        }
                    } else {
                        appntTableWeekly.setItems(filteredWeek);
                    }
                } );

        // leverages lambda to create a FilteredList when using monthFilter ComboBox
        monthFilter.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldFilter, newFilter) -> {
                    int i = 0;

                    for (Months m : Index.d.getMonthList()) {
                        if (m.getName().contains(monthFilter.getValue())) {
                            i = m.getId();
                        }
                    }

                    int finalI = i;
                    FilteredList<Appointments> filteredMonth = new FilteredList<>(Index.d.getAppointmentList(), appointments
                            -> appointments.getAppnt_StartDateTime().getMonthValue() == finalI);
                    if (!Objects.isNull(textType.getValue()) && !Objects.isNull(filterContacts.getValue())) {
                        if (Objects.equals(textType.getValue(), "All")) {
                            FilteredList<Appointments> allTypeMonth = new FilteredList<>(filteredMonth, appointments
                                    -> appointments.getAppnt_Contact().contains(filterContacts.getValue()));

                            appntTableMonthly.setItems(allTypeMonth);
                        } else if (Objects.equals(filterContacts.getValue(), "All")) {
                            FilteredList<Appointments> allContactMonth = new FilteredList<>(filteredMonth, appointments
                                    -> appointments.getAppnt_Type().contains(textType.getValue()));

                            appntTableMonthly.setItems(allContactMonth);
                        } else {
                            FilteredList<Appointments> typeMonth = new FilteredList<>(filteredMonth, appointments
                                    -> appointments.getAppnt_Contact().contains(filterContacts.getValue()));

                            FilteredList<Appointments> contactMonth = new FilteredList<>(typeMonth, appointments
                                    -> appointments.getAppnt_Type().contains(textType.getValue()));

                            appntTableMonthly.setItems(contactMonth);
                        }

                        if (Objects.equals(textType.getValue(), "All")
                                && Objects.equals(filterContacts.getValue(), "All")) {
                            appntTableMonthly.setItems(filteredMonth);
                        }
                    } else {
                        appntTableMonthly.setItems(filteredMonth);
                    }

                    totalNum.setText(String.valueOf(appntTableMonthly.getItems().size()));
                } );

        // leverages lambda to create a FilteredList when using textType ComboBox
        textType.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldFilter, newFilter) -> {
                    weekFilter.getSelectionModel().select("This Week");
                    monthFilter.getSelectionModel().select(Index.d.getMonthList().get(Index.monthNumNow - 1).getName());

                    FilteredList<Appointments> filteredWeek = new FilteredList<>(Index.d.getAppointmentList(), appointments
                            -> appointments.getAppnt_StartDateTime().get(Index.weekFields.weekOfWeekBasedYear())
                            == Index.weekNumNow);
                    FilteredList<Appointments> filteredMonth = new FilteredList<>(Index.d.getAppointmentList(), appointments
                            -> appointments.getAppnt_StartDateTime().getMonthValue() == Index.monthNumNow);

                    if (!Objects.equals(textType.getValue(), "All")) {
                        FilteredList<Appointments> filteredTypeWeek = new FilteredList<>(filteredWeek, appointments
                                -> appointments.getAppnt_Type().contains(textType.getValue()));
                        FilteredList<Appointments> filteredTypeMonth = new FilteredList<>(filteredMonth, appointments
                                -> appointments.getAppnt_Type().contains(textType.getValue()));

                        if (!Objects.isNull(filterContacts.getValue()) && !Objects.equals(filterContacts.getValue(), "All")) {
                            FilteredList<Appointments> filteredContactWeek = new FilteredList<>(filteredTypeWeek, appointments
                                    -> appointments.getAppnt_Contact().contains(filterContacts.getValue()));
                            FilteredList<Appointments> filteredContactMonth = new FilteredList<>(filteredTypeMonth, appointments
                                    -> appointments.getAppnt_Contact().contains(filterContacts.getValue()));

                            appntTableWeekly.setItems(filteredContactWeek);
                            appntTableMonthly.setItems(filteredContactMonth);
                        } else {
                            appntTableWeekly.setItems(filteredTypeWeek);
                            appntTableMonthly.setItems(filteredTypeMonth);
                        }
                    } else {
                        appntTableWeekly.setItems(filteredWeek);
                        appntTableMonthly.setItems(filteredMonth);
                    }

                    totalNum.setText(String.valueOf(appntTableMonthly.getItems().size()));
                } );

        // leverages lambda to create a FilteredList when using filterContacts ComboBox
        filterContacts.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldFilter, newFilter) -> {
                    weekFilter.getSelectionModel().select("This Week");
                    monthFilter.getSelectionModel().select(Index.d.getMonthList().get(Index.monthNumNow - 1).getName());

                    FilteredList<Appointments> filteredWeek = new FilteredList<>(Index.d.getAppointmentList(), appointments
                            -> appointments.getAppnt_StartDateTime().get(Index.weekFields.weekOfWeekBasedYear())
                            == Index.weekNumNow);
                    FilteredList<Appointments> filteredMonth = new FilteredList<>(Index.d.getAppointmentList(), appointments
                            -> appointments.getAppnt_StartDateTime().getMonthValue() == Index.monthNumNow);

                    if (!Objects.equals(filterContacts.getValue(), "All")) {
                        FilteredList<Appointments> filteredContactWeek = new FilteredList<>(filteredWeek, appointments
                                -> appointments.getAppnt_Contact().contains(filterContacts.getValue()));
                        FilteredList<Appointments> filteredContactMonth = new FilteredList<>(filteredMonth, appointments
                                -> appointments.getAppnt_Contact().contains(filterContacts.getValue()));

                        if (!Objects.isNull(textType.getValue()) && !Objects.equals(textType.getValue(), "All")) {
                            FilteredList<Appointments> filteredTypeWeek = new FilteredList<>(filteredContactWeek, appointments
                                    -> appointments.getAppnt_Type().contains(textType.getValue()));
                            FilteredList<Appointments> filteredTypeMonth = new FilteredList<>(filteredContactMonth, appointments
                                    -> appointments.getAppnt_Type().contains(textType.getValue()));

                            appntTableWeekly.setItems(filteredTypeWeek);
                            appntTableMonthly.setItems(filteredTypeMonth);
                        } else {
                            appntTableWeekly.setItems(filteredContactWeek);
                            appntTableMonthly.setItems(filteredContactMonth);
                        }
                    } else {
                        appntTableWeekly.setItems(filteredWeek);
                        appntTableMonthly.setItems(filteredMonth);
                    }

                    totalNum.setText(String.valueOf(appntTableMonthly.getItems().size()));
                } );
    }
}
