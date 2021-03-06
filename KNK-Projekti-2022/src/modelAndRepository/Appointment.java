package modelAndRepository;

import database.CnxWithDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Appointment {


    static public Connection cnx = CnxWithDB.getConnection();
    private int rdv_id;
    private int patient_id;
    private int doctor_id;
    private Date  rdv_date;


    
    public Appointment (Date rdv_date , int patient_id , int doctor_id){
        this.rdv_date = rdv_date;
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;

        this.rdv_id = getAppointmentId();
    }


    
    public void add(){


        if(rdv_id == 0 ){

            try {

                Statement stmt = cnx.createStatement();

                String sql = "INSERT INTO appointments"
                        + "(patient_id,doctor_id,rdv_date) "
                        + "VALUES ('" + this.patient_id + "','" + this.doctor_id + "','" + this.rdv_date + "')";
                stmt.executeUpdate(sql);
                this.rdv_id = getAppointmentId();

                System.out.println("Element added to the database .");
                System.out.println("ID After being added to the db " + this.rdv_id);
                WaitingRoom wr = new WaitingRoom();
                 wr.add(this.patient_id,this.doctor_id);


                stmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }






        }




    }


    
    private int getAppointmentId() {
        String query = "SELECT rdv_id FROM appointments WHERE rdv_date = ? AND patient_id = ? AND doctor_id = ? ";

        try ( PreparedStatement st = cnx.prepareStatement(query)) {

            st.setDate(1, this.rdv_date);
            st.setInt(2, this.patient_id);
            st.setInt(3, this.doctor_id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                return rs.getInt("rdv_id");

            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;

    }

    
    static public void delete(int id){



            try {

                Statement stmt = cnx.createStatement();

                String sql = "DELETE FROM appointments WHERE rdv_id =  "+ id;

                stmt.executeUpdate(sql);

                System.out.println("Element deleted .");

                stmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    static public ObservableList<AppointmentSearchResult> search(Date date , String criteria , int id ){

        String query ="SELECT appointments.rdv_id, patients.patient_id , patients.firstName , patients.secondName ,appointments.doctor_id, patients.adr, patients.num FROM patients " +
                "INNER JOIN appointments ON patients.patient_id = appointments.patient_id" +
                " AND (rdv_date = '" + date + "') AND (patients.firstName  LIKE '%" + criteria +  "%' OR patients.secondName LIKE '%" + criteria +"%') AND (doctor_id =" + id+") ORDER BY appointments.rdv_id";


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

    
    static public ObservableList<AppointmentSearchResult> search(Date date , String criteria ){

        String query = "SELECT appointments.rdv_id, patients.patient_id , patients.firstName , patients.secondName ,appointments.doctor_id, patients.adr, patients.num FROM patients " +
                "INNER JOIN appointments ON patients.patient_id = appointments.patient_id" +
                " AND (rdv_date = '" + date + "') AND (patients.firstName  LIKE '%" + criteria +  "%' OR patients.secondName LIKE '%" + criteria +"%')  ORDER BY appointments.rdv_id";

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
                AppointmentSearchResult patient = new AppointmentSearchResult(rdv_id , firstName,secondName,doctor_name,adr,num);
                patients.add(patient);

            }
            rs.close();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;





    }





}
