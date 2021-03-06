/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.util;

import by.epam.entity.GemStone;
import by.epam.entity.Jewelry;

public class JewelryBuilder{
    
    private Jewelry jewelry = new Jewelry();

    public JewelryBuilder() {
    }

    public Jewelry getJewelry() {
        return jewelry;
    }

    public void buildStones(GemStone...stones) {
        for (GemStone stone : stones) {
            jewelry.addStone(stone);
        }
    }

    public void buildStone(GemStone stone) {
        jewelry.addStone(stone);
    }
    
}
