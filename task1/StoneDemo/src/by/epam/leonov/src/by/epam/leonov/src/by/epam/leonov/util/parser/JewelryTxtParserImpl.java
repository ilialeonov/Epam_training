/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.leonov.util.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 *
 * @author Администратор
 */
public class JewelryTxtParserImpl implements JewelryParser{
    
    Scanner scan;
    
    public JewelryTxtParserImpl(String path) {
        try {
            scan = new Scanner(new File(path));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    } 
    
    public boolean hasNextStone() {
        return scan.hasNextLine();
    }
    
    public String getStoneName() {
        return scan.nextLine();
    }
    
    public int getStoneCost() {
        return Integer.parseInt(scan.nextLine());
    }
    
    public double getStoneWeight() {
        return Double.parseDouble(scan.nextLine());
    } 
    
    public int getStoneTransparency() {
        return Integer.parseInt(scan.nextLine());
    }
    
}
