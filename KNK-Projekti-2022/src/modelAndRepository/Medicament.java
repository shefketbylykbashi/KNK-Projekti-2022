package modelAndRepository;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import database.CnxWithDB;
import javafx.scene.control.CheckBox;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Medicament {
    static public Connection cnx = CnxWithDB.getConnection();
    private String name;
    private String form;
    private String dosage;
    private CheckBox checkBox_morning;
    private CheckBox checkBox_noon;
    private CheckBox checkBox_evening;
    public int id ;

    public CheckBox getCheckBox_morning() {
        return checkBox_morning;
    }

    public CheckBox getCheckBox_noon() {
        return checkBox_noon;
    }

    public CheckBox getCheckBox_evening() {
        return checkBox_evening;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setCheckBox_morning(CheckBox checkBox_morning) {
        this.checkBox_morning = checkBox_morning;
    }

    public void setCheckBox_midi(CheckBox checkBox_noon) {
        this.checkBox_noon = checkBox_noon;
    }

    public void setCheckBox_soir(CheckBox checkBox_evening) {
        this.checkBox_evening = checkBox_evening;
    }

    public String getName() {
        return name;
    }

    public String getForm() {
        return form;
    }

    public String getDosage() {
        return dosage;
    }

    public Medicament(String name, String form, String dosage) {
        this.id = getId(name, form, dosage);
        this.name = name;
        this.form = form;
        this.dosage = dosage;
        this.checkBox_morning = new CheckBox();
        this.checkBox_noon = new CheckBox();
        this.checkBox_evening = new CheckBox();
    }

    static int getId(String a, String b, String c) {

        String query = "SELECT id FROM medicaments WHERE name = ? AND form = ? AND dosage = ?";
        int medId = 0;
        try {
            PreparedStatement pr = cnx.prepareStatement(query);
            pr.setString(1, a);
            pr.setString(2, b);
            pr.setString(3, c);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                medId = rs.getInt("id");


            }


        } catch (SQLException e) {

            e.printStackTrace();

        }

        return medId;
    }

    public void add(int consult_id) {


        this.id = getId(this.name,this.form,this.dosage);
        System.out.println("medicament id = "+this.id);

        try {
            int c1, c2, c3;
            c1 = ((checkBox_morning.isSelected()) ? 1 : 0);
            c2 = ((checkBox_noon.isSelected()) ? 1 : 0);
            c3 = ((checkBox_evening.isSelected()) ? 1 : 0);


            String sql = " INSERT INTO prescription " +
                    " (consult_id,med_id,morning,noon,evening) " +
                    "VALUES ('" + consult_id + "','" + this.id + "','" + c1 + "','" + c2 + "','" + c3 + "')";


            Statement st = cnx.createStatement();
            st.executeUpdate(sql);
            System.out.println("Added medicament to perscription");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}