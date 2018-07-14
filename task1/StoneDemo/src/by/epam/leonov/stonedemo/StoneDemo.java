/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.leonov.stonedemo;

import by.epam.leonov.controller.JewelryController;
import by.epam.leonov.entity.GemStone;
import by.epam.leonov.entity.Necklace;
import by.epam.leonov.entity.PreciousStone;
import by.epam.leonov.util.JewelryParser;
import by.epam.leonov.util.JewelryTxtParserImp;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Администратор
 */
public class StoneDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        JewelryController controller = new JewelryController();
        JewelryParser parser = new JewelryTxtParserImp("stonelist.txt");
        controller.setJewelryParser(parser);
        controller.createNecklace();
        controller.showNecklace();
        controller.showGemsCost();
        controller.showGemsWeight();
        controller.sortByCost();
        controller.showNecklace();
        controller.showInTransparencyInterval(90, 98);
    }
    
}
