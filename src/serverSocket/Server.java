/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
    private int contador=0;
    private static ArrayList<Threads> hilos= new ArrayList<>();;

    public static void main(String[] args) {
        Server server= new Server();
    }

    public Server() {
        
        try {
            ss = new ServerSocket(Integer.valueOf(ResourceBundle.
                    getBundle("properties.PropertiesFile")
                .getString("port")));
            clientSocket = ss.accept();

            
            for (int i = 0; i < hilos.size(); i++) {
                if (!hilos.get(i).isAlive()) {
                    hilos.remove(i);
                }
            }
            if (hilos.size()<10) {
                th = new Threads(clientSocket);
                hilos.add(th);
                th.start();
            }
            
            
        } catch (IOException ex) {
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
