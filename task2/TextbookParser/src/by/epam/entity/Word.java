/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 *
 * @author uks50
 */
public class Word extends CompoundComponentSkeleton{
    private final Logger LOGGER = Logger.getLogger(Text.class); 
    private List<Component> word = new ArrayList<>();

    private Pattern pattern;
    private Matcher matcher;    
    private String data;

    public Word() {
    }

    public Word(String data) {
        super(data);
    }

    @Override
    public void parse() {
        
    }
    
}
