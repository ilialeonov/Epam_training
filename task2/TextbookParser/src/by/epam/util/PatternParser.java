/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.util;

import by.epam.entity.Component;
import by.epam.entity.Composite;
import by.epam.entity.Leaf;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

public class PatternParser {
    private final Logger LOGGER = Logger.getLogger(PatternParser.class); 
    private List<Component> component;

    private Pattern pattern;
    private Matcher matcher;     
    private String data;

    public PatternParser() {
    }
    
    protected PatternParser(String data) {
        this.data = data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
    
    public static List<Component> parseSaveAll(String data, String regex){
            
        List<Component> componentList = new ArrayList();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
            
        String[] textArray = pattern.split(data);
        StringBuilder textLine = new StringBuilder("");
            
        int counter = 0;
        String matcherText = "";
                    
        // summing elements from split and from match
        while(matcher.find()) {
            int start = matcher.start();
            matcherText = matcher.group();
            if(textLine.length() == start) {
                componentList.add(new Leaf(matcherText));
                textLine.append(matcherText);
            } else {
                componentList.add(new Composite(textArray[counter]));
                textLine.append(textArray[counter]);
                counter++;
                componentList.add(new Leaf(matcherText));
                textLine.append(matcherText);
            } 
        }
        if(textArray.length - 1 == counter) {
            componentList.add(new Composite(textArray[counter]));
            textLine.append(textArray[counter]);
        }            
        return componentList;    
    }
    
    public static List<Component> parseSplitByDelimeter(String data,
            String regex){
        
        List<Component> componentList = new ArrayList();
        String[] textArray;
                
        Pattern pattern = Pattern.compile(regex);
        textArray = pattern.split(data);
        for (String x : textArray) {
            if(!x.isEmpty()){
                componentList.add(new Composite(x));
            }
        }
        return componentList;
    }
    
    public static List<Component> parseGetMatched(String data,String regex){
        List<Component> componentList = new ArrayList();
        String[] textArray;
                
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()) {
            componentList.add(new Composite(matcher.group()));  
        }
        return componentList;
    }    
}
