/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.runner;

import by.epam.entity.Candy;
import by.epam.sevice.CandiesSaxBuilder;
import by.epam.util.SchemaValidator;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author Администратор
 */
public class CandiesRunner {
    private static final Logger LOG = Logger.getLogger(CandiesRunner.class);
    
    public static void main(String[] args) {
        LOG.info("Checking if xml file is valid to xsd scheme");
        String filePath = "Candies.xml";
        String schemaPath = "Candies.xsd";
        SchemaValidator.validate(filePath, schemaPath);
        CandiesSaxBuilder candiesSB = new CandiesSaxBuilder();
        candiesSB.buildSetCandies(filePath);
        Set<Candy> candies = candiesSB.getCandies();
        for (Candy x: candies) {
            LOG.info(x);
        }
    }
}
