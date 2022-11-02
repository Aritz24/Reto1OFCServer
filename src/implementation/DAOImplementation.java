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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


import userPackage.User;

/**
 * With the implementation we will connect to the DB and make the necessary 
 * queries as needed.
 * @author Aritz
 */

public class DAOImplementation implements DaoInterface{

    private Connection con;
    private PreparedStatement stmt;

    /**
     * Implantation builder
     * @param con The connection we will use for queries
     */
    public DAOImplementation(Connection con) {
         this.con=con;
    }
    private final String selectUserpasswd="SELECT * FROM user WHERE Login = ? and Password=?";
    private final String selectUser="SELECT * FROM user WHERE Login = ?";
    private final String selectEmail="SELECT * FROM user WHERE Email = ?";
    private final String insertUser="INSERT INTO user(Login,Email,FullName,UserStatus,Privilege,Password,LastPasswordChange) VALUES(?,?,?,?,?,?,now())";
    private final String exitUser="INSERT INTO signin(LastSignIn,id) VALUES(CURRENT_TIMESTAMP(), (SELECT id FROM user WHERE Login = ?))";
    private final String checkPassword = "SELECT * FROM user WHERE Login = ? and Password=?";
    private final String removeFirstSignIn = "DELETE FROM signin WHERE LastSignIn = ? and id = ?";
    private final String selectLastSignIn = "SELECT MIN(LastSignIn) FROM signin where id = ?";
    private final String updateLastSignIn = "UPDATE signin SET LastSignIn = CURRENT_TIMESTAMP() WHERE id IN(SELECT id FROM user)";
    
    
    @Override
    public User SignIn(User usu) throws LoginUsernameException, LoginPasswordException, LoginUsernameAndPasswordException, ServerConnectionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    @Override
    public void SignUp(User usu) throws SignUpUsernameException, SignUpEmailException, SignUpEmailAndUsernameException, ServerConnectionException {
      
        if (comprobarUsuario(usu)) {
            throw new SignUpUsernameException("");
        }
        if (comprobarEmail(usu)) {
            throw new SignUpEmailException("");
        }
        try {
           stmt = con.prepareStatement(insertUser);
            
            stmt.setString(1, usu.getLogin());
            stmt.setString(2, usu.getEmail());
            stmt.setString(3, usu.getFullname());
            stmt.setString(4, usu.getStatus().name());
            stmt.setString(5, usu.getPrivileges().name());
            stmt.setString(6, usu.getPassword());
            
            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * We check that the name of the user you want to enter is not repeated.
     * @param usu User data
     * @return Returns true if it finds an identical username and false
     * if it does not.
     */
    public boolean comprobarUsuario(User usu){
         ResultSet rs= null;
         Boolean salida= true;
        
        try {
          stmt =  con.prepareStatement(selectUser);
            
            stmt.setString(1, usu.getLogin());
            
            rs = stmt.executeQuery();

            while (rs.next() || salida) {
                
                if (rs.getString("Login").equalsIgnoreCase(usu.getLogin())) {
                    salida=false;
                }
              //NO SABEMOS SI CIERRA PRIMERO O HACE EL RETURN SIN CERRAR NADA
             // return rs.getString("Login").equalsIgnoreCase(usu.getLogin());
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
             try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
        return !salida;
        
    }
    
    /**
     * We check that the email of the user you want to enter is not repeated.
     * @param usu User data
     * @return Returns true if it finds an identical email and false
     * if it does not.
     */
    public boolean comprobarEmail(User usu){
         ResultSet rs= null;
        
        try {
          stmt =  con.prepareStatement(selectEmail);
            
            stmt.setString(1, usu.getEmail());
            
            rs = stmt.executeQuery();

            while (rs.next()) {
                 if (rs.getString("Email").equalsIgnoreCase(usu.getEmail())) {
                   return true;
                }else{
                     return false;
                 }

            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
             try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
        return false;
        

        
    }

    @Override
    public void LogOut() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
