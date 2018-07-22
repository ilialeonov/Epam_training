/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.runner;

import by.epam.service.JewelryService;
import by.epam.entity.GemStone;
import by.epam.entity.Jewelry;
import by.epam.exception.BadIntervalException;
import by.epam.util.parser.JewelryParser;
import by.epam.util.parser.JewelryTxtStreamParserImpl;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;

/**
 *
 * @author Администратор
 */
public class StoneRunner {
    
    private static final Logger logger = Logger.getLogger("by.epam.runner");

    public static void main(String[] args) throws FileNotFoundException {
        JewelryService service = new JewelryService();
        JewelryParser parser = new JewelryTxtStreamParserImpl("stonelist.txt");
        service.setJewelryParser(parser);
        Jewelry necklace = service.createNecklace();
        logger.info(necklace);
    
        int commonCost = service.getGemsCost();
        logger.info("Common cost of all gems is " + commonCost + " $.\n");        
        
        double commonWeight = service.getGemsWeight();
        logger.info("Common weight of all gems is " + commonWeight
                + "carats.\n\n");        
        
        service.sortByCost();
        logger.info(necklace);
        
        List<GemStone> inInterval = null;
        try {
            inInterval = service.getInTransparencyInterval(90, 99);
        } catch (BadIntervalException ex) {
            logger.error("You have entered bad interval", ex);
        }
        logger.info("GemStones in interval of the chosen tranparency: ");
        for (GemStone stone : inInterval) {
            logger.info(stone);
        }
    }
    
}
