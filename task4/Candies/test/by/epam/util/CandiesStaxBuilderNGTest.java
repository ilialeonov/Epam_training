/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.util;

import by.epam.entity.Candy;
import by.epam.entity.ChocoType;
import by.epam.entity.Ingridient;
import by.epam.entity.SweetType;
import by.epam.entity.Value;
import by.epam.exception.NoSourceException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import org.apache.log4j.Logger;
import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CandiesStaxBuilderNGTest {
    
    private static final Logger LOG = Logger.getLogger(CandiesStaxBuilder.class);
    
    private static final String PARSERNAME = "stax";
    private CandyBuilderFactory factory = new CandyBuilderFactory();
    private AbstractCandiesBuilder builder;
    private static String FILE_PATH = "dataXML\\Candies.xml";  
    private String BAD_FILE_PATH = "dataXML\\Candiess.xml";
    private String SINGLE_CANDY_FILE_PATH = "dataXML\\Candies_test.xml";
    private static Candy candy;
    private static Ingridient ingridient;
    private static Value value;
    
    
    private final static String NAME = "Raffaello";
    private final static int ENERGY = 412;
    private final static String ENERGY_UNIT = "kkal";
    private final static SweetType SWEET_TYPE = SweetType.CHOCOLATESOUFFLE;
    private final static ChocoType CHOCO_TYPE = ChocoType.WHITE;
    private final static int WATER = 206;
    private final static String WATER_UNIT = "ml";
    private final static int SUGAR = 416;
    private final static String SUGAR_UNIT = "mg";
    private final static int FRUCTOSE = 210;
    private final static String FRUCTOSE_UNIT = "mg";
    private final static int VANILIN = 56;
    private final static String VANILIN_UNIT = "mg";
    private final static int PROTEIN = 52;
    private final static String PROTEIN_UNIT = "g";
    private final static int FAT = 12;
    private final static String FAT_UNIT = "g";
    private final static int CARBOHYDRATE = 36;
    private final static String CARBOHYDRATE_UNIT = "g";
    private final static String PRODUCER = "Raffaello";
    private final static Calendar CAL = Calendar.getInstance();

    public CandiesStaxBuilderNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        candy = new Candy();
        candy.setName(NAME);
        candy.setEnergy(ENERGY);
        candy.setEnergyUnit(ENERGY_UNIT);
        candy.setType(SWEET_TYPE);
        ingridient = new Ingridient();
        ingridient.setType(CHOCO_TYPE);
        ingridient.setWater(WATER);
        ingridient.setWaterUnit(WATER_UNIT);
        ingridient.setSugar(SUGAR);
        ingridient.setSugarUnit(SUGAR_UNIT);
        ingridient.setFructose(FRUCTOSE);
        ingridient.setFructoseUnit(FRUCTOSE_UNIT);
        ingridient.setVanilin(VANILIN);
        ingridient.setVanilinUnit(VANILIN_UNIT);
        candy.setIngridient(ingridient);
        value = new Value();
        value.setProtein(PROTEIN);
        value.setProteinUnit(PROTEIN_UNIT);
        value.setFat(FAT);
        value.setFatUnit(FAT_UNIT);
        value.setCarbohydrate(CARBOHYDRATE);
        value.setCarbohydrateUnit(CARBOHYDRATE_UNIT);
        candy.setValue(value);
        candy.setProducer(PRODUCER);
        Date date = new SimpleDateFormat( "dd.MM.yyyy" ).parse( "24.09.2017" );
        CAL.set(2017, Calendar.SEPTEMBER, 24);
        candy.setDeliveryTime(date);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        candy = null;
        ingridient = null;
        value = null;
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    @DataProvider
    public Object[][] staxData() {
        return new Object[][]{
            {FILE_PATH},
            {BAD_FILE_PATH}
        };
    } 
    
    @DataProvider
    public Object[][] staxDataException() {
        return new Object[][]{
            {null}
        };
    }         

    @DataProvider
    public Object[][] staxDataSingleCandy() {
        return new Object[][]{
            {SINGLE_CANDY_FILE_PATH, candy}
        };
    }        
    
    @Test(dataProvider = "staxData")
    public void testBuildSetCandiesFine(String input) 
            throws NoSourceException {      
        builder = factory.createCandyBuilder(PARSERNAME);
        LOG.info("TEST TWO");
        builder.buildSetCandies(input);
        Set<Candy> candies = builder.getCandies();
        Assert.assertNotNull(candies);
        LOG.info("Test two has been successfully done");
    }
    
    @Test(dataProvider = "staxDataException", expectedExceptions = NoSourceException.class)
    public void testBuildSetCandiesFineException(String input) 
            throws NoSourceException {      
        builder = factory.createCandyBuilder(PARSERNAME);
        LOG.info("TEST TWO Exception");
        builder.buildSetCandies(input);
        Set<Candy> candies = builder.getCandies();
        Assert.assertNotNull(candies);
        LOG.info("Test two has been successfully done");
    } 
    
    @Test(dataProvider = "staxDataSingleCandy")
    public void testBuildSetCandiesSingle(String path, Candy expected) 
            throws NoSourceException { 
        builder = factory.createCandyBuilder(PARSERNAME);
        System.out.println("******************************");
        System.out.println(path);
        builder.buildSetCandies(path);
        Set<Candy> candyParsedSet = builder.getCandies();
        Candy candyParsed = (Candy) candyParsedSet.toArray()[0];
        Assert.assertEquals(candyParsed, expected);
    }
}
