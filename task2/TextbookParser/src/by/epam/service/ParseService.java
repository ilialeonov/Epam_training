/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.service;

import by.epam.entity.Textbook;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import org.apache.log4j.Logger;

public class ParseService {
    private final Logger LOGGER = Logger.getLogger(ParseService.class);
    private BufferedReader in;
    private StringBuilder data;
    Textbook textbook;
    
    public ParseService() {
        
    }
    
    public void setSource(String path) {
        try {
            in = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException ex) {
            LOGGER.info("File has not been found");
            LOGGER.error("File not found", ex);
        }
        initData();
        LOGGER.debug("sets datas for parsing to textbook");
        textbook = new Textbook(data.toString());
    }  
    
    private void initData() {
        LOGGER.debug("Enters process of initing data for Textbook");
        data = new StringBuilder();
        String line;
        try {
            while((line = in.readLine()) != null){
                data.append(line).append("\n");
            }
            LOGGER.debug("Exits process of initing data for Textbook");
        } catch (IOException ex) {
            LOGGER.info("Some error with access to file");
            LOGGER.error("IOException has occured", ex);
        }
    }  
    
    
    public void parseToListening(){
        textbook.parse();
    }    
    
    public void parseToSentense(){
        
    }    
    
    public void parseToWords(){
        
    }
    
    public void parseToSymbols(){
        
    }
}
