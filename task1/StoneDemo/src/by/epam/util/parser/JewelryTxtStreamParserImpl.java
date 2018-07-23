/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.util.parser;

import by.epam.util.GemFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import org.apache.log4j.Logger;

/**
 *
 * @author Администратор
 */
public class JewelryTxtStreamParserImpl implements JewelryParser{
    private final static Logger LOGGER = Logger.getLogger(GemFactory.class);
    private BufferedReader in;
    private String line;

    public JewelryTxtStreamParserImpl(String path) throws FileNotFoundException {
        in = new BufferedReader(new FileReader(path));
    } 
    
    public boolean hasNextStone() throws IOException{
        LOGGER.debug("checks if there is next stone");
        line = in.readLine();
        return line != null;
    }
    
    public String getStoneName() throws IOException{
        if (line == null) {
            return in.readLine();
        } else {
            return line;
        }   
    }
    
    public int getStoneCost() throws IOException{
        return Integer.parseInt(in.readLine());
    }
    
    public double getStoneWeight() throws IOException{
        return Double.parseDouble(in.readLine());
    } 
    
    public int getStoneTransparency() throws IOException{
        return Integer.parseInt(in.readLine());
    }    
}
