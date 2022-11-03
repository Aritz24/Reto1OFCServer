/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;


import implementation.DAOImplementation;
import interfacePackage.mainInterface;
import com.mysql.jdbc.Connection;

/**
 *
 * @author Aritz
 */
public class DAOFactory {

    public DAOFactory() {
         
    }
    
    public DAOImplementation makeDao(Connection con){
        mainInterface dao = (mainInterface) 
                new DAOImplementation(con);
        return (DAOImplementation) dao;
    }
   
}
