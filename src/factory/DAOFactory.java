/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import implementation.DAOImplementation;

/**
 *
 * @author Aritz
 */
public class DAOFactory {
   
    public DAOInterface DAOFactory(){
         return new DAOImplementation();
    }
      
       
    
}
