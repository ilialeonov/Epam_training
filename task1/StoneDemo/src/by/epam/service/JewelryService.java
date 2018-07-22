/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.service;

import by.epam.entity.Gem;
import by.epam.entity.GemStone;
import by.epam.entity.Jewelry;
import by.epam.exception.BadIntervalException;
import by.epam.exception.NegativeCostException;
import by.epam.exception.NegativeWeightException;
import by.epam.exception.NoSuchGemException;
import by.epam.exception.NoSuchPositionStoneException;
import by.epam.exception.OutOfPercentIntervalException;
import by.epam.util.GemFactory;
import by.epam.util.JewelryBuilder;
import by.epam.util.parser.JewelryParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
/**
 *
 * @author Администратор
 */
public class JewelryService {
    private Jewelry necklace;
    private JewelryParser jewelryParser;
    private int sizeOfElements;
    private double weight = 0;
    private int cost = 0;
    private boolean characteristicsAssigned = false;
    
    private static final Logger logger = Logger.getLogger("by.epam.service");    
    private final GemFactory gemFactory = new GemFactory();
    private final JewelryBuilder necklaceBuilder = new JewelryBuilder();

    public JewelryService() {
        
    }
    
    public void setJewelryParser(JewelryParser jewelryParser){
        this.jewelryParser = jewelryParser;
    }
    
    public Jewelry createNecklace(){
        GemStone nextStone = null;
        try {
            while (jewelryParser.hasNextStone()) {
                try {
                    logger.debug("create necklace");
                    nextStone = getStone();
                } catch (NoSuchGemException ex) {
                    logger.error("An error has occured, no such gem", ex);
                }
                necklaceBuilder.buildStone(nextStone);
            }
            necklace = necklaceBuilder.getJewelry();
            sizeOfElements = necklace.getStoneAmount();
            return necklace;
        } catch (IOException ex) {
            logger.error("An error has occured", ex);
        }
        return necklace;
    }
    
    private GemStone getStone() throws NoSuchGemException {
        String name = "";
        int cost = 0;
        double weight = 0;
        int transparency = 0;
            
        try {
            logger.debug("gets stone");
            name = jewelryParser.getStoneName();
            cost = jewelryParser.getStoneCost();
            weight = jewelryParser.getStoneWeight();
            transparency = jewelryParser.getStoneTransparency();
        } catch (IOException ex) {
            logger.error("An IOE error has occured", ex);
        }    
        Gem gemList = Gem.valueOf(name.toUpperCase());
            
        switch(gemList){ 
            case DIAMOND:
            case ESMERALD:
            case RUBY: {
                try {
                    logger.debug("creating precious in factory");
                    return gemFactory.createPreciousStone(name,
                            cost, weight, transparency);
                } catch (NegativeCostException | NegativeWeightException
                        | OutOfPercentIntervalException ex) {
                    logger.error("An error has occured", ex);
                }
            }
            case AQUAMARINE:
            case GARNET:
            case TOPAZ:
            {
                try {
                    logger.debug("creating semiprecious");
                    return gemFactory.createSemiPreciousStone(name,
                            cost, weight, transparency);
                } catch (NegativeCostException | NegativeWeightException
                        | OutOfPercentIntervalException ex) {
                    logger.error("An error has occured", ex);
                }
            }
            default: throw new NoSuchGemException();
        }
    }
    
    private void assignCharacteristics(){
        final int FIRST_POSITION = 0;
        
        try {
            logger.debug("assigning characteristics");
            for (int i = FIRST_POSITION; i < necklace.getStoneAmount(); i++) {
                weight += necklace.getStone(i).getWeight();
                cost += necklace.getStone(i).getCost();
            }  
        } catch (NoSuchPositionStoneException ex) {
            logger.error("An error has occured", ex);
        }
        characteristicsAssigned = true;
    }
    
    private void checkIfAssigned(){
        if(!characteristicsAssigned) {
            assignCharacteristics();
        }
    }
    
    public double getGemsWeight(){
        checkIfAssigned();
        return weight;        
    }
    
    public int getGemsCost(){
        checkIfAssigned();
        return cost;        
    }
    
    public void sortByCost(){
        int sizeOfSteps = sizeOfElements - 1;
        GemStone temp;
        boolean isSorted = true;

        try {
            logger.debug("sorting");
            for (int i = 0; i < sizeOfSteps; i++) {
                for (int j = sizeOfElements - 1; j > i; j--) { 
                    if (necklace.getStone(j).getCost() 
                            > necklace.getStone(j - 1).getCost()) {
                        temp = necklace.getStone(j);
                        necklace.removeStone(j);
                        necklace.addStoneById(j, necklace.getStone(j - 1));
                        necklace.removeStone(j - 1);
                        necklace.addStoneById(j - 1, temp);
                        isSorted = false;
                    }
                }
                if (isSorted) {
                    break;
                }
            }
        } catch (NoSuchPositionStoneException ex) {
            logger.info("NSPSException", ex);
        }
    }
    
    public List<GemStone> getInTransparencyInterval(int down, int up) throws BadIntervalException{
        if (down < 0 || up > 100 || down > up) {
            logger.debug("checking transparency");            
            throw new BadIntervalException();
        }
        List<GemStone> inInterval = new ArrayList<>();
        
        try {
            logger.debug("getting in transparency");
            for (int i = 0; i < sizeOfElements; i++) {
                int transparency = necklace.getStone(i).getTransparency();
                if(down <= transparency && transparency <= up) {
                    inInterval.add(necklace.getStone(i));
                }
            }    
        } catch (NoSuchPositionStoneException ex) {
            logger.info("There is no such position stone", ex);
        }
        return inInterval;
    }
}
