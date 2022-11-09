/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import thread.ExitThread;
import thread.Threads;

/**
 *This is the class in which we create the server connections and generate the 
 * threads to serve the clients.
 * @author Aritz
 */
public class Server {

    private ServerSocket ss;
    private Socket clientSocket;
    private Threads th;
    private static final ThreadCounter hilos= new ThreadCounter();
    private static final Logger LOGGER= Logger.getLogger("serverSocket/Server");
    private static final Integer PUERTO =  Integer.valueOf(ResourceBundle.
            getBundle("properties.PropertiesFile").getString("port"));
    

    public static void main(String[] args) {
        Server server = new Server();
       
    }

    /**
     * Server builder
     */
    public Server() {

        Scanner sc = new Scanner(System.in);
        hilos.setCount(0);
        ExitThread ext= new ExitThread();
        ext.start();
        while (true) {

            if (hilos.getCount() != 10) {
                try {
                    ss = new ServerSocket(PUERTO);
                    clientSocket = ss.accept();

                    if (hilos.getCount() < 10) {
                        hilos.setCount(hilos.getCount() + 1);
                        th = new Threads(clientSocket, hilos);
                        th.start();
                    }

                } catch (IOException ex) {
                    LOGGER.severe(ex.getMessage());
                    Logger.getLogger(ServerSocket.class.getName()).log(Level.
                            SEVERE, null, ex);

                } finally {
                    try {
                        ss.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ServerSocket.class.getName()).log(Level
                                .SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
