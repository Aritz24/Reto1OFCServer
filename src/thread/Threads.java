/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public Threads(Socket clientSocket) {
        this.s = clientSocket;
    }

    @Override
    public synchronized void run() {

        while (!this.isInterrupted()) {

            try {
                menenv= new Message();
                env = new ObjectOutputStream(s.getOutputStream());
                recib = new ObjectInputStream(s.getInputStream());
                men = recib.readUTF();

                //Si el pool de conexiones tiene conexiones entonces llamar a esa conexión
                if (true) {

                } else {
                    //Sino crear conexión nueva
                    f.makeConecction();
                }

                if (men.getAction().equalsIgnoreCase("SignIn")) {
                    
                    if (dao.validateDates(men)) {
                        menenv.setMessage("Sesion iniciada");
                    } else {
                         menenv.setMessage("La contraseña o el usuario no son correctos");
                    }
                    env.writeObject(menenv);

                } else if (men.getAction().equalsIgnoreCase("SignUp")) {
                    dao.createNewUser(men);
                }

            } catch (IOException ex) {
                Logger.getLogger(Threads.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
