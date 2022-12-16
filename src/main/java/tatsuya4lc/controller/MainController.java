package tatsuya4lc.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tatsuya4lc.helper.*;
import tatsuya4lc.model.Appointments;
import tatsuya4lc.scheduler.Index;
import tatsuya4lc.model.Customers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is a class Controller for the Customer Information window.
 * This class provides logic for the MainView.fxml
 */
public class MainController implements Initializable {

    @FXML
    private Label winTitle;

    @FXML
    private Button addButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<Customers> tableViewCustomers;

    @FXML
    private TableColumn<Customers, Integer> colCustomerID;

    @FXML
    private TableColumn<Customers, String> colCustomerName;

    @FXML
    private TableColumn<Customers, String> colCustomerAddress;

    @FXML
    private TableColumn<Customers, String> colCustomerLocation;

    @FXML
    private TableColumn<Customers, String> colCustomerCountry;

    @FXML
    private TableColumn<Customers, String> colCustomerPostalCode;

    @FXML
    private TableColumn<Customers, String> colCustomerPhoneNumber;

    @FXML
    private Button appntButton;

    @FXML
    private Button exitButton;

    /**
     * This is the onAddCustomer method.
     * This method opens CustomerView scene
     * allowing user to add new Customer
     *
     * @param event the event that the button was pressed
     */
    @FXML
    void onAddCustomer(ActionEvent event) {
        Index.switchWindow(event, 2);
    }

    /**
     * This is the onModifyCustomer method.
     * This method is for the update button.
     * checks if an item is selected to prevent runtime error
     * then calls for a method from the Index to change scene
     * and opens the menu to modifying Customer's Information
     *
     * @param event the event that the button was pressed
     */
    @FXML
    void onModifyCustomer(ActionEvent event) {
        if (!tableViewCustomers.getSelectionModel().isEmpty()) {
            Customers c = tableViewCustomers.getSelectionModel().getSelectedItem();
            Index.switchWindow(event, 2, c, null);
            tableViewCustomers.getSelectionModel().clearSelection();
        }
    }

    /**
     * This is the onRemoveCustomer method.
     * This method removes Customer's Information by sending the selected
     * customer to a method in the Database class
     *
     * Includes an Alert confirmation before deletion
     * and Alert warning when there's an associated appointment to confirm
     * both appointment cancellation and customer deletion
     */
    @FXML
    void onRemoveCustomer() {
        Customers c = tableViewCustomers.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result;

        if (!tableViewCustomers.getSelectionModel().isEmpty()) {
            boolean cancel = false;

            for (Appointments a : Index.d.getAppointmentList()) {
                if (c.getCus_ID() == a.getCus_ID()) {
                    cancel = true;
                    break;
                }
            }

            if (!cancel) {
                alert.setTitle(Index.rb.getString("mainConfirmation"));
                alert.setHeaderText(c.getCus_Name() + Index.rb.getString("mainConfirmText1"));
                alert.setContentText(Index.rb.getString("mainConfirm"));

            } else {
                alert.setTitle(Index.rb.getString("mainWarning"));
                alert.setHeaderText(Index.rb.getString("mainConfirmText2"));
                alert.setContentText(Index.rb.getString("mainConfirmDelete"));

            }
            result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Index.d.deleteAssociatedAppntSQL(c.getCus_ID());
                Index.d.deleteCustomerSQL(c);
            }
        }
    }

    /**
     * This is the onOpenAppnt method.
     * This method open the AppointmentView scene
     *
     * @param event the event that the button was pressed
     */
    @FXML
    void onOpenAppnt(ActionEvent event) {
        Index.switchWindow(event, 3);
    }

    /**
     * This is the onMainExit method.
     * This method exits the application.
     */
    @FXML
    void onMainExit() {
        JDBC.closeConnection();
        System.exit(0);
    }

    /**
     * This is the onSelectionCustomer method.
     * This method set Update, Remove button to be enabled
     * when an item is selected
     */
    @FXML
    void onSelectionCustomer() {
            if (!tableViewCustomers.getSelectionModel().isEmpty()) {
                modifyButton.setDisable(false);
                deleteButton.setDisable(false);
            }
    }

    /**
     * This is the mouseClickDeselect method.
     * This method set certain button to be disabled
     * when an item is deselected
     */
    @FXML
    void mouseClickDeselect() {
        tableViewCustomers.getSelectionModel().clearSelection();
        modifyButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Index.d.getCustomerSQL();
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("cus_ID"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("cus_Name"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("cus_Address"));
        colCustomerLocation.setCellValueFactory(new PropertyValueFactory<>("cus_Location"));
        colCustomerCountry.setCellValueFactory(new PropertyValueFactory<>("cus_Country"));
        colCustomerPostalCode.setCellValueFactory(new PropertyValueFactory<>("cus_PostalCode"));
        colCustomerPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("cus_Phone"));

        tableViewCustomers.setItems(Index.d.getCustomerList());

        winTitle.setText(Index.rb.getString("mainTitle"));
        addButton.setText(Index.rb.getString("mainNewButton"));
        modifyButton.setText(Index.rb.getString("mainUpdateButton"));
        deleteButton.setText(Index.rb.getString("mainRemoveButton"));
        appntButton.setText(Index.rb.getString("mainAppntButton"));
        exitButton.setText(Index.rb.getString("mainExitButton"));

        colCustomerID.setText(Index.rb.getString("mainID"));
        colCustomerName.setText(Index.rb.getString("mainName"));
        colCustomerAddress.setText(Index.rb.getString("mainAddress"));
        colCustomerLocation.setText(Index.rb.getString("mainLocation"));
        colCustomerCountry.setText(Index.rb.getString("mainCountry"));
        colCustomerPostalCode.setText(Index.rb.getString("mainPostalCode"));
        colCustomerPhoneNumber.setText(Index.rb.getString("mainPhoneNumber"));
    }
}
