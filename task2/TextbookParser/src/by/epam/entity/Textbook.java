/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.entity;

import by.epam.service.ParseService;
import by.epam.util.PatternParser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 *
 * @author Администратор
 */
public class Textbook extends CompoundComponentSkeleton{
    private final Logger LOGGER = Logger.getLogger(Textbook.class); 
    
    
    private Pattern pattern;
    private Matcher matcher;    
    private String data;

    public Textbook() {
    }

    public Textbook(String data) {
        super(data);
        this.data = data;
    }

    public void parse() {
        LOGGER.debug("enters to textbook parse()");
        List<Component> textbook = null;
        String regex = "(1 package .+;(\\s[0-9]+.*)+)"
                + "|((.+\\(.*\\);(.*)?\n)+)";
        try {
            textbook = PatternParser.parseByTwoSaveDelimeter(data, 
                    regex, Text.class, Listing.class);
        } catch (InstantiationException | IllegalAccessException ex) {
            LOGGER.info("An error has occured");
            LOGGER.error("INstExc has occured", ex);
        } 
        this.setComponents(textbook);
        parseChildren();
    }      
}
