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

        User usu = new User();
        usu.setUsername("Prueba2");
        usu.setPassword("777");
        DAOImplementation prueba = new DAOImplementation();
        boolean b=false;
        
        if (prueba.signIn(usu).getEmail().equals("prueba2.@gmail.com")) {
            b=true;
        }
        
        assertTrue("Si el email es igual entonces es que ha sacado bien "
                + "los datos",b);
    }

    /**
     * Test of SignUp method, of class DAOImplementation.
     */
    @Test
    public void testSignUp() throws Exception {

        //El test solo se puede probar una vez ya que no existe un metodo de
        //borrar datos en la base de datos
        usu = new User();
        usu.setUsername("Prueba5");
        usu.setPassword("777777");
        usu.setEmail("prueba5.@gmail.com");
        usu.setFullname("Prueba4");
        usu.setPrivileges(UserPrivilege.USER);
        usu.setStatus(UserStatus.ENABLED);
        DAOImplementation prueba = new DAOImplementation();
        prueba.signUp(usu);
        
        
        assertTrue("Si es true significa que ya hay un usuario igual, lo que "
                + "quiere decir que si ha funcionado el introducir un "
                + "nuevo usuario", prueba.comprobarUsuario(usu, con));
        
        
    }

    /**
     * Test of comprobarUsuario method, of class DAOImplementation.
     */
    @Test
    public void testComprobarUsuario() {
       
        User usu = new User();
        usu.setUsername("Prueb");
        DAOImplementation prueba = new DAOImplementation();
        
       
        assertFalse("Si es true significa que hay un usuario con los"
                + " datos indicados", prueba.comprobarUsuario(usu, con));
       
    }

    /**
     * Test of comprobarEmail method, of class DAOImplementation.
     */
    @Test
    public void testComprobarEmail() {
     User usu = new User();
        usu.setEmail("prueba2.@gmail.com");
        DAOImplementation prueba = new DAOImplementation();
        
       
        assertTrue("Si es true significa que hay un email identico"
                , prueba.comprobarEmail(usu, con));
    }

    /**
     * Test of LogOut method, of class DAOImplementation.
     */
    @Test
    public void testLogOut() {
        System.out.println("LogOut");
        DAOImplementation instance = null;
        instance.logOut();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    
     @Test
    public void testComprobarPassword() {
        User usu = new User();
        usu.setUsername("Prueba2");
        usu.setPassword("777");
        DAOImplementation prueba = new DAOImplementation();
        
        assertTrue("Si es true significa que hay un password identico",
                 prueba.comprobarPassword(usu, con));
    }
    
}
