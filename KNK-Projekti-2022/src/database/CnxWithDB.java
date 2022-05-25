package database;


import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CnxWithDB {
    private static final String servername = "localhost";
    private static final Integer port = 3307;

    private static final String username = "root";
    private static final String password = "Shefket1";
    private static String dbname = "clinic_db";
    private static MysqlDataSource dataSource = new MysqlDataSource();


    public static Connection getConnection() {

        Connection cnx = null;

        dataSource.setServerName(servername);
        dataSource.setPortNumber(port);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setDatabaseName(dbname);

        try {
            cnx = dataSource.getConnection();

        } catch (SQLException ex) {
            Logger.getLogger("Connection error ===> " + CnxWithDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cnx;
    }


}
