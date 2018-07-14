/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.leonov.util;

import by.epam.leonov.entity.GemStone;
import by.epam.leonov.entity.Jewelry;

/**
 *
 * @author Администратор
 */
public interface JewelryBuilder {
    Jewelry getJewelry();
    void buildStone(GemStone stone);
    void buildStones(GemStone...stones);
}
