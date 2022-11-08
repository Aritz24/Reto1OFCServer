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
import thread.Threads;

/**
 *
 * @author 2dam
 */
public class Server {

    private ServerSocket ss;
    private Socket clientSocket;
    private Threads th;
    private static Integer hilos= 0;
    private static final Logger LOGGER= Logger.getLogger("serverSocket/Server");
    

    public static void main(String[] args) {
        Server server = new Server();
       
    }

    public Server() {
     
        Scanner sc= new Scanner(System.in);
        
        
        while (true) {
            System.out.println("Server0: "+hilos);
            if (hilos!=10) {
                 try {
                ss = new ServerSocket(Integer.valueOf(ResourceBundle.
                        getBundle("properties.PropertiesFile")
                        .getString("port")));
                clientSocket = ss.accept();
                System.out.println("Server: "+hilos);
                
                    
                    if (hilos < 10) {
                        hilos++;
                        th = new Threads(clientSocket, hilos);
                       
                        
                      System.out.println("Server suma: "+hilos);
                        th.start();
                        
                    }

                } catch (IOException ex) {
                    LOGGER.severe(ex.getMessage());
                    Logger.getLogger(ServerSocket.class.getName()).log(Level.SEVERE, null, ex);

                } finally {
                    try {
                        ss.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ServerSocket.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
