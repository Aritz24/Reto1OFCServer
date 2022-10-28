/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;

import com.mysql.jdbc.Connection;
import exceptions.LoginPasswordException;
import exceptions.LoginUsernameAndPasswordException;
import exceptions.LoginUsernameException;
import exceptions.ServerConnectionException;
import exceptions.SignUpEmailAndUsernameException;
import exceptions.SignUpEmailException;
import exceptions.SignUpUsernameException;
import java.sql.PreparedStatement;
import interfacePackage.DaoInterface;
import userPackage.User;

/**
 *
 * @author Aritz
 */
public class DAOImplementation implements DaoInterface{
    
    private Connection con;
    private PreparedStatement stmt;
    
   
  

    public DAOImplementation(Connection con) {
         this.con=con;
    }

    @Override
    public User SignIn(User usu) throws LoginUsernameException, LoginPasswordException, LoginUsernameAndPasswordException, ServerConnectionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SignUp(User usu) throws SignUpUsernameException, SignUpEmailException, SignUpEmailAndUsernameException, ServerConnectionException {
        
        stmt = (com.mysql.jdbc.PreparedStatement) con.prepareStatement();
    }

    @Override
    public void LogOut() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    

}
