/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.entity;

import by.epam.util.PatternParser;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 *
 * @author Администратор
 */
public class Paragraph extends CompoundComponentSkeleton{
    private final Logger LOGGER = Logger.getLogger(Paragraph.class); 
  
    public Paragraph() {
    }

    public Paragraph(String data) {
        super(data);
    }

    @Override
    public void parse() {
        LOGGER.debug("enters to text parse()");
        List<Component> paragraph = null;
        String regex = ".*?[.!?:;]";
        String data = this.getData();
        try {
            //System.out.println("data" + data);
            paragraph = PatternParser.parseByOneWithDelimeter(data, 
                    regex, Paragraph.class);
        } catch (InstantiationException | IllegalAccessException ex) {
            LOGGER.info("Some error occured");
            LOGGER.error("InstantiationExc or IllegalAccessExc", ex);
        }
        this.setComponents(paragraph);
        this.parseChildren();
    }  
}
