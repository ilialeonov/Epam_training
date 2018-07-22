/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.entity;

import by.epam.exception.NoSuchPositionStoneException;
import java.util.ArrayList;
import java.util.List;

public class Jewelry {
    
    private List<GemStone> stones; 

    public Jewelry() { 
        stones = new ArrayList<>();
    }
    
    protected Jewelry(List<GemStone> stones) {
        this.stones = stones;
    }
    
    public void addStone(GemStone stone) {
        stones.add(stone);
    }

    public void addStoneById(int id, GemStone stone) {
        stones.add(id, stone);
    }
        
    public void removeStone(int i) {
        stones.remove(i);
    } 
    
    public int getStoneAmount() {
        return stones.size();
    }
    
    public GemStone getStone(int i) throws NoSuchPositionStoneException {
        if (i < 0 || i > stones.size()) {
            throw new NoSuchPositionStoneException();
        }
        return stones.get(i);
    }   

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (GemStone stone : stones)
        {
          out.append(stone.toString());
          out.append("\n");
        }        
        return this.getClass().getSimpleName() + " with stones:\n" + out;
    }
}
