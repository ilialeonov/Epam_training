/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.leonov.runner;

import by.epam.leonov.service.JewelryService;
import by.epam.leonov.entity.GemStone;
import by.epam.leonov.entity.Jewelry;
import by.epam.leonov.exception.BadIntervalException;
import by.epam.leonov.util.parser.JewelryParser;
import by.epam.leonov.util.parser.JewelryTxtParserImpl;
import by.epam.leonov.util.parser.JewelryTxtStreamParserImpl;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Администратор
 */
public class StoneRunner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        JewelryService service = new JewelryService();
        JewelryParser parser = new JewelryTxtStreamParserImpl("stonelist.txt");
        service.setJewelryParser(parser);
        Jewelry necklace = service.createNecklace();
        System.out.println(necklace);
    
        int commonCost = service.getGemsCost();
        System.out.println("Common cost of all gems is " + commonCost + " $.\n");        
        
        double commonWeight = service.getGemsWeight();
        System.out.printf("Common weight of all gems is %.2f  "
                + "carats.\n\n", commonWeight);        
        
        service.sortByCost();
        System.out.println(necklace);
        
        List<GemStone> inInterval = null;
        try {
            inInterval = service.getInTransparencyInterval(90, 99);
        } catch (BadIntervalException ex) {
            System.out.println("You have entered bad interval");
            ex.printStackTrace();
        }
        System.out.println("GemStones in interval of the chosen tranparency: ");
        for (GemStone stone : inInterval) {
            System.out.println(stone);
        }
    }
    
}
