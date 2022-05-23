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
