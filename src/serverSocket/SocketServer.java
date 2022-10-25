/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import thread.Threads;

/**
 *
 * @author 2dam
 */
public class SocketServer {

    private ServerSocket ss;
    private Socket clientSocket;
    private Threads th;

    public static void main(String[] args) {
        // TODO code application logic here
    }

    public SocketServer() {

        try {
            ss = new ServerSocket();
            clientSocket = ss.accept();

            th = new Threads(clientSocket);
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
