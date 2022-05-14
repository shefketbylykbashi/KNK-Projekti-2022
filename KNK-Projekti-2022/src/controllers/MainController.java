package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controllers.LogInController;
import util.Util;

import java.io.IOException;

public class MainController {

    @FXML
    void adminClicked(ActionEvent event) throws IOException {
        openLogIn(Util.TYPE_ADMIN, event);
    }

    @FXML
    void receptionistClicked(ActionEvent event) throws IOException {
        openLogIn(Util.TYPE_RECEPTIONIST, event);
    }

    @FXML
    void doctorClicked(ActionEvent event) throws IOException {
        openLogIn(Util.TYPE_DOCTOR, event);
    }

    private void openLogIn(String loginType, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/ui/login/LogIn.fxml"));
        Parent parent = loader.load();
        Scene loginScene = new Scene(parent);

        Util.getInstance().setUserType(loginType);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(loginScene);
        window.show();

    }

}
