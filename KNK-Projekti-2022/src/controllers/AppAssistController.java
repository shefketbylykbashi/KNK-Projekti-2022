package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.WaitingRoom;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.FxmlLoader;

public class AppAssistController implements Initializable {
    @FXML
    private BorderPane mainPane;
    @FXML
    private AnchorPane anchor_tabs_container;
    @FXML
    private AnchorPane dash_pane;
    @FXML
    private VBox vb_dashboard;
    @FXML
    private Button btn_home;
    @FXML
    private HBox hb_home;
    @FXML
    private Label lab_home;
    @FXML
    private Button btn_watingroom;
    @FXML
    private HBox hb_waitingroom;
    @FXML
    private Button btn_patients;
    @FXML
    private HBox hb_patients;
    @FXML
    private Button btn_appointments;
    @FXML
    private HBox hb_appointments;
    @FXML
    private Button btn_logout;
    @FXML
    private HBox hb_logout;
