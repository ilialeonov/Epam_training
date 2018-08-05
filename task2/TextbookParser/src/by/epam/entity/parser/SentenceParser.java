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

 public class SentenceParser extends AbstractParser{
    private final Logger LOGGER = Logger.getLogger(SentenceParser.class); 

    private List<String> words;

    public SentenceParser() {
    }

    public SentenceParser(String data) {
        super(data);
        this.data = data;
    }

    public SentenceParser(String data, Parser nextParser) {
        super(data, nextParser);
        this.data = data;
        this.nextParser = nextParser;
    }

    @Override
    public List<String> parse()  throws EmptyFileException, 
            NoParagraphsException, NoSentencesException, 
            NoWordsException, NoSymbolsException{
        
        LOGGER.debug("begining parsing on words");
        
        words = new ArrayList();
        if (data.isEmpty()) {
            throw new NoWordsException();
        }
        
        LOGGER.debug("Start of parsing ");
        LOGGER.info("Parsing to words");   
        
        final String REGEX = "(\\s+)|(\\p{Punct}\\s*)|(\\d)";
        
        Pattern pattern = Pattern.compile(REGEX);
        String[] splited = pattern.split(data);
        Matcher matcher = pattern.matcher(data);
        
        if(nextParser != null) {
            LOGGER.debug("next parser is" + nextParser);
            nextParser.setPrinter(this.getPrinter());
            for (int i = 0; i < splited.length; i++) {
                words.addAll(nextParser.setData(splited[i]).parse());
                if(matcher.find()){
                    this.getPrinter().println(matcher.group());
                }
            }
        } else {
            LOGGER.debug("last parser");
            for (int i = 0; i < splited.length; i++) {
                String word = new String(splited[i]);
                if(!word.isEmpty()) {
                    words.add(word);
                    this.getPrinter().print("//skipped//");  
                }
                if(matcher.find()){
                    this.getPrinter().print(matcher.group());
                }
            }
        }
        LOGGER.debug("parsing to words is finished");
        return words; 
    }
}
