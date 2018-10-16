/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command.util;

import by.epam.interpol.pool.ConnectionPoolNGTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Администратор
 */
public class PersonDefinerNGTest {
    private static final Logger LOG = LogManager.getLogger(ConnectionPoolNGTest.class);
    
    public PersonDefinerNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Тест метод getInstance, класса PersonDefiner.
     */
    @Test
    public void testGetInstance() {
        LOG.info("getInstance");
        /*
        * Checks if method returns the same instance
        */
        
        PersonDefiner expResult = PersonDefiner.getInstance();
        PersonDefiner result = PersonDefiner.getInstance();
        assertTrue(result == expResult);
    }
}
