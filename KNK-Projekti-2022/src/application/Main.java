package application;

import java.util.ResourceBundle;

import controllersAndProcessor.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import modelAndRepository.LocaleBundle;
import modelAndRepository.WaitingRoom;




public class Main extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {

    	ResourceBundle bundle =  LocaleBundle.bundle(LoginController.lang);
        WaitingRoom.patientPushedFromPatientsScene = null;
        System.setProperty("prism.lcdtext", "false");
        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"),bundle);
        Scene scene = new Scene(root, 1280, 720);
        Image img = new Image("/resources/Group2.png");
        
        stage.setScene(scene);
        stage.getIcons().add(img);
        stage.setTitle("Cabinet++");
        stage.setResizable(true);

        stage.show();

    }
}
