/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pool;

import com.mysql.jdbc.Connection;
import implementation.DAOImplementation;
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
    
    private  Stack pool ;
    public ConnectionPoolTest() {
        
        Connection connnection;
        this.pool= new Stack();
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
                        pool.get(i), prueba.getConnnection(pool));
        }
    }

    /**
     * Test of newConnection method, of class ConnectionPool.
     */
    @Test
    public void testNewConnection() {
       ConnectionPool prueba = new ConnectionPool();
       Connection con;
       con= prueba.newConnection();
       
        assertNotNull("Si no es nulo entonces hay una conexion", con);
    }

    /**
     * Test of closeConnection method, of class ConnectionPool.
     */
    @Test
    public void testCloseConnection() {
       
        Connection con = BDcon();
        ConnectionPool prueba = new ConnectionPool();
        
        prueba.closeConnection(con);
        try {
            assertTrue("Si es nulo entonces es que se ha cerrado", con.isClosed());
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionPoolTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public Connection BDcon(){
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
}