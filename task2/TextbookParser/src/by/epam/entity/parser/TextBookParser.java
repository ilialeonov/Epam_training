/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.entity.parser;

import by.epam.exception.EmptyFileException;
import by.epam.exception.NoParagraphsException;
import by.epam.exception.NoSentencesException;
import by.epam.exception.NoSymbolsException;
import by.epam.exception.NoWordsException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 *
 * @author uks50
 */
public class TextBookParser extends AbstractParser{
    private final Logger LOGGER = Logger.getLogger(TextBookParser.class); 
    
    private List<String> textbook;
    
    public TextBookParser() {
        super();
    }

    protected TextBookParser(String data) {
        super(data);
    }
    
    protected TextBookParser(String data, Parser nextParser) {
        super(data, nextParser);
    }
        
    public List<String> parse() throws EmptyFileException, 
            NoParagraphsException, NoSentencesException, 
            NoWordsException, NoSymbolsException{
        
        LOGGER.debug("begining to parse on listing and text");
        
        textbook = new ArrayList();
        
        if (data.isEmpty()) {
            throw new EmptyFileException();
        }        
        
        final String REGEX = "(1 package .+;(\\n[0-9]+.*)+)\\n"
                + "|((.+\\(.*\\);(.*)?\n)+)";
        
        Pattern pattern = Pattern.compile(REGEX);
        String[] splited = pattern.split(data);
        Matcher matcher = pattern.matcher(data);
        
        if(nextParser != null) {  
            LOGGER.debug("next parser is " + nextParser);
            nextParser.setPrinter(this.getPrinter());
            for (int i = 0; i < splited.length; i++) {
                List<String> text = nextParser.setData(splited[i]).parse();
                textbook.addAll(text);
                if(matcher.find()){
                    this.getPrinter().print(matcher.group());
                }
            }
        } else {
            LOGGER.debug("last parser");
            for (int i = 0; i < splited.length; i++) {
                String text = new String(splited[i]);
                textbook.add(text);
                this.getPrinter().print("//skipped//");
                if(matcher.find()){
                    this.getPrinter().print(matcher.group());
                }
            }
        }
        LOGGER.debug("parsing on text and listing is finished");
        return textbook;
    } 
}
