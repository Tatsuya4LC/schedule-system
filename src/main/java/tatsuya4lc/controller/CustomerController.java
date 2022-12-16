package tatsuya4lc.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tatsuya4lc.model.Customers;
import tatsuya4lc.model.Locations;
import tatsuya4lc.scheduler.Index;

import java.net.URL;
import java.util.Comparator;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * This is a class Controller for adding/updating Customer Information window.
 * This class provides logic for the CustomerView.fxml
 */
public class CustomerController implements Initializable {
    boolean updateCustomer = false;
    int cus_ID;

    @FXML
    private Label winTitleCustomer;

    @FXML
    private Label labelCustomerID;

    @FXML
    private TextField textCustomerID;

    @FXML
    private Label labelCustomerName;

    @FXML
    private TextField textCustomerName;

    @FXML
    private Label labelCustomerAddress;

    @FXML
    private TextField textCustomerAddress;

    @FXML
    private Label labelCustomerPostalCode;

    @FXML
    private TextField textCustomerPostalCode;

    @FXML
    private Label labelCustomerPhone;

    @FXML
    private TextField textCustomerPhoneNumber;

    @FXML
    private Label labelCustomerLocation;

    @FXML
    private ComboBox<String> textCustomerCountry;

    @FXML
    private ComboBox<String> textCustomerLocation;

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;

    /**
     * This is the onCancel method.
     * This method changes the scene to the previous scene.
     *
     * @param event the event that the button was pressed
     */
    @FXML
    void onCancel(ActionEvent event) {
        Index.switchWindow(event, 1);
    }

    /**
     * This is the onSave method.
     * This method retrieves data from the TextFields to add/update Customer's Information.
     * Checks for empty TextFields
     *
     * @param event the event that the button was pressed
     */
    @FXML
    void onSave(ActionEvent event) {
        int l_ID = 0;
        boolean empty = false;
        String cus_Name = textCustomerName.getText();
        String cus_Address = textCustomerAddress.getText();
        String cus_PostalCode = textCustomerPostalCode.getText();
        String cus_Phone = textCustomerPhoneNumber.getText();
        String location = textCustomerLocation.getValue();

        Predicate<String> emptyField = (s) -> Objects.equals(s, "");

        for (Locations l : Index.d.getLocationList()) {
            if (l.getLocationName().equals(location)) {
                l_ID = l.getLocationID();
            }
        }

        if (emptyField.test(cus_Name)
                || emptyField.test(cus_Address)
                || emptyField.test(cus_PostalCode)
                || emptyField.test(cus_Phone)
                || Objects.isNull(location)) {
            empty = true;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(Index.rb.getString("customerNotComplete"));
            alert.setHeaderText(Index.rb.getString("customerNoBlank"));
            alert.setContentText(null);

            alert.showAndWait();
        }

        if (!updateCustomer && !empty) {
            Index.d.addCustomerSQL(new Customers(0, cus_Name, cus_Address, null, null,
                    cus_PostalCode, cus_Phone, l_ID));
            Index.switchWindow(event, 1);
        } else if (!empty) {
            Index.d.updateCustomerSQL(new Customers(cus_ID, cus_Name, cus_Address, null, null,
                    cus_PostalCode, cus_Phone, l_ID));
            Index.switchWindow(event, 1);
        }
    }

    /**
     * This is the isModifyingCustomer method.
     * This method is called when updating Customer Information.
     *
     * @param c Customers object to extract information from
     */
    public void isModifyingCustomer (Customers c) {
        updateCustomer = true;
        cus_ID = c.getCus_ID();
        confirmButton.setText(Index.rb.getString("customerUpdateButton"));
        textCustomerID.setText(String.valueOf(c.getCus_ID()));
        textCustomerName.setText(c.getCus_Name());
        textCustomerAddress.setText(c.getCus_Address());
        textCustomerPostalCode.setText(c.getCus_PostalCode());
        textCustomerPhoneNumber.setText(c.getCus_Phone());
        textCustomerLocation.getSelectionModel().select(c.getCus_Location());
        textCustomerCountry.getSelectionModel().select(c.getCus_Country());
    }

    /**
     * This is the Override initialize method.
     * This method runs when scene change to CustomerView.fxml
     *
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textCustomerID.setText(Index.rb.getString("customerIDGen"));
        winTitleCustomer.setText(Index.rb.getString("customerTitle"));
        labelCustomerID.setText(Index.rb.getString("customerID"));
        labelCustomerName.setText(Index.rb.getString("customerName"));
        textCustomerName.setPromptText(Index.rb.getString("customerPromptName"));
        labelCustomerAddress.setText(Index.rb.getString("customerAddress"));
        textCustomerAddress.setPromptText(Index.rb.getString("customerPromptAddress"));
        labelCustomerPostalCode.setText(Index.rb.getString("customerPostalCode"));
        textCustomerPostalCode.setPromptText(Index.rb.getString("customerPromptPostalCode"));
        labelCustomerPhone.setText(Index.rb.getString("customerPhoneNumber"));
        textCustomerPhoneNumber.setPromptText(Index.rb.getString("customerPromptPhoneNumber"));
        labelCustomerLocation.setText(Index.rb.getString("customerLocationLabel"));
        confirmButton.setText(Index.rb.getString("customerAddButton"));
        cancelButton.setText(Index.rb.getString("customerCancelButton"));
        textCustomerCountry.setItems(Index.d.getCountryList());

        ObservableList<String> locList = FXCollections.observableArrayList();
        for (Locations l : Index.d.getLocationList()) {
            locList.add(l.getLocationName());
        }
        textCustomerLocation.setItems(locList);

        textCustomerCountry.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldCountry, newCountry) -> {
                    ObservableList<String> list = FXCollections.observableArrayList();
                    FilteredList<Locations> filteredList = new FilteredList<>(Index.d.getLocationList(), locations
                            -> locations.getCountryName().contains(textCustomerCountry.getValue()));

                    for (Locations l : filteredList) {
                        list.add(l.getLocationName());
                    }

                    list.sort(Comparator.naturalOrder());
                    textCustomerLocation.setItems(list);
                }
        );
    }
}
