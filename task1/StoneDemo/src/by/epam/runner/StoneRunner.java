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
import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;

/**
 *
 * @author Администратор
 */
public class StoneRunner {
    
    private static final Logger LOGGER = Logger.getLogger(StoneRunner.class);

    public static void main(String[] args) throws FileNotFoundException {
        JewelryService service = new JewelryService();
        JewelryParser parser = new JewelryTxtStreamParserImpl("stonelist.txt");
        service.setJewelryParser(parser);
        Jewelry necklace = service.createNecklace();
        LOGGER.info(necklace);
    
        int commonCost = service.getGemsCost();
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info(MessageFormat.format(
                    "Common cost of all gems is {0} $.\n ", commonCost));     
        }
        
        double commonWeight = service.getGemsWeight();
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info(MessageFormat.format(
                    "Common weight of all gems is {0} carats.\n "
                    , commonWeight));    
        }
        
        service.sortByCost();
        LOGGER.info(necklace);
        
        List<GemStone> inInterval = null;
        try {
            inInterval = service.getInTransparencyInterval(90, 99);
        } catch (BadIntervalException ex) {
            LOGGER.info("You have entered bad interval");
            LOGGER.error("User have entered bad interval", ex);
        }
        LOGGER.info("GemStones in interval of the chosen tranparency: ");
        for (GemStone stone : inInterval) {
            LOGGER.info(stone);
        }
    }
    
}
