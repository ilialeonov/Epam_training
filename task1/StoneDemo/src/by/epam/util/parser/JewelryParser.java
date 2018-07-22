/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.util.parser;

import java.io.IOException;

/**
 *
 * @author Администратор
 */
public interface JewelryParser {
    public boolean hasNextStone() throws IOException;
    public String getStoneName() throws IOException;
    public int getStoneCost() throws IOException;
    public double getStoneWeight() throws IOException;
    public int getStoneTransparency() throws IOException;
}
