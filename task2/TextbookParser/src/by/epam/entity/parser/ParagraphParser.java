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
public class ParagraphParser extends AbstractParser{
    private final Logger LOGGER = Logger.getLogger(ParagraphParser.class); 
    
    List<String> sentences;

    
    public ParagraphParser() {
    }

    public ParagraphParser(String data) {
        super(data);
        this.data = data;
    }

    public ParagraphParser(String data, Parser nextParser) {
        super(data, nextParser);
        this.data = data;
        this.nextParser = nextParser;
    }

    @Override
    public List<String> parse() throws EmptyFileException, 
            NoParagraphsException, NoSentencesException, 
            NoWordsException, NoSymbolsException{
        
        if (data.isEmpty()) {
            throw new NoSentencesException();
        }
        
        sentences = new ArrayList();
        
        LOGGER.debug("Start of parsing ");
        LOGGER.info("Parsing to sentences");   
        
        final String REGEX = ".+?[a-zA-Z>?][.!:;]";
        
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(data);
        
        if(nextParser != null) {
            nextParser.setPrinter(this.getPrinter());
            while(matcher.find()) {
                try {
                    String sentence = matcher.group();
                    List<String> words = nextParser.setData(sentence).parse();
                    sentences.addAll(words);
                } catch (NoWordsException ex) {
                    
                }
            }
        } else {
            while(matcher.find()) {
                String sentence = new String(matcher.group());
                sentences.add(sentence);
                this.getPrinter().print("//skipped//");
            }
        }
        return sentences;        
    }
    
}
