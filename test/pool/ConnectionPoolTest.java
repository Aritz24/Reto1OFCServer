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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
        
        Connection connnection;
        
        for (int i = 0; i < 10; i++) {
            connnection = BDcon();
            pool.push(connnection);
        }
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getConnnection method, of class ConnectionPool.
     */
    @Test
    public void testGetConnnection() {
       ConnectionPool prueba = new ConnectionPool();
  
        for (int i = 0; i <pool.size(); i++) {
                assertNotEquals("Si no son iguales entonces la conexion ha"
                        + " sido utilizada y sacada del pool",
                        pool.get(i), prueba.getConnnection());
        }
    }

    /**
     * Test of newConnection method, of class ConnectionPool.
     */
    @Test
    public void testNewConnection() {
       ConnectionPool prueba = new ConnectionPool();
       Connection con;
       con= (Connection) prueba.newConnection();
       
        assertNotNull("Si no es nulo entonces hay una conexion", con);
    }

    /**
     * Test of closeConnection method, of class ConnectionPool.
     */
    @Test
    public void testCloseConnection() {
       
        Connection con = BDcon();
        Connection con2 = BDcon();
        ConnectionPool prueba = new ConnectionPool();
        prueba.devolConnection(con);
        prueba.devolConnection(con2);
      
        prueba.closeConnection();
      
       
        try {
            assertTrue("", con.isClosed());
             //assertTrue("Si es nulo entonces es que se ha cerrado", con2.isClosed());
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionPoolTest.class.getName()).log(Level.SEVERE, null, ex);
        }

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
