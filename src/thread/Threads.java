/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import com.mysql.jdbc.Connection;
import exceptions.LoginPasswordException;
import exceptions.LoginUsernameAndPasswordException;
import exceptions.LoginUsernameException;
import exceptions.ServerConnectionException;
import exceptions.SignUpEmailAndUsernameException;
import exceptions.SignUpEmailException;
import exceptions.SignUpUsernameException;
import factory.DAOFactory;
import implementation.DAOImplementation;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import messagePackage.Message;
import pool.ConnectionPool;

/**
 *
 * @author Aritz
 */
public class Threads extends Thread {

    private Socket s;
    private ObjectInputStream recib;
    private ObjectOutputStream env;
    private Message men;
    private DAOFactory f;
    private DAOImplementation dao;
    private Message menenv;
    private ConnectionPool p;
    private Stack pool;
    private Connection con;

    public Threads(Socket clientSocket) {
        this.s = clientSocket;
    }

    @Override
    public void run() {
        
        pool= new Stack();

        while (!this.isInterrupted()) {

            try {
                menenv = new Message();
                env = new ObjectOutputStream(s.getOutputStream());
                recib = new ObjectInputStream(s.getInputStream());
                try {
                    men = (Message) recib.readObject();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
                }
                //ES UNA PRUEBA, TRANQUILO JAVI
                System.out.println(men.getAcType());
                con = p.getConnnection(pool);
                f= new DAOFactory();
                dao= f.makeDao(con);
                
                if (men.getAcType().toString().equalsIgnoreCase("SIGNIN")){
                    try {
                        dao.SignIn(men.getUsu());
                    } catch (LoginUsernameException ex) {
                        Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (LoginPasswordException ex) {
                        Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (LoginUsernameAndPasswordException ex) {
                        Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServerConnectionException ex) {
                        Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                } else if (men.getAcType().toString().equalsIgnoreCase("SIGNUP")) {
                    try {
                        dao.SignUp(men.getUsu());
                        
                    } catch (SignUpUsernameException ex) {
                        Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SignUpEmailException ex) {
                        Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SignUpEmailAndUsernameException ex) {
                        Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServerConnectionException ex) {
                        Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                env.writeObject(menenv);

            } catch (IOException ex) {
                Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
