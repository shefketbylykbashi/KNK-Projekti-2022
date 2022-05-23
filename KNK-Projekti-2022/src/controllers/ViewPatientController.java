package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Patient;
import model.WaitingRoom;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ViewPatientController implements Initializable {
    @FXML
    private TextField fisrt_name_txtf;
    @FXML
    private TextField second_name_txtf;
    @FXML
    private TextField sex_txtf;
    @FXML
    private DatePicker date_of_birth_dp;
    @FXML
    private TextField phone_number_txtf;
    @FXML
    private TextField address_txtf;
    @FXML
    private Button add_btn;
    
    private LocalDate myDate = LocalDate.now();
    private Date dateSQL;
    private String dateformat;
    static public Patient selectedpatient;
