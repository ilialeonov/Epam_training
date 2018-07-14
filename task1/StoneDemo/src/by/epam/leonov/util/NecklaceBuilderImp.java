/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.leonov.util;

import by.epam.leonov.entity.GemStone;
import by.epam.leonov.entity.Jewelry;
import by.epam.leonov.entity.Necklace;

public class NecklaceBuilderImp implements JewelryBuilder{
    
    private Necklace jewelry = new Necklace();

    public NecklaceBuilderImp() {
    }

    @Override
    public Necklace getJewelry() {
        return jewelry;
    }

    @Override
    public void buildStones(GemStone...stones) {
        for (GemStone stone : stones) {
            jewelry.addStone(stone);
        }
    }

    @Override
    public void buildStone(GemStone stone) {
        jewelry.addStone(stone);
    }
    
}
