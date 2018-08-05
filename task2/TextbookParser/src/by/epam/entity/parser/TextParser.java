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
public class TextParser extends AbstractParser{
    private final Logger LOGGER = Logger.getLogger(TextParser.class); 
    
    private List<String> paragraphs;

    public TextParser() {
    }

    public TextParser(String data) {
        super(data);
        this.data = data;
    }

    public TextParser(String data, Parser nextParser) {
        super(data, nextParser);
        this.data = data;
        this.nextParser = nextParser;
    }

    @Override
    public List<String> parse() throws EmptyFileException, 
            NoParagraphsException, NoSentencesException, 
            NoWordsException, NoSymbolsException{
        
        LOGGER.debug("Parsing to paragraphs ");    
        
        if (data.isEmpty()) {
            throw new NoParagraphsException();
        }
        
        paragraphs = new ArrayList();   
        
        final String REGEX = "\n+";
        
        Pattern pattern = Pattern.compile(REGEX);
        String[] splited = pattern.split(data);
        Matcher matcher = pattern.matcher(data);
        
        if(nextParser != null) {
            LOGGER.debug("next parser is " + nextParser);
            nextParser.setPrinter(this.getPrinter());
            for (int i = 0; i < splited.length; i++) {
                List<String> paragraph = nextParser.setData(splited[i]).parse();
                paragraphs.addAll(paragraph);
                this.getPrinter().print("\n");
            }
        } else {
            LOGGER.debug("last parser");
            for (int i = 0; i < splited.length; i++) {
                String paragraph = new String(splited[i]);
                paragraphs.add(paragraph);
                this.getPrinter().print("//skipped//");
                if(matcher.find()){
                    this.getPrinter().print(matcher.group());
                }
            }
        }
        LOGGER.debug("parsing to paragraphs is finished");
        return paragraphs;             
    }
    
}
