package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Patient;
import model.WaitingRoom;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class ModifyPatientController implements Initializable {
    @FXML
    private TextField fisrt_name_txtf;
    @FXML
    private TextField second_name_txtf;

    @FXML
    private DatePicker date_of_birth_dp;
    @FXML
    private TextField phone_number_txtf;
    @FXML
    private TextField address_txtf;
    @FXML
    private Button add_btn;
    @FXML
    private ComboBox sex_combo;

    private LocalDate myDate = LocalDate.now();
    private Date dateSQL;
    private String dateformat;
    static public Patient selectedpatient;

    public void modify_Patient(ActionEvent event) throws IOException {
        Patient patient;

        if (fisrt_name_txtf.getText().trim().isBlank() || second_name_txtf.getText().trim().isBlank()
                || date_of_birth_dp == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "You must enter the first name, family name and date of birth");
            alert.showAndWait();
        } else {
            myDate = date_of_birth_dp.getValue();
            dateSQL = Date.valueOf(myDate.toString());
            String sdate =   myDate.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy"));
            if (sdate.equals(dateformat)) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You must enter the first name, family name and date of birth");
                alert.showAndWait();
            }
