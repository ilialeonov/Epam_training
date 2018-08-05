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
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author uks50
 */
public abstract class AbstractParser implements Parser{
    protected String data;
    protected Parser nextParser;
    private PrintWriter out;
    
    public AbstractParser() {
    }

    protected AbstractParser(String data) {
        this.data = data;
    }

    protected AbstractParser(String data, Parser nextParser) {
        this.data = data;
        this.nextParser = nextParser;
    }
    
    @Override
    public Parser setData(String data) {
        this.data = data;
        return this;
    }

    @Override
    public String getData() {
        return data;
    }
    
    public Parser getNextParser() {
        return nextParser;
    }

    public void setNextParser(Parser nextParser) {
        this.nextParser = nextParser;
    }
    
    public void setPrinter(PrintWriter out){
        this.out = out;
    } 
    
    public PrintWriter getPrinter(){
        return out;
    }

    @Override
    public abstract List<String> parse() throws NoParagraphsException,  
            EmptyFileException, NoSentencesException, NoWordsException, NoSymbolsException;
    
}
