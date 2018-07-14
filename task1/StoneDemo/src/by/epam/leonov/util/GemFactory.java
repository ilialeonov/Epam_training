/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.leonov.util;

import by.epam.leonov.entity.PreciousStone;
import by.epam.leonov.entity.SemiPreciousStone;
import by.epam.leonov.exception.NegativeCostException;
import by.epam.leonov.exception.NegativeWeightException;
import by.epam.leonov.exception.OutOfPercentIntervalException;

/**
 *
 * @author Администратор
 */
public class GemFactory {

    public GemFactory() {
    }
    
    public PreciousStone createPreciousStone(String name, int cost, 
            double weight, int transparency) throws NegativeCostException, 
            NegativeWeightException, OutOfPercentIntervalException {
        
        checkStone(cost, weight, transparency);
        
        PreciousStone preciousStone  = new PreciousStone();
        
        preciousStone.setName(name);
        preciousStone.setCost(cost);
        preciousStone.setWeight(weight);        
        preciousStone.setTransparency(transparency);
        
        return preciousStone;
    }
    
    public SemiPreciousStone createSemiPreciousStone(String name, int cost, 
            double weight, int transparency) throws NegativeCostException, 
            NegativeWeightException, OutOfPercentIntervalException {
        
        checkStone(cost, weight, transparency);
        
        SemiPreciousStone semiPreciousStone  = new SemiPreciousStone();
        
        semiPreciousStone.setName(name);
        semiPreciousStone.setCost(cost);
        semiPreciousStone.setWeight(weight);        
        semiPreciousStone.setTransparency(transparency);
        
        return semiPreciousStone;
    }    
    private void checkStone(int cost, double weight, int transparency) throws 
            NegativeCostException, NegativeWeightException, 
            OutOfPercentIntervalException {
        
        if (cost < 0) {
            throw new NegativeCostException();
        } else if (weight < 0) {
            throw new NegativeWeightException();
        } else if (transparency <= 0 || transparency >= 100) {
            throw new OutOfPercentIntervalException();
        }
        
    }
}
