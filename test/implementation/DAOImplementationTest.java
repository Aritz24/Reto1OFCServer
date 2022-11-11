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
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import userPackage.User;
import userPackage.UserPrivilege;
import userPackage.UserStatus;

/**
 * DAOImplementation test
 * @author Aritz
 */
public class DAOImplementationTest {
    
    private User usu;
    private com.mysql.jdbc.Connection con;
    private static String driver = 
             ResourceBundle.getBundle("properties.PropertiesFile")
            .getString("driver");
    
        private static String url = 
                ResourceBundle.getBundle("properties.PropertiesFile")
            .getString("url");
    
        private static String user = 
                ResourceBundle.getBundle("properties.PropertiesFile")
            .getString("username");
    
        private static String password = 
                ResourceBundle.getBundle("properties.PropertiesFile")
            .getString("password");
    
    /**
     *testserver1_askldsaldk
     */
    public DAOImplementationTest() {
       
        try {
            con = (Connection) DriverManager.getConnection(url, user, password);
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOImplementation.class.getName())
                    .log(Level.SEVERE, null, ex);
        
        }
       
    }
    
      /**
     * Test of SignUp method, of class DAOImplementation.
     * @throws java.lang.Exception
     */
    @Test
    public void testSignUp() throws Exception {

        //El test solo se puede probar una vez ya que no existe un metodo de
        //borrar datos en la base de datos. Por lo que para probarlo
        //hay que cambiar manualmente los datos a introducir
        usu = new User();
        usu.setUsername("testman2");
        usu.setPassword("testman2");
        usu.setEmail("testman2.@gmail.com");
        usu.setFullname("testman2");
        usu.setPrivileges(UserPrivilege.USER);
        usu.setStatus(UserStatus.ENABLED);
        DAOImplementation prueba = new DAOImplementation();
        prueba.signUp(usu);
        
        
        assertTrue("Si es true significa que ya hay un usuario igual, lo que "
                + "quiere decir que si ha funcionado el introducir un "
                + "nuevo usuario", prueba.comprobarUsuario(usu, con));
        
        
    }
    
    /**
     * Test of SignIn method, of class DAOImplementation.
     * @throws java.lang.Exception
     */
     @Test
    public void testSignIn() throws Exception {

        User usu = new User();
        usu.setUsername("testman");
        usu.setPassword("testman");
        DAOImplementation prueba = new DAOImplementation();
        boolean b=false;
        
        if (prueba.signIn(usu).getEmail().equals("testman.@gmail.com")) {
            b=true;
        }
        
        assertEquals("Si el email es igual entonces es que ha sacado bien "
                + "los datos",
                prueba.signIn(usu).getEmail(),"testman.@gmail.com");
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
        usu.setEmail("testman.@gmail.com");
        DAOImplementation prueba = new DAOImplementation();
        
       
        assertTrue("Si es true significa que hay un email identico"
                , prueba.comprobarEmail(usu, con));
    }

    /**
     * Test of LogOut method, of class DAOImplementation.
     */
    @Test
    @Ignore
    public void testLogOut() {
        System.out.println("LogOut");
        DAOImplementation instance = null;
        instance.logOut();
        // TODO review the generated test code and remove the default call to
        //fail.
        fail("The test case is a prototype.");
    }
    
    /**
     *
     */
    @Test
    public void testComprobarPassword() {
        User usu = new User();
        usu.setUsername("testman");
        usu.setPassword("testman");
        DAOImplementation prueba = new DAOImplementation();
        
        assertTrue("Si es true significa que hay un password identico",
                 prueba.comprobarPassword(usu, con));
    }
    
}
