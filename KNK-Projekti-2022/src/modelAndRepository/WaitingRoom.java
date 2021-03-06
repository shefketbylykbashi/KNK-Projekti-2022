package modelAndRepository;


import database.CnxWithDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

import controllersAndProcessor.AppController;

public class WaitingRoom {
    private Date todayDate;
    static public Patient assistPatient =null;
    static public Patient currentPatient1 =null;
    static public Patient currentPatient2 =null;
    
    static public Patient patientPushedFromPatientsScene = null;
    static public Connection cnx = CnxWithDB.getConnection();

    public Date getTodayDate() {
        return todayDate;
    }

    public WaitingRoom() {
        long millis=System.currentTimeMillis();
         todayDate =new java.sql.Date(millis);
    }

    static  public void delete(int id){
        try {
             Statement stmt = cnx.createStatement();

            String sql = "DELETE FROM waitingroom WHERE rdv_id =  "+ id;

            stmt.executeUpdate(sql);

            System.out.println("Element deleted .");

            stmt.close();
           Appointment.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void clear(){

        String query = "DELETE FROM waitingroom WHERE rdv_date < ?";

        try {
            PreparedStatement pr = cnx.prepareStatement(query);
            pr.setDate(1,todayDate);
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   public void update(){
       System.out.println(this.todayDate);
        String query = "INSERT INTO waitingroom (rdv_id , patient_id , doctor_id , rdv_date) " +
                " SELECT * FROM appointments WHERE  rdv_date =  \""+this.todayDate+"\"";

       try {
           Statement pr = cnx.createStatement();

           System.out.println(query);
           pr.executeUpdate(query);

       } catch (SQLException e) {
           e.printStackTrace();
       }
   }


     public ObservableList<AppointmentSearchResult> search(String criteria , int id ){

        String query ="SELECT waitingroom.rdv_id , patients.patient_id , patients.firstName , patients.secondName ,waitingroom.doctor_id, patients.adr, patients.num FROM patients " +
                "INNER JOIN waitingroom ON patients.patient_id = waitingroom.patient_id" +
                " AND (rdv_date = '" + todayDate + "') AND (patients.firstName  LIKE '%" + criteria +  "%' OR patients.secondName LIKE '%" + criteria +"%') AND (doctor_id =" + id+") ORDER BY waitingroom.rdv_id";


        ObservableList<AppointmentSearchResult> patients = FXCollections.observableArrayList();

        try {
            Statement st = cnx.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                
                int rdv_id = rs.getInt("rdv_id");
                String firstName   = rs.getString("firstName") ;
                String secondName = rs.getString("secondName") ;

                int doctor_id = rs.getInt("doctor_id") ;
                String doctor_name = "";
                if (doctor_id == 2) {
                    doctor_name = "Endodontist";
                }
                if (doctor_id == 3) {
                    doctor_name = "Prosthetist";
                }
                String adr = rs.getString("adr") ;
                String num = rs.getString("num");
                AppointmentSearchResult patient = new AppointmentSearchResult(rdv_id,firstName,secondName,doctor_name,adr,num);
                patients.add(patient);
            }
            rs.close();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public ObservableList<AppointmentSearchResult> search(String criteria ){

        String query ="SELECT waitingroom.rdv_id , patients.patient_id , patients.firstName , patients.secondName ,waitingroom.doctor_id, patients.adr, patients.num FROM patients " +
                "INNER JOIN waitingroom ON patients.patient_id = waitingroom.patient_id" +
                " AND (rdv_date = '" + todayDate + "') AND (patients.firstName  LIKE '%" + criteria +  "%' OR patients.secondName LIKE '%" + criteria +"%')  ORDER BY waitingroom.rdv_id";

        ObservableList<AppointmentSearchResult> patients = FXCollections.observableArrayList();
        try {
            Statement st = cnx.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                
                int rdv_id = rs.getInt("rdv_id");
                String firstName   = rs.getString("firstName") ;
                String secondName = rs.getString("secondName") ;

                int doctor_id = rs.getInt("doctor_id") ;
                String doctor_name = "";
                if (doctor_id == 2) {
                    doctor_name = "Endodontist";
                }
                if (doctor_id == 3) {
                    doctor_name = "Prosthetist";
                }
                String adr = rs.getString("adr") ;
                String num = rs.getString("num");
                AppointmentSearchResult patient = new AppointmentSearchResult(rdv_id,firstName,secondName,doctor_name,adr,num);
                patients.add(patient);
            }
            rs.close();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }
    
    public void add(int paitent_id , int doctor_id){

        String query = "INSERT INTO waitingroom (patient_id , doctor_id , rdv_date ) VALUES (?,?,?)";
        try {

            PreparedStatement stmt = cnx.prepareStatement(query);
            stmt.setInt(1,paitent_id);
            stmt.setInt(2,doctor_id);
            stmt.setDate(3,this.todayDate);

            stmt.executeUpdate();

            System.out.println("Element Added .");

            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    static public void deleteCurrentPatient() {
        if (AppController.user_id != 1  ) {
            String query = " UPDATE patientsinconsultations"
                    +" SET patient_id = NULL"
                    +" WHERE doctor_id = "+ AppController.user_id;
            System.out.println(query);
            try{
                Statement st = cnx.createStatement();

                st.executeUpdate(query);
            } catch (SQLException E){
                E.printStackTrace();
            }
        }
    }

    static public boolean checkIfNull(int doctorid){

        String query = "SELECT patient_id FROM patientsinconsultations WHERE doctor_id = " + doctorid;

        try{
            Statement st = cnx.createStatement();

            ResultSet rs = st.executeQuery(query);

            if(rs.next()){
                return rs.getInt("patient_id") == 0 ;
            }

        } catch (SQLException E){
            E.printStackTrace();
        }
        return true;
    }


    
    static public void addCurrentPatient(int patient_id,int doctor_id) {
        if(checkIfNull(doctor_id)) {
            String query = "UPDATE patientsinconsultations"
                    +" SET patient_id = "+patient_id
                    +" WHERE doctor_id = "+doctor_id;
            try{
                Statement st = cnx.createStatement();
                st.executeUpdate(query);
            } catch (SQLException E){
                E.printStackTrace();
            }

        }
    }
    
    public void initialize(){
        //    clear();
      //      update();
        }


    static public int getCurrentPatientIdFromDB(int doctor_id){

        String query = "SELECT patient_id FROM patientsinconsultations WHERE doctor_id = " + doctor_id;

        try{
            Statement st = cnx.createStatement();

            ResultSet rs = st.executeQuery(query);

            if(rs.next()){

                 return rs.getInt("patient_id");
            }

        } catch (SQLException E){
            E.printStackTrace();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "WaitingRoom{" +
                "todayDate=" + todayDate +
                '}';
    }
}
