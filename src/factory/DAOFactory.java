/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;


import implementation.DAOImplementation;
import interfacePackage.mainInterface;

/**
 * In this class we create the implementations of the intrface.
 * @author Aritz
 */
public class DAOFactory {

    public DAOFactory() {
         
    }
    
    /**
     * We create the implementations of the intrface
     * @return Returns DAOImplementation.
     */
    public DAOImplementation makeDao(){
        mainInterface dao = (mainInterface) 
                new DAOImplementation();
        return (DAOImplementation) dao;
    }
   
}
