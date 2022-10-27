/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;

import com.mysql.jdbc.Connection;
import interfacePackage.DaoInteface;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import userPackage.User;

/**
 *
 * @author Aritz
 */
public class DAOImplementation implements DaoInteface{
    
    private String driver;
    private String url;
    private String user;
    private String password;
    
    private Connection con;
    private PreparedStatement stmt;
    
    private final String selectUser="SELECT * FROM user WHERE Login = ? and Password=?";
    private final String insertUser="INSERT INTO user(Login,Email,FullName,UserStatus,Privilege,Password) VALUES(?,?,?,?,?,?)";
    private final String exitUser="INSERT INTO signin(LastSignIn,id) VALUES(CURRENT_TIMESTAMP(), (SELECT id FROM user WHERE Login = ?))";
    private final String checkPassword = "SELECT * FROM user WHERE Login = ? and Password=?";
    private final String removeFirstSignIn = "DELETE FROM signin WHERE LastSignIn = ? and id = ?";
    private final String selectLastSignIn = "SELECT MIN(LastSignIn) FROM signin where id = ?";
    private final String updateLastSignIn = "UPDATE signin SET LastSignIn = CURRENT_TIMESTAMP() WHERE id IN(SELECT id FROM user)";
    
    
    public DAOImplementation() {
         this.driver= ResourceBundle.getBundle("ConfAchieve.properties")
                .getString("driver");
        this.url= ResourceBundle.getBundle("ConfAchieve.properties")
                .getString("url");
        this.user= ResourceBundle.getBundle("ConfAchieve.properties")
                .getString("user");
        this.password= ResourceBundle.getBundle("ConfAchieve.properties")
                .getString("password");
    }

    public void openConnection (){
        try {
            con= (Connection) DriverManager.getConnection
        (url, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(DAOImplementation.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeConnection() {
        if (stmt!= null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (con!= null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOImplementation.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public User SignIn(User usu) {
        ResultSet rs= null;
        this.openConnection();
        try {
            stmt = con.prepareStatement(selectUser);
            
        } catch (SQLException ex) {
            Logger.getLogger(DAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usu;
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
