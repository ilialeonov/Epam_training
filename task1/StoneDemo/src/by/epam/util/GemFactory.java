/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.util;

import by.epam.entity.GemStone;
import by.epam.entity.Value;
import by.epam.exception.NegativeCostException;
import by.epam.exception.NegativeWeightException;
import by.epam.exception.OutOfPercentIntervalException;
import org.apache.log4j.Logger;

/**
 *
 * @author Администратор
 */
public class GemFactory {
    private final static Logger LOGGER = Logger.getLogger(GemFactory.class);

    public GemFactory() {
    }
    
    public GemStone createPreciousStone(String name, int cost, 
            double weight, int transparency) throws NegativeCostException, 
            NegativeWeightException, OutOfPercentIntervalException {
        
        checkStone(cost, weight, transparency);
        GemStone preciousStone = createTypicalStone(name, cost, 
            weight, transparency);
        preciousStone.setValue(Value.PRECIOUS);
        
        return preciousStone;
    }
    
    public GemStone createSemiPreciousStone(String name, int cost, 
            double weight, int transparency) throws NegativeCostException, 
            NegativeWeightException, OutOfPercentIntervalException {
        
        GemStone semiPreciousStone = createTypicalStone(name, cost, 
            weight, transparency);
        semiPreciousStone.setValue(Value.SEMIPRECIOUS);
        
        return semiPreciousStone;
    }    
    
    private void checkStone(int cost, double weight, int transparency) throws 
            NegativeCostException, NegativeWeightException, 
            OutOfPercentIntervalException {
        
        LOGGER.debug("checks stone");
        
        if (cost < 0) {
            throw new NegativeCostException();
        } else if (weight < 0) {
            throw new NegativeWeightException();
        } else if (transparency <= 0 || transparency >= 100) {
            throw new OutOfPercentIntervalException();
        }        
    }
    
    private GemStone createTypicalStone(String name, int cost, 
            double weight, int transparency) throws NegativeCostException, 
            NegativeWeightException, OutOfPercentIntervalException{
        
        LOGGER.debug("creates stone");
        
        checkStone(cost, weight, transparency);
        
        GemStone stone  = new GemStone();
        
        stone.setName(name);
        stone.setCost(cost);
        stone.setWeight(weight);        
        stone.setTransparency(transparency);
        
        return stone;
    }
}
