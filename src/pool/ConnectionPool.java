/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pool;


import implementation.DAOImplementation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * In this class we save and create connections to the DB and send them to
 * the users for their use.
 * @author Aritz
 */
public class ConnectionPool {
    
   
/**
 * Checks if the Stack has connections and if it does, one of them is sent, if 
 * not, a new connection is created and sent.
 * @param pool It is the Stack in which the connections can be stored.
 * @return Retorna una conexion.
 */
    public Connection getConnnection(Stack pool){
       
        if (pool.isEmpty()) {
            return (Connection) newConnection();
        }else{
            return (Connection) pool.pop();
        }
    }
    
    /**
     * Creates a new connection to the DB
     * @return Returns a connection to the DB
     */
    public Connection newConnection() {
        String driver;
        String url;
        String user;
        String password;
        Connection con;
        driver = ResourceBundle.getBundle("properties.PropertiesFile")
                .getString("driver");
        url = ResourceBundle.getBundle("properties.PropertiesFile")
                .getString("url");
        user = ResourceBundle.getBundle("properties.PropertiesFile")
                .getString("username");
        password = ResourceBundle.getBundle("properties.PropertiesFile")
                .getString("password");
        try {
            con = (Connection) DriverManager.getConnection(url, user, password);
             return con;
        } catch (SQLException ex) {
            Logger.getLogger(DAOImplementation.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return null;
       
    }

    /**
     * Closes the connection to the DB
     * @param con The connection that needs to be closed
     */
    public void closeConnection(Connection con) {
  
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
