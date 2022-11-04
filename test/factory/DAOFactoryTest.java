/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import com.mysql.jdbc.Connection;
import implementation.DAOImplementation;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 2dam
 */
public class DAOFactoryTest {
    
    public DAOFactoryTest() {
    }

    /**
     * Test of makeDao method, of class DAOFactory.
     */
    @Test
    public void testMakeDao() {
        System.out.println("makeDao");
        Connection con = null;
        DAOFactory instance = new DAOFactory();
        DAOImplementation expResult = null;
        DAOImplementation result = instance.makeDao(con);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
