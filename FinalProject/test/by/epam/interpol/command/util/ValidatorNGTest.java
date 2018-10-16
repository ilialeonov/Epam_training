/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command.util;

import java.awt.image.BufferedImage;
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
public class ValidatorNGTest {
    private static final Logger LOG = LogManager.getLogger(Validator.class);
    
    public ValidatorNGTest() {
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

    @DataProvider
    public Object[][] loginPasswordData() {
      return new Object[][]{
        {new String[]{null, null}, false},
        {new String[]{"", ""}, false},
        {new String[]{"-cxzzxc", "zszs"}, false},
        {new String[]{"saSA23sd", "12sdanJS"}, true},
        {new String[]{"sdmnsaJSD", "smsda77"}, true}
      };
    }
    
    @Test(dataProvider = "loginPasswordData")
    public void testIsLoginPasswordValide(String[] pair, boolean bool) {
        LOG.debug("isLoginPasswordValide");
        String login = pair[0];
        String password = pair[1];
        boolean expResult = bool;
        boolean result = Validator.isLoginPasswordValide(login, password);
        assertEquals(result, expResult);
       }

    @DataProvider
    public Object[][] userData() {
      return new Object[][]{
        {new String[]{null, null, null, null}, false},
        {new String[]{"", "", "", ""}, false},
        {new String[]{"-cxzzxc", "zszs", "saSA23sd", "12sdanJS"}, false},
        {new String[]{"saSA23sd", "12sdanJS", "saSA23sd", "12sdanJS"}, false},
        {new String[]{"sdmnsaJSD", "smsda77", "saSA23sd", "12sdanJS"}, false},
        {new String[]{"sdmnsaJSD", "smsda77@mail.ru", "saSA23sd", "12sdanJS"}, true},
        {new String[]{"SdmnsaJSD", "smsda77@mail.ru", "saSA23sd", "12sdanJS"}, true}
      };
    }
    
    @Test(dataProvider = "userData")
    public void testAreUserDataValid(String[] data, boolean bool) {
        LOG.debug("areUserDataValid");
        String name = data[0];
        String mail = data[1];
        String login = data[2];
        String password = data[3];
        boolean expResult = bool;
        boolean result = Validator.areUserDataValid(name, mail, login, password);
        assertEquals(result, expResult);
    }

    @DataProvider
    public Object[][] idData() {
      return new Object[][]{
        {null, false},
        {"", false},
        {new String("3"), true},
        {new String("333"), true},
        {new String("99999999"), true},
      };
    }

    @Test(dataProvider = "idData")
    public void testIdIsValid(String id, boolean bool) {
        LOG.debug("idIsValid");
        String idStr = id;
        boolean expResult = bool;
        boolean result = Validator.idIsValid(idStr);
        assertEquals(result, expResult);
    }

    @DataProvider
    public Object[][] namePannameData() {
      return new Object[][]{
        {new String[]{null, null}, false},
        {new String[]{"", ""}, false},
        {new String[]{"-cxzzxc", "zszs"}, false},
        {new String[]{"saSA23sd", "12sdanJS"}, false},
        {new String[]{"sdmnsaJSD", "smsda"}, false},
        {new String[]{"SSdmnsaJSD", "Smsda"}, true},
      };
    }
    
    @Test(dataProvider = "namePannameData")
    public void testNamePannameAreValid(String[] pair, boolean bool) {
        LOG.debug("namePannameAreValid");
        String name = pair[0];
        String panname = pair[1];
        boolean expResult = bool;
        boolean result = Validator.namePannameAreValid(name, panname);
        assertEquals(result, expResult);
    }

    @DataProvider
    public Object[][] regionData() {
      return new Object[][]{
        {null, false},
        {"", false},
        {new String("CSsda3"), false},
        {new String("CasaCsd"), true},
        {new String("sadcass"), false},
      };
    }
    
    @Test(dataProvider = "regionData")
    public void testIsRegionValid(String str, boolean bool) {
        LOG.debug("isRegionValid");
        String region = str;
        boolean expResult = bool;
        boolean result = Validator.isRegionValid(region);
        assertEquals(result, expResult);
    }

    @DataProvider
    public Object[][] pointsData() {
      return new Object[][]{
        {null, false},
        {"", false},
        {new String("CSsda3"), false},
        {new String("3"), true},
        {new String("0"), true},
        {new String("44"), true},
        {new String("99"), true},
        {new String("100"), false},
      };
    }

    @Test(dataProvider = "pointsData")
    public void testPointsIsValid(String points, boolean bool) {
        LOG.debug("pointsIsValid");
        String pointsStr = points;
        boolean expResult = bool;
        boolean result = Validator.pointsIsValid(pointsStr);
        assertEquals(result, expResult);
    }
    
}
