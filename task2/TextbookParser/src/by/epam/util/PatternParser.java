/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.util;

import by.epam.entity.Component;
import by.epam.entity.ComponentSkeleton;
import by.epam.entity.Listing;
import by.epam.entity.Text;
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
    
    public static List<Component> parseByTwoSaveDelimeter(String data, String regex, 
            Class<? extends Component> firstCl, 
            Class<? extends Component> secondCl) 
            throws InstantiationException, IllegalAccessException{
        
            List<Component> componentList = new ArrayList();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(data);
            Component component;
            
            String[] textArray = pattern.split(data);
            StringBuilder textLine = new StringBuilder("");
            
            int counter = 0;
            String matcherText = "";
                    
            // summing elements from split and from match
            while(matcher.find()) {
                int start = matcher.start();
                matcherText = matcher.group();
                

                if(textLine.length() == start) {
                    component = secondCl.newInstance();
                    component.setData(matcherText);
                    componentList.add(component);
                    textLine.append(matcherText);
                } else {
                    component = firstCl.newInstance();
                    component.setData(textArray[counter]);
                    componentList.add(component);
                    textLine.append(textArray[counter]);
                    counter++;
                    component = secondCl.newInstance();
                    component.setData(matcherText);
                    componentList.add(component);
                    textLine.append(matcherText);
                } 
            }
            if(textArray.length - 1 == counter) {
                component = firstCl.newInstance();
                component.setData(textArray[counter]);
                componentList.add(component);
                textLine.append(textArray[counter]);
            }            
        return componentList;    
    }
    
    public static List<Component> parseByOneNoDelimeter(String data, String regex, 
            Class<? extends Component> cl) 
            throws InstantiationException, IllegalAccessException{
        List<Component> componentList = new ArrayList();
        String[] textArray;
        Component component;
                
        Pattern pattern = Pattern.compile(regex);
        textArray = pattern.split(data);
        for (String x : textArray) {
            if(x.length()>0){
                component = cl.newInstance();
                component.setData(x);
                componentList.add(component);
            }
        }
        return componentList;
    }
    
    public static List<Component> parseByOneWithDelimeter(String data, 
            String regex, Class<? extends Component> cl) 
            throws InstantiationException, IllegalAccessException{
        List<Component> componentList = new ArrayList();
        String[] textArray;
        Component component;
                
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()) {
            component = cl.newInstance();
            component.setData(matcher.group());
            componentList.add(component);
            
        }
        return componentList;
    }    
}
