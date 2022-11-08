/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import enumPackcage.ExceptionType;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import messagePackage.Message;
import pool.ConnectionPool;
import com.mysql.jdbc.Connection;

/**
 * In this class we maintain the connection to the client side and when that
 * connection is lost we will save it in the connection stack instead of closing
 * it.
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
    private Connection con;
    private final Logger LOGGER = Logger.getLogger("thread/Threads");
    private static Integer hilos;

    /**
     * Thread builder
     *
     * @param clientSocket Client conexion
     * @param hilos
     */
    public Threads(Socket clientSocket, Integer hilos) {
        this.s = clientSocket;
        Threads.hilos = hilos;
       
    }

    /**
     *
     */
    @Override
    public void run() {

        p = new ConnectionPool();

         /*try {
                 sleep(50000);
             } catch (InterruptedException ex) {
                 Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
             }*/
        try {

            menenv = new Message();

            recib = new ObjectInputStream(s.getInputStream());
            try {
                men = (Message) recib.readObject();
            } catch (ClassNotFoundException ex) {

                Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
            }
            con = (Connection) p.getConnnection();
            /*try {
                 sleep(10000);
             } catch (InterruptedException ex) {
                 Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
             }*/

            f = new DAOFactory();
            dao = f.makeDao(con);

            if (men.getUser() != null) {
                env = new ObjectOutputStream(s.getOutputStream());
                if (men.getAcType().toString().equalsIgnoreCase("SIGNIN")) {
                    try {
                        menenv.setUser(dao.signIn(men.getUser()));

                    } catch (LoginUsernameException ex) {
                        Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
                        menenv.setExType(ExceptionType.LOGINUSERNAMEEXCEPTION);
                    } catch (LoginPasswordException ex) {
                        Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
                        menenv.setExType(ExceptionType.LOGINPASSWORDEXCEPTION);
                    } catch (LoginUsernameAndPasswordException ex) {
                        Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
                        menenv.setExType(ExceptionType.SIGNUPEMAILANDUSERNAMEEXCEPTION);
                    } catch (ServerConnectionException ex) {
                        Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else if (men.getAcType().toString().equalsIgnoreCase("SIGNUP")) {
                    try {
                        dao.signUp(men.getUser());

                    } catch (SignUpUsernameException ex) {
                        Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
                        menenv.setExType(ExceptionType.SIGNUPUSERNAMEEXCEPTION);

                    } catch (SignUpEmailException ex) {
                        Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
                        menenv.setExType(ExceptionType.SIGNUPEMAILEXCEPTION);
                    } catch (SignUpEmailAndUsernameException ex) {
                        Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
                        menenv.setExType(ExceptionType.SIGNUPEMAILANDUSERNAMEEXCEPTION);
                    } catch (ServerConnectionException ex) {
                        Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                p.devolConnection(con);
                env.writeObject(menenv);
                
                  try {
                 sleep(10000);
             } catch (InterruptedException ex) {
                 Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
             }
                System.out.println(hilos);
                hilos--;
                System.out.println(hilos);
                
              
               
                this.interrupt();
            }
            

        } catch (IOException ex) {

            Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);

            hilos--;
            this.interrupt();
        }
    }

}
