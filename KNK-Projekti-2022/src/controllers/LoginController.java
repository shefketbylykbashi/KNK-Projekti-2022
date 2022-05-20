package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Appointment;
import model.Login;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import database.CnxWithDB;
/**
 * @author AyoubFRIHAOUI
 * @author WalidABDALLAOUI
 */
public class LoginController {
    @FXML
    public AnchorPane back_pane;
    @FXML
    public AnchorPane left_pane;
    @FXML
    public Circle back_circle;
    @FXML
    public Label select_user_label;
    @FXML
    public Button user_button_1;
    @FXML
    public Button user_button_2;
    @FXML
    public Label login_label;
    @FXML
    public Button user_button_3;
    @FXML
    public Label password_label;
    @FXML
    public PasswordField password_field;
    @FXML
    public Button submit_button;
    //this invisible label is used only to retrieve username text from button
    // , whenever you click on a button this label will get the username text
    @FXML
    public Label invisibleUsernameLabel;
    public void user_button_1OnAction(ActionEvent event) {
        invisibleUsernameLabel.setText(user_button_1.getText());
        cleanButtonStyles(user_button_1);
        cleanButtonStyles(user_button_2);
        cleanButtonStyles(user_button_3);
        user_button_1.getStyleClass().add("userbuttonChecked");
        user_button_2.getStyleClass().add("userbutton");
        user_button_3.getStyleClass().add("userbutton");
    }
    public void user_button_2OnAction(ActionEvent event) {
        invisibleUsernameLabel.setText(user_button_2.getText());
        cleanButtonStyles(user_button_1);
        cleanButtonStyles(user_button_2);
        cleanButtonStyles(user_button_3);
        user_button_1.getStyleClass().add("userbutton");
        user_button_2.getStyleClass().add("userbuttonChecked");
        user_button_3.getStyleClass().add("userbutton");
    }
    public void user_button_3OnAction(ActionEvent event) {
        invisibleUsernameLabel.setText(user_button_3.getText());
        cleanButtonStyles(user_button_1);
        cleanButtonStyles(user_button_2);
        cleanButtonStyles(user_button_3);
        user_button_1.getStyleClass().add("userbutton");
        user_button_2.getStyleClass().add("userbutton");
        user_button_3.getStyleClass().add("userbuttonChecked");
    }
    //whenever the mouse is moved update the usernames from the db ,
    // this will allow us to have runtime synchironisation between the users db and login window
    public void back_paneOnMouseMoved(MouseEvent event) {
        model.Login.initUsernameButtons(user_button_1, user_button_2, user_button_3);
    }
    
//    public static String generateSalted() {
//		Random random = new SecureRandom();
//		int length = 64;
//		
//	    StringBuilder sb = new StringBuilder(length);
//	    for (int i = 0; i < length; i++) {
//	      int c = random.nextInt(62);
//	      if (c <= 9) {
//	        sb.append(String.valueOf(c));
//	      } else if (c < 36) {
//	        sb.append((char) ('a' + c - 10));
//	      } else {
//	        sb.append((char) ('A' + c - 36));
//	      }
//	      
//	    }
//	    System.out.println("salted: " + sb.toString());
//	    
//	    return sb.toString();
//	    
//	  }
    
 
   
   
    private static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
    
    public static String generate(String password, String salted) throws NoSuchAlgorithmException {
		String originalString = password + salted;
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hashbytes = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
		String sha256Text = bytesToHex(hashbytes);
		return sha256Text;
	}

    public String salted;
    static public Connection cnx = CnxWithDB.getConnection();
    public void submit_buttonOnAction(ActionEvent event) throws IOException,NoSuchAlgorithmException {
        String user = invisibleUsernameLabel.getText();
        String password = password_field.getText();
        
        String query = "SELECT salted FROM users WHERE username = ? ";
        
        try {
        	
            PreparedStatement st = cnx.prepareStatement(query);
            st.setString(1,user);
            ResultSet rs = st.executeQuery();
            if(rs.next()){

                salted = rs.getString("salted");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        
        String saltedHash = LoginController.generate(password, salted);
        
        int user_id = Login.login(user, saltedHash);
        //show error alert
        if (user_id == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The password entered is incorrect.");
            password_field.setText("");
            alert.showAndWait();
        }
        //close the login window , show successful login alert ,
        // open the main app window and set the user_id in the window Controller to the user_id retrieved from login
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Logged in succesfuly!");
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.close();
            alert.showAndWait();
            controllers.AppController.setUser_id(user_id);
            openAppWindow(window);
        }
    }
    public void cleanButtonStyles(Button b) {
        b.getStyleClass().remove("userbutton");
        b.getStyleClass().remove("userbuttonChecked");
    }
    // open the application main window
    public void openAppWindow(Stage window) throws IOException {
        Parent appParent;
        int x,y;
        boolean a=false;
        if (AppController.user_id == 1) {
            appParent = FXMLLoader.load(getClass().getResource("/views/AppAssist.fxml"));
            x=1280;
            y=720;
            a=true;

        } else  {
            appParent = FXMLLoader.load(getClass().getResource("/views/SplashScreen.fxml"));
            x=600;
            y=400;
        }
        Scene appScene = new Scene(appParent, x, y);
        appScene.getStylesheets().add(getClass().getResource("/views/AppStyling.css").toExternalForm());
        appScene.setFill(Color.TRANSPARENT);
        Image img = new Image("/resources/Group2.png");
        window.getIcons().add(img);
        window.setTitle("Cabinet++");
        window.setScene(appScene);
        window.setResizable(a);
        window.show();
    }
}