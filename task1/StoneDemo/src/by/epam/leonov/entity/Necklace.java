/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.leonov.entity;

import java.util.List;

/**
 *
 * @author Администратор
 */
public class Necklace extends Jewelry{

    public Necklace() {
    }

    protected Necklace(List<GemStone> stones) {
        super(stones);
    }
    
}
