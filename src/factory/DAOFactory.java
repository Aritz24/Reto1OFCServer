/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import com.mysql.jdbc.Connection;
import implementation.DAOImplementation;
import interfacePackage.DaoInterface;

/**
 *
 * @author Aritz
 */
public class DAOFactory {

    public DAOFactory() {
         
    }
    
    public DAOImplementation makeDao(Connection con){
        DaoInterface dao = (DaoInterface) 
                new DAOImplementation(con);
        return (DAOImplementation) dao;
    }
   
}
