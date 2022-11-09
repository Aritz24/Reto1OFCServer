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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Aritz
 */
public class ConnectionPoolTest {
    
   
     private static String driver = ResourceBundle.getBundle("properties.PropertiesFile")
            .getString("driver");
    
        private static String url = ResourceBundle.getBundle("properties.PropertiesFile")
            .getString("url");
    
        private static String user = ResourceBundle.getBundle("properties.PropertiesFile")
            .getString("username");
    
        private static String password = ResourceBundle.getBundle("properties.PropertiesFile")
            .getString("password");
    
       private static Connection con;
       
       
    public ConnectionPoolTest() {
        
      
    }


    /**
     * Test of getConnnection method, of class ConnectionPool.
     */
    @Test
    public void testGetConnnection() {
      
            Connection con = null;
        
                assertEquals("Si no son iguales entonces la conexion ha"
                        + " sido utilizada y sacada del pool",con
                       , ConnectionPool.getConnnection());

                ConnectionPool.closeConnection();       
        
    }

    /**
     * Test of newConnection method, of class ConnectionPool.
     */
    @Test
    public void testNewConnection() {
       
       Connection con;
       con= (Connection) ConnectionPool.newConnection();
       
        assertNotNull("Si no es nulo entonces hay una conexion", con);
    }

    /**
     * Test of closeConnection method, of class ConnectionPool.
     */
    @Test
    public void testCloseConnection() {
       
        Connection con = BDcon();
        Connection con2 = BDcon();
        
        ConnectionPool.devolConnection(con);
        ConnectionPool.devolConnection(con2);
      
        ConnectionPool.closeConnection();
      
       
        try {
            assertTrue("", con.isClosed());
             //assertTrue("Si es nulo entonces es que se ha cerrado", con2.isClosed());
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionPoolTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionPool.closeConnection();

    }
    
    public Connection BDcon(){
      
        try {
            con = (Connection) DriverManager.getConnection(url, user, password);
             return con;
        } catch (SQLException ex) {
            Logger.getLogger(DAOImplementation.class.getName())
                    .log(Level.SEVERE, null, ex);
        
        }
        return null;
        
    }
}
