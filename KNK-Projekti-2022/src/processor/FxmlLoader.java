package processor;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import modelAndRepository.LocaleBundle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controllersAndProcessor.LoginController;
import controllersAndProcessor.AppController;

public class FxmlLoader {
    private Pane view;
    public Pane getPage(String fileName) {
    	ResourceBundle bundle =  LocaleBundle.bundle(LoginController.lang);
        try {
            URL fileUrl = AppController.class.getResource("/views/" + fileName + ".fxml");
            if (fileUrl == null ) {
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }
            view = new FXMLLoader().load(fileUrl,bundle);
        }
        catch(Exception e) {

            e.printStackTrace();
            System.out.println("fxml cant be found");
        }
        return view;
    }
}
