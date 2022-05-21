package controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import model.Patient;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import database.CnxWithDB;
public class HomeController implements Initializable {
	
    @FXML
    private Label assitfullname;

    @FXML
    private Label med2fullname;

    @FXML
    private Label med1fullname;

    @FXML
    private Label patientsnumber;
    
    @FXML
    private PieChart pieChart;
 
    @FXML
    private LineChart lineChart;

    @FXML
    private Label todayincome;
    
    static public Connection cnx = CnxWithDB.getConnection();
    public static String assist;
    public static String med1;
    public static String med2;
    ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
    XYChart.Series series = new XYChart.Series<>();
    

    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        assitfullname.setText(assist);
        med1fullname.setText(med1);
        med2fullname.setText(med2);
        
        
       buildChartsData();
       pieChart.setTitle("APPOINTMENTS THROUGHOUT THE YEAR");
       pieChart.getData().addAll(data);
       
       lineChart.getData().addAll(series);
       
          
        
    }
    
    public void buildChartsData() {
    	
            try {            	
				String sql = 
						"select count(patient_id), monthname(rdv_date) from appointments where rdv_date between '2022-01-01' and '2022-12-31' group by monthname(rdv_date) order by month(rdv_date) ";
                
                ResultSet rs = cnx.createStatement().executeQuery(sql);
                
                while(rs.next()) {
                	this.data.add(new PieChart.Data(rs.getString(2), rs.getDouble(1)));
                	this.series.getData().add(new XYChart.Data<>(rs.getString(2), rs.getInt(1)));
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }
    }
    

}
