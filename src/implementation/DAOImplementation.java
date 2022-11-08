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

import interfacePackage.mainInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;


import userPackage.*;

/**
 * With the implementation we will connect to the DB and make the necessary 
 * queries as needed.
 * @author Aritz
 */

public class DAOImplementation implements mainInterface{

    private Connection con;
    private PreparedStatement stmt;

    /**
     * Implantation builder
     * @param con The connection we will use for queries
     */
    public DAOImplementation(Connection con) {
         this.con=con;
    }
    
    private final String selectUserpasswd="SELECT * FROM user WHERE Login = ? "
            + "and Password=?";
    private final String selectUser="SELECT * FROM user WHERE Login = ?";
    private final String selectEmail="SELECT * FROM user WHERE Email = ?";
    private final String insertUser="INSERT INTO user(Login,Email,FullName,"
            + "UserStatus,Privilege,Password,LastPasswordChange) "
            + "VALUES(?,?,?,?,?,?,now())";
    private final String exitUser="INSERT INTO signin(LastSignIn,id) "
            + "VALUES(CURRENT_TIMESTAMP(), (SELECT id FROM user WHERE "
            + "Login = ?))";
    private final String checkPassword = "SELECT * FROM user WHERE Login = ? "
            + "and Password=?";
    private final String removeFirstSignIn = "DELETE FROM signin WHERE "
            + "LastSignIn = ? and id = ?";
    private final String selectFirstLastSignIn = "SELECT MIN(LastSignIn) FROM"
            + " signin where id = ?";
    private final String insertLastSignIn = "INSERT INTO signin "
            + "(LastSignIn, id) VALUES (now(), ?)";
      private final String selectPassword="SELECT * FROM user "
            + "WHERE password = ? ";
      private final String selectLastSignInCount= "SELECT count(*) FROM"
              + " signin where id= ?";
    
    
    @Override
    public synchronized User signIn(User usu) throws LoginUsernameException,
            LoginPasswordException,
            LoginUsernameAndPasswordException, ServerConnectionException {
        
        ResultSet rs = null;
         String enume;
         int id = 0;
       
        if (!comprobarUsuario(usu)) {
            throw new LoginUsernameException("");
        }
        if (!comprobarPassword(usu)) {
            throw new LoginPasswordException("");
        }
        try {
            stmt = con.prepareStatement(selectUserpasswd);
            stmt.setString(1, usu.getUsername());
            stmt.setString(2, usu.getPassword());
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (!rs.getString("Login").equalsIgnoreCase(usu.getUsername())
                        || !rs.getString("Password").
                                equalsIgnoreCase(usu.getPassword())) {
                    throw new LoginUsernameAndPasswordException("");
                } else {
                    usu.setUsername(rs.getString("Login"));
                    usu.setPassword(rs.getString("Password"));
                    usu.setEmail(rs.getString("Email"));
                    usu.setFullname(rs.getString("FullName"));
                    enume= rs.getString("UserStatus");
                    if (enume.equalsIgnoreCase(UserStatus.DISABLED.toString())) {
                        usu.setStatus(UserStatus.DISABLED);
                    }else{
                        usu.setStatus(UserStatus.ENABLED);
                    }
                     enume= rs.getString("Privilege");
                    if (enume.equalsIgnoreCase(UserPrivilege.ADMIN.toString())) {
                        usu.setPrivileges(UserPrivilege.ADMIN);
                    }else{
                        usu.setPrivileges(UserPrivilege.USER);
                    }
                    usu.setLastPasswordChange(rs.getTimestamp("LastPasswordChange"));
                    id= rs.getInt("id");
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(DAOImplementation.class.getName()).
                    log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAOImplementation.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            lastSignIn(id);
            
        }
        return usu;
    }
    
    public void lastSignIn(int id){
        ResultSet rs = null;
        try {
            
            int count= lastSignInCount(id);
            
            if (count==10) {
                removeLastSignIn(id, firstLastSignIn(id));
            }
            stmt = con.prepareStatement(insertLastSignIn);
            
            stmt.setInt(1, id);
            
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
    
  
    public Timestamp firstLastSignIn(int id){
         ResultSet rs = null;
        try {
           
            stmt = con.prepareStatement(selectFirstLastSignIn);
            
            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                return rs.getTimestamp(1);

            }
            return null;
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
        return null;
        
    }
    
    public int lastSignInCount(int id){
        ResultSet rs = null;
        try {
            
            stmt = con.prepareStatement(selectLastSignInCount);
            
            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            while (rs.next()) {

                return rs.getInt(1);

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
        return 0;
        
    }
    
    public void removeLastSignIn(int id, Timestamp tm){
       
        try {
            ResultSet rs = null;
            stmt = con.prepareStatement(removeFirstSignIn);
            
            stmt.setTimestamp(1, tm);
            stmt.setInt(2, id);

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

    @Override
    public synchronized void signUp(User usu) throws SignUpUsernameException, SignUpEmailException, SignUpEmailAndUsernameException, ServerConnectionException {

        if (comprobarUsuario(usu)) {
            throw new SignUpUsernameException("");
        }
        if (comprobarEmail(usu)) {
            throw new SignUpEmailException("");
        }
        try {
           stmt = con.prepareStatement(insertUser);
            
            stmt.setString(1, usu.getUsername());
            stmt.setString(2, usu.getEmail());
            stmt.setString(3, usu.getFullname());
            stmt.setString(4, usu.getStatus().name());
            stmt.setString(5, usu.getPrivileges().name());
            stmt.setString(6, usu.getPassword());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * We check that the name of the user you want to enter exist.
     *
     * @param usu User data
     * @return Returns true if it finds an identical username and false if it
     * does not.
     */
    public boolean comprobarUsuario(User usu) {
        ResultSet rs = null;
        Boolean salida = true;

        try {
            stmt = con.prepareStatement(selectUser);

            stmt.setString(1, usu.getUsername());

            rs = stmt.executeQuery();

            while (rs.next()) {

                return rs.getString("Login").equals(usu.getUsername());

            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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
              return rs.getString("Email").equals(usu.getEmail());

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
    
    /**
     * We check that the password of the user you want to enter exist
     * @param usu User data
     * @return Returns true if it finds an identical password and false
     * if it does not.
     */
       public boolean comprobarPassword(User usu){
         ResultSet rs= null;
        
        try {
          stmt =  con.prepareStatement(selectPassword);
            
            stmt.setString(1, usu.getPassword());
            
            rs = stmt.executeQuery();

            while (rs.next()) {
              return rs.getString("Password").equals(usu.getPassword());

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
    public synchronized void logOut() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
