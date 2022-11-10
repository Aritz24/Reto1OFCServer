/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import com.mysql.jdbc.Connection;
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

import serverSocket.ThreadCounter;

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
    private static ThreadCounter hilos;

    /**
     * Thread builder
     *
     * @param clientSocket Client conexion
     * @param hilos
     */
    public Threads(Socket clientSocket, ThreadCounter hilos) {
        this.s = clientSocket;
        this.hilos = hilos;

    }

    /**
     * We collect the customer's message and according to the action type will
     * make the signIn or signup. If it does signin we will save the user data 
     * returned from the database and if there is any error the exception will
     * be sent. In signUp it will return null if everything has gone well,
     * otherwise it will send the exception.
     */
    @Override
    public void run() {

        try {

            menenv = new Message();
            LOGGER.info("RECIBIENDO MENSAJE DEL USUARIO");
            recib = new ObjectInputStream(s.getInputStream());
            try {
                men = (Message) recib.readObject();
                LOGGER.info("MENSAJE DEL USUARIO RECIBIDO");
            } catch (ClassNotFoundException ex) {
                LOGGER.info("RECEPCION DEL MENSAJE FALLIDO");

                Logger.getLogger(Threads.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
          
            f = new DAOFactory();
            LOGGER.info("CREANDO INTERFAZ");
            dao = f.makeDao();
            LOGGER.info("INTERFAZ CREADA");
            if (men.getUser() != null) {
                env = new ObjectOutputStream(s.getOutputStream());
                if (men.getAcType().toString().equalsIgnoreCase("SIGNIN")) {
                    try {
                        LOGGER.info("ANIADIENDO USUARIO A LA BD Y "
                                + "RECOGIENDO SUS DATOS");
                        menenv.setUser(dao.signIn(men.getUser()));
                        LOGGER.info("USUARIO ANIADIDO Y DATOS RECOGIDOS");

                    } catch (LoginUsernameException ex) {
                        
                        LOGGER.info(ex.getMessage());
                        menenv.setExType(ExceptionType.LOGINUSERNAMEEXCEPTION);
                    } catch (LoginPasswordException ex) {
                   
                        LOGGER.info(ex.getMessage());
                        menenv.setExType(ExceptionType.LOGINPASSWORDEXCEPTION);
                    } catch (LoginUsernameAndPasswordException ex) {
                       
                        LOGGER.info(ex.getMessage());
                        menenv.setExType
                        (ExceptionType.SIGNUPEMAILANDUSERNAMEEXCEPTION);
                    } catch (ServerConnectionException ex) {
                        Logger.getLogger(Threads.class.getName()).
                                log(Level.SEVERE, null, ex);
                    }

                } else if (men.getAcType().toString().
                        equalsIgnoreCase("SIGNUP")) {
                    try {
                        LOGGER.info("INTRODUCIENDO UN NUEVO USUARIO EN LA BD");
                        dao.signUp(men.getUser());
                        LOGGER.info("USUARIO NUEVO INTRODUCIDO");

                    } catch (SignUpUsernameException ex) {
                        LOGGER.info(ex.getMessage());
                        menenv.setExType(ExceptionType.SIGNUPUSERNAMEEXCEPTION);

                    } catch (SignUpEmailException ex) {
                      
                        LOGGER.info(ex.getMessage());
                        menenv.setExType(ExceptionType.SIGNUPEMAILEXCEPTION);
                    } catch (SignUpEmailAndUsernameException ex) {
                        Logger.getLogger(Threads.class.getName()).
                                log(Level.SEVERE, null, ex);
                        LOGGER.info(ex.getMessage());
                        menenv.setExType
                        (ExceptionType.SIGNUPEMAILANDUSERNAMEEXCEPTION);
                    } catch (ServerConnectionException ex) {
                        Logger.getLogger(Threads.class.getName()).
                                log(Level.SEVERE, null, ex);
                    }
                }
                LOGGER.info("ENVIANDO MENSAJE AL USUARIO");
                env.writeObject(menenv);
                LOGGER.info("MENSAJE ENVIADO AL USUARIO");

                
                /*try {
                    sleep(10000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Threads.class.getName()).
                log(Level.SEVERE, null, ex);
                }*/

                LOGGER.info("CERRANDO HILO Y CONEXION");
                hilos.setCount(hilos.getCount() - 1);
                   s.close();
                this.interrupt();
                LOGGER.info("HILO Y CONEXION CERRADOS");
                
            }

        } catch (IOException ex) {

            try {
                Logger.getLogger(Threads.class.getName()).
                        log(Level.SEVERE, null, ex);
                
                s.close();
                hilos.setCount(hilos.getCount() - 1);
                this.interrupt();
            } catch (IOException ex1) {
                Logger.getLogger(Threads.class.getName()).
                        log(Level.SEVERE, null, ex1);
            }
        }
    }
}
