/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aritz
 */
public class DAOImplementation {
    
    private String driver;
    private String url;
    private String user;
    private String password;
    
    private Connection con;
    private PreparedStatement stmt;
    
   
    public DAOImplementation() {
         this.driver= ResourceBundle.getBundle("ConfAchieve.properties")
                .getString("driver");
        this.url= ResourceBundle.getBundle("ConfAchieve.properties")
                .getString("url");
        this.user= ResourceBundle.getBundle("ConfAchieve.properties")
                .getString("user");
        this.password= ResourceBundle.getBundle("ConfAchieve.properties")
                .getString("password");
    }

    public void openConnection (){
        try {
            con= (Connection) DriverManager.getConnection
        (url, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(DAOImplementation.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeConnection() {
        if (stmt!= null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (con!= null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOImplementation.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
