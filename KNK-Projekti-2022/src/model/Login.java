package model;

import database.CnxWithDB;
import controllers.HomeController;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author WalidABDALLAOUI
 */

public  class Login {

 
	
    static public Connection cnx = CnxWithDB.getConnection();
// initialize username buttons with real usernames from the users db
    public static void initUsernameButtons(Button b1, Button b2, Button b3) {
        String query = "SELECT username FROM users WHERE user_id = ?";
        try (PreparedStatement st = cnx.prepareStatement(query)) {
            st.setInt(1, 1);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                String us = rs.getString("username");
                b1.setText(us);
                HomeController.assist = us;
            }

            st.setInt(1, 2);
            rs = st.executeQuery();
            if(rs.next()) {
                String us =  rs.getString("username");
              b2.setText(us);
                HomeController.med1 = us;
            }


            st.setInt(1, 3);

            rs = st.executeQuery();
            if(rs.next()) {

                String us =  rs.getString("username");
                b3.setText(us);
                HomeController.med2 = us;


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }





}
