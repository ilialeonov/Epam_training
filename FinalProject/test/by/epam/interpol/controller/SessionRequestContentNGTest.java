/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.controller;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author Администратор
 */
public class SessionRequestContentNGTest {
    private static final Logger LOG 
            = LogManager.getLogger(SessionRequestContentNGTest.class);
    
    public SessionRequestContentNGTest() {
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

    @Test
    public void testActivateSession() {
        LOG.debug("activateSession");
        SessionRequestContent instance = new SessionRequestContent();
        instance.activateSession();
        assertTrue(instance.isSessionIsActive());
        }

    @Test
    public void testDeactivateSession() {
        System.out.println("deactivateSession");
        SessionRequestContent instance = new SessionRequestContent();
        instance.activateSession();
        instance.deactivateSession();
        assertFalse(instance.isSessionIsActive());
    }

    @DataProvider
    public Object[][] attributeData() {
      return new Object[][]{
        {"command", "run"},
        {"operation", "forward"},
        {null, null},
        {"bool", true},
        {"number", 21}
      };
    }
    
    @Test(dataProvider = "attributeData")
    public void testSetGetRequestAttribute(String key, Object value) {
        LOG.debug("getRequestAttribute");
        SessionRequestContent instance = new SessionRequestContent();
        instance.setRequestAttribute(key, value);
        assertEquals(instance.getRequestAttribute(key), value);
    }

    @Test(dataProvider = "attributeData")
    public void testNotActiveSessionRequestAttribute(String key, Object value) {
        LOG.debug("getSessionRequestAttribute");
        SessionRequestContent instance = new SessionRequestContent();
        instance.setSessionRequestAttribute(key, value);
        assertEquals(instance.getSessionRequestAttribute(key), null);
    }
    
    @Test(dataProvider = "attributeData")
    public void testSetGetSessionRequestAttribute(String key, Object value) {
        LOG.debug("getSessionRequestAttribute");
        SessionRequestContent instance = new SessionRequestContent();
        instance.activateSession();
        instance.setSessionRequestAttribute(key, value);
        assertEquals(instance.getSessionRequestAttribute(key), value);
    }

    @Test
    public void testGetRequestAttributes() {
        LOG.debug("getRequestAttributes");
        HashMap<String, Object> map = new HashMap();
        map.put("String", "word");
        map.put("int", 21);
        map.put("obj", new Rectangle());
        SessionRequestContent instance = new SessionRequestContent();
        instance.setRequestAttribute("String", "word");
        instance.setRequestAttribute("int", 21);
        instance.setRequestAttribute("obj", new Rectangle());
        HashMap<String, Object> mapResult = instance.getRequestAttributes();
        assertEquals(map, mapResult);
    }

    @Test
    public void testGetSessionAttributes() {
        LOG.debug("getSessionAttributes");
        HashMap<String, Object> map = new HashMap();
        map.put("String", "word");
        map.put("int", 21);
        map.put("obj", new Rectangle());
        SessionRequestContent instance = new SessionRequestContent();
        instance.setSessionIsActive(true);
        instance.setSessionRequestAttribute("String", "word");
        instance.setSessionRequestAttribute("int", 21);
        instance.setSessionRequestAttribute("obj", new Rectangle());
        HashMap<String, Object> mapResult = instance.getSessionAttributes();
        assertEquals(map, mapResult);
    }

}
