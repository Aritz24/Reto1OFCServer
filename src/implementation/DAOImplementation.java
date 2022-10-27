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
import interfacePackage.DaoInterface;
import userPackage.User;

/**
 *
 * @author Aritz
 */
public class DAOImplementation implements DaoInterface{
    
    private String driver;
    private String url;
    private String user;
    private String password;
    
    private Connection con;
    private PreparedStatement stmt;
    
   
  

    public DAOImplementation(Connection con) {
         this.con=con;
    }

    @Override
    public User SignIn(User usu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SignUp(User usu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void LogOut() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    

}
