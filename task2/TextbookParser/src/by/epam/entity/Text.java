/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.entity;

import by.epam.util.PatternParser;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 *
 * @author Администратор
 */
public class Text extends CompoundComponentSkeleton{
    private final Logger LOGGER = Logger.getLogger(Text.class); 
    public Text() {
    }

    public Text(String data) {
        super(data);
    }
    
    @Override
    public void parse() { 
        LOGGER.debug("enters to text parse()");
        
        List<Component> text = null; 
        String regex = "\n";
        System.out.println("here");
        String data = this.getData();
        try {
            //System.out.println("data" + data);
            text = PatternParser.parseByOneNoDelimeter(data, 
                    regex, Paragraph.class);
        } catch (InstantiationException | IllegalAccessException ex) {
            LOGGER.info("Some error occured");
            LOGGER.error("InstantiationExc or IllegalAccessExc", ex);
        }
        this.setComponents(text);
        parseChildren();
    }

}
