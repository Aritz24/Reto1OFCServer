/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;


import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import userPackage.User;
import userPackage.UserPrivilege;
import userPackage.UserStatus;

/**
 *
 * @author Aritz
 */
public class DAOImplementationTest {
    
    private User usu;
    private com.mysql.jdbc.Connection con;
   
    public DAOImplementationTest() {
        
        usu = new User();
        usu.setLogin("Prueba2");
        usu.setPassword("777");
        usu.setEmail("prueba2.@gmail.com");
        usu.setFullname("Prueba2");
        usu.setPrivileges(UserPrivilege.USER);
        usu.setStatus(UserStatus.ENABLED);
        
        String driver;
        String url;
        String user;
        String password;
        
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
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOImplementation.class.getName())
                    .log(Level.SEVERE, null, ex);
        
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
     * Test of SignIn method, of class DAOImplementation.
     */
    @Test
    public void testSignIn() throws Exception {
        System.out.println("SignIn");
        User usu = null;
        DAOImplementation instance = null;
        User expResult = null;
        User result = instance.SignIn(usu);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of SignUp method, of class DAOImplementation.
     */
    @Test
    public void testSignUp() throws Exception {
       
        //El test solo se puede probar una vez ya que no existe un metodo de
        //borrar datos en la base de datos
        DAOImplementation prueba = new DAOImplementation(con);
        prueba.SignUp(usu);
        
        
        assertTrue("Si es true significa que ya hay un usuario igual, lo que "
                + "quiere decir que si ha funcionado el introducir un "
                + "nuevo usuario", prueba.comprobarUsuario(usu));
        
        
    }

    /**
     * Test of comprobarUsuario method, of class DAOImplementation.
     */
    @Test
    public void testComprobarUsuario() {
       
        User usu = new User();
        usu.setLogin("Pepe");
        DAOImplementation prueba = new DAOImplementation(con);
        
       
        assertTrue("Si es true significa que hay un usuario con los"
                + " datos indicados", prueba.comprobarUsuario(usu));
       
    }

    /**
     * Test of comprobarEmail method, of class DAOImplementation.
     */
    @Test
    public void testComprobarEmail() {
     User usu = new User();
        usu.setEmail("a.a@.com");
        DAOImplementation prueba = new DAOImplementation(con);
        
       
        assertTrue("Si es true significa que hay un email identico"
                , prueba.comprobarEmail(usu));
    }

    /**
     * Test of LogOut method, of class DAOImplementation.
     */
    @Test
    public void testLogOut() {
        System.out.println("LogOut");
        DAOImplementation instance = null;
        instance.LogOut();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
