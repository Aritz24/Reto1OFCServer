/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pool;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import implementation.DAOImplementation;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aritz
 */
public class ConectionPool {
    
    private static Stack pool;
   
   

    public ConectionPool() {

    }

    public void getConnnection(){
        if (pool.isEmpty()) {
            
        }
    }
    public void newConnection() {
        String driver;
        String url;
        String user;
        String password;
        Connection con;
        driver = ResourceBundle.getBundle("ConfAchieve.properties")
                .getString("driver");
        url = ResourceBundle.getBundle("ConfAchieve.properties")
                .getString("url");
        user = ResourceBundle.getBundle("ConfAchieve.properties")
                .getString("user");
        password = ResourceBundle.getBundle("ConfAchieve.properties")
                .getString("password");
        try {
            con = (Connection) DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(DAOImplementation.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    public void closeConnection(Connection con, PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConectionPool.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOImplementation.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

}
