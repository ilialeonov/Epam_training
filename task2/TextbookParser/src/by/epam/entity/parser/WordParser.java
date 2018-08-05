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
public class WordParser extends AbstractParser{
    private final static Logger LOGGER = Logger.getLogger(WordParser.class); 
    
    private List<String> symbols = new ArrayList();

    public WordParser() {
    }

    public WordParser(String data) {
        super(data);
        this.data = data;
    }

    public WordParser(String data, Parser nextParser) {
        super(data, nextParser);
        this.data = data;
        this.nextParser = nextParser;
    }

    @Override
    public List<String> parse() throws EmptyFileException, 
            NoParagraphsException, NoSentencesException, 
            NoWordsException, NoSymbolsException{
        
        LOGGER.debug("begining of parsing to symbols");
        LOGGER.debug("this is last parser");
                
        if (data.isEmpty()) {
            throw new NoSymbolsException();
        }
        
        final String REGEX = "\\w";
        
        Pattern pattern = Pattern.compile(REGEX);
        String[] splited = pattern.split(data);
        Matcher matcher = pattern.matcher(data);
        
        while(matcher.find()) {
            String symbol = matcher.group();
            symbols.add(symbol);
            this.getPrinter().print("//skipped//");
            }
        LOGGER.debug("parsing to symbols is finished");
        return symbols;        
    }
}    