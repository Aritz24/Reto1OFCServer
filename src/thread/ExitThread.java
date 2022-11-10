/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import static java.lang.System.exit;
import java.util.Scanner;
import java.util.logging.Logger;
import pool.ConnectionPool;

/**
 *
 * @author 2dam
 */
public class ExitThread extends Thread {

    public static final Logger LOGGER = Logger.getLogger("thread/ExitThread");

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);

        LOGGER.info("PARA CERRAR EL SERVIDOR Y LAS CONEXIONES A"
                + " LA BD ESCRIBA EXIT");
        while (!this.isInterrupted()) {

            if (sc.next().equalsIgnoreCase("Exit")) {
                ConnectionPool.closeConnection();
                this.interrupt();
            }
        }
        exit(0);
    }
}
