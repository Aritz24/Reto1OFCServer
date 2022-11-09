/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pool;

import implementation.DAOImplementation;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * In this class we save and create connections to the DB and send them to the
 * users for their use.
 *
 * @author Aritz
 */
public class ConnectionPool {

    private static Stack pool = new Stack();

    private static String driver = ResourceBundle.getBundle
        ("properties.PropertiesFile")
            .getString("driver");
    
        private static String url = ResourceBundle.getBundle
        ("properties.PropertiesFile")
            .getString("url");
    
        private static String user = ResourceBundle.getBundle
        ("properties.PropertiesFile")
            .getString("username");
    
        private static String password = ResourceBundle.getBundle
        ("properties.PropertiesFile")
            .getString("password");
    
       private static Connection con;

    /**
     * Checks if the Stack has connections and if it does, one of them is sent,
     * if not, a new connection is created and sent.
     *
     * @return Retorna una conexion.
     */
    private ConnectionPool() {

    }

    public static synchronized Connection getConnnection() {

        if (pool.isEmpty()) {
            return (Connection) newConnection();
        } else {
            return (Connection) pool.pop();
        }
    }

    /**
     * Creates a new connection to the DB
     *
     * @return Returns a connection to the DB
     */
    public static Connection newConnection() {

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
     * In this method we save the connections to the DB that are not being used.
     *
     * @param con is the connection that we are going to return to the stack
     */
    public static void devolConnection(Connection con) {
        pool.push(con);
    }

    /**
     * Closes the connections to the DB
     */
    public static void closeConnection() {
        Connection con;

        while (!pool.isEmpty()) {
            con = (Connection) pool.pop();     
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOImplementation.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

    }
}
