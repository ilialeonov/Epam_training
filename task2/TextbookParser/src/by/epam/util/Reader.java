/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.util;

import by.epam.exception.EmptyFileException;
import by.epam.entity.parser.TextParser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 *
 * @author Администратор
 */
public class Reader {
    
    private static final Logger LOGGER = Logger.getLogger(Reader.class); 
    
    public static StringBuilder readFromFile(String path){
        
        LOGGER.debug("Enters process of reading data");        
        
        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException ex) {
            LOGGER.info("File has not been found");
            LOGGER.error("File not found", ex);
        }        
        StringBuilder text = new StringBuilder();
        String line;
        try {
            while((line = in.readLine()) != null){
                text.append(line).append("\n");
            }
            LOGGER.debug("Exits process of initing data for Textbook");
        } catch (IOException ex) {
            LOGGER.info("Some error with access to file");
            LOGGER.error("IOException has occured", ex);
        } 
        return text;
    }
}
