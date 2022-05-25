package controllersAndProcessor;

import database.CnxWithDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import modelAndRepository.Medicament;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MedicamentsController extends Task<ObservableList<Medicament>> implements Initializable {
	    @FXML
	    private TableView<Medicament> table1;

	    @FXML
	    private TableColumn<Medicament, String> brand_1;

	    @FXML
	    private TableColumn<Medicament, String> form_1;

	    @FXML
	    private TableColumn<Medicament, String> dosage_1;

	    @FXML
	    private TextField search_field;

	    @FXML
	    private Button Done_table;

	    @FXML
	    private TableView<Medicament> table2;

	    @FXML
	    private TableColumn<Medicament, String> brand_2;

	    @FXML
	    private TableColumn<Medicament, String> form_2;

	    @FXML
	    private TableColumn<Medicament, String> dosage_2;

	    @FXML
	    private TableColumn<Medicament, String> morning;

	    @FXML
	    private TableColumn<Medicament, String> noon;

	    @FXML
	    private TableColumn<Medicament, String> evening;

	    @FXML
	    private Button Addtable;
	// i made this list static to ensure that not every time the list get retrieved again from the db
	// so it will be initialized once , after the login
	    public static ObservableList<Medicament> oblist = FXCollections.observableArrayList();
	    private ObservableList<Medicament> medocs = FXCollections.observableArrayList();
	    private FilteredList<Medicament> searchResultList;

	    public void addItem() {
	        Medicament medoc = table1.getSelectionModel().getSelectedItem();
	        medocs.add(medoc);
	        table2.setItems(medocs);
	    }

	    public void addT2todb(ActionEvent event){
	        for (Medicament medicament : medocs){
	            System.out.println("we will insert medicament with id "+AppConsController.oblist.get(0).consultation_id );
	            medicament.add(AppConsController.oblist.get(0).consultation_id);
	        }
	        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        window.close();



	    }

	    @Override
	    public void initialize(URL url, ResourceBundle resourceBundle) {

	        searchResultList = new FilteredList<>(oblist, b -> true);
	        search_field.textProperty().addListener((Observable, oldValue, newValue) -> {
	            searchResultList.setPredicate(patient -> {
	                if (newValue == null || newValue.isEmpty()) {
	                    return true;
	                }
	                String lowerCaseFilter = newValue.toLowerCase();
	                if (patient.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
	                    return true;
	                } else return false;
	            });
	        });

	        brand_1.setCellValueFactory(new PropertyValueFactory<>("name"));
	        form_1.setCellValueFactory(new PropertyValueFactory<>("form"));
	        dosage_1.setCellValueFactory(new PropertyValueFactory<>("dosage"));
	        table1.setItems(searchResultList);
	        brand_2.setCellValueFactory(new PropertyValueFactory<>("name"));
	        form_2.setCellValueFactory(new PropertyValueFactory<>("form"));
	        dosage_2.setCellValueFactory(new PropertyValueFactory<>("dosage"));
	        morning.setCellValueFactory(new PropertyValueFactory<>("checkBox_morning"));
	        noon.setCellValueFactory(new PropertyValueFactory<>("checkBox_noon"));
	        evening.setCellValueFactory(new PropertyValueFactory<>("checkBox_evening"));


	    }

	    @Override
	    protected ObservableList<Medicament> call() throws Exception {
	        try {
	            int i=0;
	            Connection cnx = CnxWithDB.getConnection();
	            ResultSet rs = cnx.createStatement().executeQuery("select * from medicaments ");
	            while (rs.next()) {
	                oblist.add(new Medicament(rs.getString("name"), rs.getString("form"), rs.getString("dosage")));

	                if (i%400 == 0) {
	                    this.updateMessage("Loading medecines");
	                }
	                else if(i%400 == 99 ) {
	                    this.updateMessage("Loading medecines.");
	                }
	                else if(i%400 == 199 ) {
	                    this.updateMessage("Loading medecines..");
	                }
	                else if(i%400 == 299 ) {
	                    this.updateMessage("Loading medecines...");
	                }
	                i++;
	                this.updateProgress(i,1000);
	            }

	        } catch (SQLException e) {
	            Logger.getLogger(MedicamentsController.class.getName()).log(Level.SEVERE, null, e);
	        }
	        return oblist;
	    }
}
