package modelAndRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.CnxWithDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Patient {

	static public Connection cnx = CnxWithDB.getConnection();

    boolean existInDb;
    private int patientId;
    private String first_name;
    private String second_name;
    private Date dateOfbirth;
    private String sex;
    private String adr;
    private String number;

    public Patient(String first_name, String second_name, Date dateOfbirth) {
        this(first_name, second_name, dateOfbirth, "M", "Prishtine", "0000000000");

    }

    public Patient(String first_name, String second_name, Date dateOfbirth, String sex, String adr, String number) {
        this.first_name = first_name;
        this.second_name = second_name;

        this.dateOfbirth = dateOfbirth;

        this.sex = sex;
        this.adr = adr;

        this.number = number;

        this.existInDb = exist();
        if (existInDb) {
            this.patientId = getPatientId();
        }

    }
    public String getFirst_name() {
        return first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public Date getDateOfbirth() {
        return dateOfbirth;
    }

    public String getSex() {
        return sex;
    }

    public String getAdr() {
        return adr;
    }

    public String getNumber() {
        return number;
    }
    public int getId() {
        return patientId;
    }
    
    
    public static ObservableList<Patient> search(String criteria, int id) {
        ObservableList<Patient> patients = FXCollections.observableArrayList();
        String sql;
        if (id == 1) {
            sql = "SELECT * FROM patients WHERE firstName LIKE '%" + criteria + "%'" + " OR secondName LIKE '%" + criteria + "%'";

        } else {
            System.out.println("you have changed user and perfoming search");
            sql = "SELECT * FROM patients WHERE patient_id IN (SELECT patient_id FROM associatedDoctors WHERE user_id = " + id + ") AND "
                    + "(firstName LIKE '%" + criteria + "%'" + " OR secondName LIKE '%" + criteria + "%' )";
        }

        try {

            Statement stmt = cnx.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                
                String firstName0 = rs.getString("firstName");
                String secondName0 = rs.getString("secondName");

                Date dateOfbirth0 = rs.getDate("dateOfbirth");
                String sex0 = rs.getString("sex");
                String adr0 = rs.getString("adr");
                String num0 = rs.getString("num");

                Patient patient = new Patient(firstName0, secondName0, dateOfbirth0, sex0, adr0, num0);
                patients.add(patient);

            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public static Patient getPatientById(int id) {

        try {

            Statement stmt = cnx.createStatement();
            String sql = "SELECT * FROM patients WHERE patient_id = " + id;
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                
                String firstName0 = rs.getString("firstName");
                String secondName0 = rs.getString("secondName");

                Date dateOfbirth0 = rs.getDate("dateOfbirth");
                String sex0 = rs.getString("sex");
                String adr0 = rs.getString("adr");
                String num0 = rs.getString("num");

                Patient patient = new Patient(firstName0, secondName0, dateOfbirth0, sex0, adr0, num0);

                return patient;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean add() {
        if (!existInDb) {

            try {

                Statement stmt = cnx.createStatement();

                String sql = "INSERT INTO patients"
                        + "(firstName,secondName,dateOfbirth,sex,adr,num) "
                        + "VALUES ('" + this.first_name + "','" + this.second_name + "','" + this.dateOfbirth + "','" + this.sex + "','" + this.adr + "','" + this.number + "')";
                //stmt.executeUpdate(sql);
                stmt.execute(sql);
                this.existInDb = true;

                System.out.println("Element added to the database .");
                this.patientId = getPatientId();
                System.out.println("ID After being added to the db " + this.patientId);
                stmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return exist();
    }

    public boolean delete() {
        if (existInDb) {

            try {

                Statement stmt = cnx.createStatement();

                String sql = "DELETE FROM patients WHERE firstName='" + this.first_name + "'AND secondName='" + this.second_name + "' AND dateOfbirth='" + this.dateOfbirth + "'";

                stmt.executeUpdate(sql);

                System.out.println("Element deleted .");
                this.existInDb = false;
                stmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return !exist();
    }


    public void modify() {

        if (existInDb) {
            Patient patientInDb = getPatientById(this.patientId);

            if (this.first_name.equals(patientInDb.first_name)
                    && this.second_name.equals(patientInDb.second_name)
                    && this.dateOfbirth.equals(patientInDb.dateOfbirth)
                    && this.sex.equals(patientInDb.sex)
                    && this.adr.equals(patientInDb.adr)
                    && this.number.equals(patientInDb.number)) {
                return;
            }

            String query = "UPDATE patients SET firstName = ? , secondName = ? , dateOfBirth = ? ,"
                    + " sex = ? , adr = ?  , num = ?"
                    + " WHERE patient_id = " + this.patientId;

            try (PreparedStatement st = cnx.prepareStatement(query)) {
                st.setString(1, this.first_name);
                st.setString(2, this.second_name);
                st.setDate(3, this.dateOfbirth);
                st.setString(4, this.sex);
                st.setString(5, this.adr);
                st.setString(6, this.number);

                st.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private boolean exist() {

        try {
            String query = "SELECT * FROM patients WHERE firstname = ? AND secondName = ? AND dateOfBirth =  ? ";

            PreparedStatement st = cnx.prepareStatement(query);
            st.setString(1, this.first_name);
            st.setString(2, this.second_name);
            st.setString(3, String.valueOf(this.dateOfbirth));

            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                return true;
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }

    @Override
    public String toString() {
        return "first_name=" + first_name + ", second_name=" + second_name + ", dateOfbirth=" + dateOfbirth + ", sex=" + sex + ", adr=" + adr + ", number=" + number + ", associatedDoctors=" + '\n';
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public void setDateOfbirth(Date dateOfbirth) {
        this.dateOfbirth = dateOfbirth;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setAssociatedDoctors(int id) {
        addAssociateDoctor(id);
    }

    public int getPatientId() {

        String query = "SELECT patient_id FROM patients WHERE firstName = ? AND secondName = ? AND dateOfBirth = ? ";

        try (PreparedStatement st = cnx.prepareStatement(query)) {

            st.setString(1, this.first_name);
            st.setString(2, this.second_name);
            st.setDate(3, this.dateOfbirth);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                return rs.getInt("patient_id");

            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;

    }


    public void addAssociateDoctor(int doctorId) {


        if (existInDb && doctorIdExist(doctorId)) {
            if (!associateDoctorAdded(doctorId)) {
                String query = "INSERT INTO associatedDoctors VALUES (?,?)";

                try (PreparedStatement st = cnx.prepareStatement(query)) {

                    st.setInt(1, this.patientId);
                    st.setInt(2, doctorId);
                    st.executeUpdate();
                    st.close();


                } catch (SQLException ex) {
                    Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

   
    private boolean associateDoctorAdded(int doctorId) {

        String query = "SELECT * FROM associatedDoctors WHERE patient_id = ? AND user_id = ?";

        try (PreparedStatement st = cnx.prepareStatement(query)) {

            st.setInt(1, this.patientId);
            st.setInt(2, doctorId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                return true;
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    private boolean doctorIdExist(int doctorId) {

        String query = "SELECT user_id FROM users WHERE user_id = " + doctorId;

        try (Statement st = cnx.createStatement()) {

            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                return true;
            }
            rs.close();
            st.close();

        } catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

}
