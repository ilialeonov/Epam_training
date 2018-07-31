/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import org.apache.log4j.Logger;


public class WorkService {
    private final Logger LOGGER = Logger.getLogger(WorkService.class);
    private String[] words;
    
    public WorkService() {
        
    }
    
    public void setData(String[] words) {
        this.words = words;
    }
    
    public void sortWords(){
        Arrays.parallelSort(words, (a, b) -> {
            return Character.toLowerCase(a.charAt(0)) 
                    - Character.toLowerCase(b.charAt(0));
        });
    }
    public void sendInSource(String path) throws IOException{
        try (PrintWriter out = 
                new PrintWriter(new BufferedWriter(new FileWriter(path)))){    
            out.printf("\t%s ", words[0]);
            for(int i = 1; i < words.length; i++){
                if(Character.toLowerCase(words[i].charAt(0)) 
                        != Character.toLowerCase(words[i-1].charAt(0))) {
                    out.printf("%n\t%s ", words[i]);
                } else {
                    out.printf("%s ", words[i]);
                }
            }      
        }
    }
}
