/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.runner;

import by.epam.entity.Candy;
import by.epam.util.AbstractCandiesBuilder;
import by.epam.util.CandiesDOMBuilder;
import by.epam.util.CandiesSaxBuilder;
import by.epam.util.CandiesStaxBuilder;
import by.epam.util.CandyBuilderFactory;
import by.epam.util.SchemaValidator;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author Администратор
 */
public class CandiesRunner {
    private static final Logger LOG = Logger.getLogger(CandiesRunner.class);
    
    private static final String PARSERNAME = "stax";
    public static void main(String[] args) {
        LOG.info("Checking if xml file is valid to xsd scheme");
        String filePath = "dataXML\\Candies.xml";
        String schemaPath = "dataXML\\Candies.xsd";
        SchemaValidator.validate(filePath, schemaPath);
        LOG.info("Parsing xml file");
        CandyBuilderFactory factory = new CandyBuilderFactory();
        LOG.info("The chosen parser is " + PARSERNAME);
        AbstractCandiesBuilder builder = factory.createStudentBuilder(PARSERNAME);
        builder.buildSetCandies(filePath);
        LOG.info("Parsed elements are:");
        Set<Candy> candies = builder.getCandies();
        for (Candy x: candies) {
            LOG.info(x);
        }
    }
}
