/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.leonov.util;

/**
 *
 * @author Администратор
 */
public interface JewelryParser {
    public boolean hasNextStone();
    public String getStoneName();
    public int getStoneCost();
    public double getStoneWeight();
    public int getStoneTransparency();
}
