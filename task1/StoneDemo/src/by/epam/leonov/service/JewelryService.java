/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.leonov.service;

import by.epam.leonov.entity.Gem;
import by.epam.leonov.entity.GemStone;
import by.epam.leonov.entity.Jewelry;
import by.epam.leonov.exception.BadIntervalException;
import by.epam.leonov.exception.NegativeCostException;
import by.epam.leonov.exception.NegativeWeightException;
import by.epam.leonov.exception.NoSuchGemException;
import by.epam.leonov.exception.NoSuchPositionStoneException;
import by.epam.leonov.exception.OutOfPercentIntervalException;
import by.epam.leonov.util.GemFactory;
import by.epam.leonov.util.JewelryBuilder;
import by.epam.leonov.util.parser.JewelryParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                    nextStone = getStone();
                } catch (NoSuchGemException ex) {
                    ex.printStackTrace();
                }
                necklaceBuilder.buildStone(nextStone);
            }
            necklace = necklaceBuilder.getJewelry();
            sizeOfElements = necklace.getStoneAmount();
            return necklace;
        } catch (IOException ex) {
            Logger.getLogger(JewelryService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return necklace;
    }
    
    private GemStone getStone() throws NoSuchGemException {
        String name = "";
        int cost = 0;
        double weight = 0;
        int transparency = 0;
            
        try {
            name = jewelryParser.getStoneName();
            cost = jewelryParser.getStoneCost();
            weight = jewelryParser.getStoneWeight();
            transparency = jewelryParser.getStoneTransparency();
        } catch (IOException ex) {
            Logger.getLogger(JewelryService.class.getName()).log(Level.SEVERE, null, ex);
        }    
        Gem gemList = Gem.valueOf(name.toUpperCase());
            
        switch(gemList){ 
            case DIAMOND:
            case ESMERALD:
            case RUBY: {
                try {
                    return gemFactory.createPreciousStone(name,
                            cost, weight, transparency);
                } catch (NegativeCostException | NegativeWeightException
                        | OutOfPercentIntervalException ex) {
                    System.out.println("some error");
                    ex.printStackTrace();
                }
            }
            case AQUAMARINE:
            case GARNET:
            case TOPAZ:
            {
                try {
                    return gemFactory.createSemiPreciousStone(name,
                            cost, weight, transparency);
                } catch (NegativeCostException | NegativeWeightException
                        | OutOfPercentIntervalException ex) {
                    System.out.println("some error");
                    ex.printStackTrace();
                }
            }
            default: throw new NoSuchGemException();
        }
    }
    
    private void assignCharacteristics(){
        final int FIRST_POSITION = 0;
        
        try {
            for (int i = FIRST_POSITION; i < necklace.getStoneAmount(); i++) {
                weight += necklace.getStone(i).getWeight();
                cost += necklace.getStone(i).getCost();
            }  
        } catch (NoSuchPositionStoneException ex) {
            System.out.println("There is no such position stone");
            ex.printStackTrace();
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
            System.out.println("There is no such position stone");
            ex.printStackTrace();
        }
    }
    
    public List<GemStone> getInTransparencyInterval(int down, int up) throws BadIntervalException{
        if (down < 0 || up > 100 || down > up) {
            System.out.println("You have entered bad interval");            
            throw new BadIntervalException();
        }
        List<GemStone> inInterval = new ArrayList<>();
        
        try {
            for (int i = 0; i < sizeOfElements; i++) {
                int transparency = necklace.getStone(i).getTransparency();
                if(down <= transparency && transparency <= up) {
                    inInterval.add(necklace.getStone(i));
                }
            }    
        } catch (NoSuchPositionStoneException ex) {
            System.out.println("There is no such position stone");
                ex.printStackTrace();
        }
        return inInterval;
    }
    
    public void showInTransparencyInterval(int down, int up){
        List<GemStone> inInterval = null;
        try {
            inInterval = getInTransparencyInterval(down, up);
        } catch (BadIntervalException ex) {
            System.out.println("You have entered bad interval");
            ex.printStackTrace();
        }
    }
}
