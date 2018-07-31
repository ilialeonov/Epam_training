/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.runner;

import by.epam.exception.NoSuchLevelException;
import by.epam.service.ParseService;
import by.epam.service.WorkService;
import by.epam.util.PatternParser;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;


/**
 *
 * @author Администратор
 */
public class ParserRunner { 
    private static final Logger LOGGER = Logger.getLogger(ParserRunner.class);
    public static void main(String[] args) {
        String output = "output.txt";
        String depthLevel = "word";
        ParseService service = new ParseService();
        service.setSource("task_2_data.txt");
        
        try {
            LOGGER.info("Depth level of parsing is " + depthLevel);
            service.setDepthLevel(depthLevel);
            service.parseToTextListing();
        } catch (NoSuchLevelException ex) {
            LOGGER.info("You have entered bad depth level");
            LOGGER.error("bad depth level", ex);
        }
  
        List<String> arrayList = service.getResult();
        
        String[] array = arrayList.toArray(new String[arrayList.size()]);
        
        WorkService workServ = new WorkService();
        workServ.setData(array);
        workServ.sortWords();
        try {
            workServ.sendInSource(output);
            LOGGER.info("Output folder is " + output);
        } catch (IOException ex) {
            LOGGER.info("Output folder access error has occured");
            LOGGER.error("Output error", ex);            
        }
        LOGGER.info("Parsing successfully done");
    } 
}
