/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.service;

import by.epam.exception.NoSuchLevelException;
import by.epam.entity.Component;
import by.epam.entity.Composite;
import by.epam.entity.LevelDepth;
import by.epam.exception.EmptyFileException;
import by.epam.exception.NoParagraphsException;
import by.epam.exception.NoSentencesException;
import by.epam.exception.NoSymbolsException;
import by.epam.exception.NoWordsException;
import by.epam.util.PatternParser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;

public class ParseService {
    private final Logger LOGGER = Logger.getLogger(ParseService.class);
    private BufferedReader in;
    private StringBuilder data;
    LevelDepth level;
    private List<String> result = new ArrayList();
    
    public ParseService() {  
    }
    
    public void setSource(String path) throws EmptyFileException {
        try {
            in = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException ex) {
            LOGGER.info("File has not been found");
            LOGGER.error("File not found", ex);
        }
        initData();
        LOGGER.debug("sets datas for parsing");
    }  
    
    private void initData() throws EmptyFileException {
        LOGGER.debug("Enters process of initing data");
        data = new StringBuilder();
        String line;
        try {
            while((line = in.readLine()) != null){
                data.append(line).append("\n");
            }
            if (data.length() == 0){
                throw new EmptyFileException();
            }
            LOGGER.debug("Exits process of initing data for Textbook");
        } catch (IOException ex) {
            LOGGER.info("Some error with access to file");
            LOGGER.error("IOException has occured", ex);
        }
    }
    
    public void setDepthLevel(String level) throws NoSuchLevelException{
        boolean flag = false;
        LevelDepth[] values = LevelDepth.values();
        for (LevelDepth value: values) {
            if(level.toUpperCase().equals(value.toString())){
                this.level = LevelDepth.valueOf(level.toUpperCase());
                flag = true;
            }
        }
        if(!flag) {
            throw new NoSuchLevelException();
        }
    }
    
    public List<String> getResult(){
        return result;
    }
    
    public void parseToTextListing() throws NoSuchLevelException{
        LOGGER.debug("Start of parsing");
        LOGGER.info("Parsing to text and listing");
        List<Component> textbook = null;
        String regex = "(1 package .+;(\\s[0-9]+.*)+)"
                + "|((.+\\(.*\\);(.*)?\n)+)";
        
        textbook = PatternParser.parseSaveAll(data.toString(), regex);
        
        if (level == LevelDepth.LISTING){
            for(Component x : textbook) {
                if (!(x instanceof Composite)) {
                    result.add(x.getData());
                }
            }
        } else if (level == LevelDepth.TEXT){
            for(Component x : textbook) {
                if (x instanceof Composite) {
                    result.add(x.getData());
                }
            }
        } else {
            for(Component x : textbook) {
                if (x instanceof Composite) {
                    try {
                        parseToParagraphs(x.getData());
                    } catch (NoParagraphsException ex) {
                        LOGGER.info("Can not be parsed in paragraphs");
                        LOGGER.error("Parsing in paragraphs is not allowed", ex);
                    }
                }
            }
        }
    }    
    
    public void parseToParagraphs(String text) 
            throws NoSuchLevelException, NoParagraphsException{
        
        if (text.isEmpty()) {
            throw new NoParagraphsException();
        }
        
        LOGGER.info("Parsing to paragraphs");
        
        List<Component> paragraphs = null;
        String regex = "\n";
        
        paragraphs = PatternParser.parseSplitByDelimeter(text, regex);
        
        if (level == LevelDepth.PARAGRAPH){
            for(Component x : paragraphs) {
                result.add(x.getData());
            }
        } else {
            for(Component x : paragraphs) {
                try {
                    parseToSentences(x.getData());
                } catch (NoSentencesException ex) {
                    LOGGER.info("Can not be parsed in sentences");
                    LOGGER.error("Parsing in sentences is not allowed", ex);
                }
            }
        }        

    }
    
    public void parseToSentences(String paragraph) 
            throws NoSuchLevelException, NoSentencesException{
        
        if (paragraph.isEmpty()) {
            throw new NoSentencesException();
        }
        
        LOGGER.info("Parsing to sentences");
        List<Component> sentences = null;
        String regex = ".+?[a-zA-Z>?][.!:;]";
        
        sentences = PatternParser.parseGetMatched(paragraph, regex);
        
        if (level == LevelDepth.SENTENCE){
            for(Component x : sentences) {
                result.add(x.getData());
            }
        } else {
            for(Component x : sentences) {
                try {
                    parseToWords(x.getData());
                } catch (NoWordsException ex) {
                    LOGGER.info("Can not be parsed in words");
                    LOGGER.error("Parsing in words is not allowed", ex);
                }
            }
        }        

    }    
    
    public void parseToWords(String sentence) throws NoSuchLevelException, NoWordsException{
        if (sentence.isEmpty()) {
            throw new NoWordsException();
        }
        
        LOGGER.info("Parsing to words");
        List<Component> words = null;
        String regex = "(\\s)|(\\p{Punct})|(\\d)";
        
        words = PatternParser.parseSaveAll(sentence, regex);
        
        if (level == LevelDepth.WORD){
            for(Component x : words) {
                if (x instanceof Composite) {
                    if(!x.getData().isEmpty()){
                        result.add(x.getData());
                    } 
                }
            }
        } else {
            for(Component x : words) {
                if (x instanceof Composite) {
                    try {
                        parseToSymbols(x.getData());
                    } catch (NoSymbolsException ex) {
                        LOGGER.info("Can not be parsed in symbols");
                        LOGGER.error("Parsing in symbols is not allowed", ex);
                    }
                }
            }
        }                 
    }
    
    public void parseToSymbols(String word) throws NoSuchLevelException, NoSymbolsException{
        if (word.isEmpty()) {
            throw new NoSymbolsException();
        }
        
        LOGGER.info("Parsing to symbols");
        List<Component> symbols = null;
        String regex = "\\w";
        
        symbols = PatternParser.parseGetMatched(word, regex);
        
        if (level == LevelDepth.SYMBOL){
            for(Component x : symbols) {
                result.add(x.getData());
            }
        }
    }
}
