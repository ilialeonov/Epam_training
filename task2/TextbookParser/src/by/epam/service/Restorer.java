/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.service;

import by.epam.entity.parser.WordParser;
import by.epam.util.Reader;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

public class Restorer {
    
    private final static Logger LOGGER = Logger.getLogger(WordParser.class); 
    
    public static StringBuilder restoreText(
            List<String> parsedElements, String path) throws FileNotFoundException{
        
        LOGGER.debug("begining to restore file");
        int i = 0;
        final String REGEX = "//skipped//";
        
        StringBuilder text = Reader.readFromFile(path);
        StringBuilder restoredText = new StringBuilder("");
        
        Pattern pattern = Pattern.compile(REGEX);
        String[] notMatchedText = pattern.split(text);
        Matcher matcher = pattern.matcher(text);
        while(matcher.find()) {
            restoredText.append(parsedElements.get(i)).append(notMatchedText[i+1]);
            i++;
        }
        LOGGER.debug("restoring is finished");
        return restoredText;
    }
}
