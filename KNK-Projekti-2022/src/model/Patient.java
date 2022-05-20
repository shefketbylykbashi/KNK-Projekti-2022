package model;

import java.sql.Connection;
import java.sql.Date;

import database.CnxWithDB;

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




    // this three arguments are mandatory for every patient the rest is optional
    public Patient(String first_name, String second_name, Date dateOfbirth) {
        this(first_name, second_name, dateOfbirth, "m", "00,Rue,Commune,Wilaya", "0000000000");

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
}
