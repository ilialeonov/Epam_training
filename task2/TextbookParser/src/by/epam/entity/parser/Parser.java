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
public interface Parser {
    public Parser setData(String data);
    public String getData();
    public List<String> parse() throws NoParagraphsException, 
            EmptyFileException, NoSentencesException, NoWordsException, 
            NoSymbolsException;
    public void setPrinter(PrintWriter out);
    public PrintWriter getPrinter();
    public Parser getNextParser();
    public void setNextParser(Parser nextParser);
}
