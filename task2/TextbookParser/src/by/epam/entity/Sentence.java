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
 * @author uks50
 */
public class Sentence extends CompoundComponentSkeleton{
    private final Logger LOGGER = Logger.getLogger(Sentence.class); 

    public Sentence() {
    }

    public Sentence(String data) {
        super(data);
    }

    @Override
    public void parse() {
        LOGGER.debug("enters to text parse()");
        List<Component> sentence = null;
        String regex = "[a-zA-Z]++";
        String data = this.getData();
        try {
            //System.out.println("data" + data);
            sentence = PatternParser.parseByTwoSaveDelimeter(data, 
                    regex, PunctuationAndOther.class, Word.class);
        } catch (InstantiationException | IllegalAccessException ex) {
            LOGGER.info("Some error occured");
            LOGGER.error("InstantiationExc or IllegalAccessExc", ex);
        }
        this.setComponents(sentence);
        for (Component x : sentence) {
            System.out.println("+++++++++++++++++++");
            System.out.println(x.getData());
        }
    } 
}
