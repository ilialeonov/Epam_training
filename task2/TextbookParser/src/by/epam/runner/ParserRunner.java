/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.runner;

import by.epam.exception.EmptyFileException;
import by.epam.exception.NoParagraphsException;
import by.epam.exception.NoSentencesException;
import by.epam.exception.NoSymbolsException;
import by.epam.exception.NoWordsException;
import by.epam.entity.parser.ParagraphParser;
import by.epam.entity.parser.Parser;
import by.epam.entity.parser.SentenceParser;
import by.epam.entity.parser.TextBookParser;
import by.epam.entity.parser.TextParser;
import by.epam.service.WorkService;
import by.epam.service.Restorer;
import by.epam.util.Reader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.apache.log4j.Logger;


/**
 *
 * @author Администратор
 */
public class ParserRunner { 
    private static final Logger LOGGER = Logger.getLogger(ParserRunner.class);
    public static void main(String[] args) {
        String outputPath = "output.txt";
        String dataPath = "task_2_data.txt";
        
        List<String> words = null;
        LOGGER.info("Get text from file" + dataPath);
        LOGGER.debug("Get text");
        StringBuilder text = Reader.readFromFile(dataPath);
        LOGGER.info("text is:");
        LOGGER.info(text);
        
        try (PrintWriter output = new PrintWriter(
                new BufferedWriter(new FileWriter(outputPath)))){
            LOGGER.debug("parsing to words");
            Parser tbParser = new TextBookParser();
            tbParser.setData(text.toString());
            tbParser.setPrinter(output);
            Parser tParser = new TextParser();
            Parser parParser = new ParagraphParser();
            Parser sntParser = new SentenceParser();
            tbParser.setNextParser(tParser);
            tParser.setNextParser(parParser);
            parParser.setNextParser(sntParser);
            try {
                LOGGER.info("Text parsed to words");
                words = tbParser.parse();
                for(String word : words) {
                    LOGGER.info(word);
                }
            } catch (EmptyFileException ex) {
                LOGGER.info("File is empty");
                LOGGER.error("An empty file", ex);
            } catch (NoParagraphsException ex) {
                LOGGER.info("There are no paragraphs");
                LOGGER.error("No paragraphs exception", ex);
            } catch (NoSentencesException ex) {
                LOGGER.info("There are no sentences");
                LOGGER.error("No sentences exception", ex);
            } catch (NoWordsException ex) {
                LOGGER.info("There are no words");
                LOGGER.error("No words exception", ex);
            } catch (NoSymbolsException ex) {
                LOGGER.info("There are no symbols");
                LOGGER.error("No symbols exception", ex);
            }
        } catch (IOException ex) {
            LOGGER.info("Access to file exception");
            LOGGER.error("Access exception", ex);
        }
        
        try {
            LOGGER.info("Restoring text with parsed words "
                    + "and output file" + outputPath);
            LOGGER.debug("restoring file");
            StringBuilder restoredText = Restorer.restoreText(words, outputPath);
            LOGGER.info("restored text:");
            LOGGER.info(restoredText);
        } catch (FileNotFoundException ex) {
            LOGGER.info("File was not found by this path");
            LOGGER.error("File was not found", ex);
        }
        
        String outputForWords = "outputForWords.txt";
        
        WorkService workServ = new WorkService();
        LOGGER.debug("Setting data for sorting words");
        workServ.setData(words.toArray(new String[words.size()]));
        LOGGER.debug("Sorting words");
        workServ.sortWords();
        try {
            LOGGER.debug("sorted");
            workServ.sendInSource(outputForWords);
            LOGGER.info("Output folder is " + outputForWords);
        } catch (IOException ex) {
            LOGGER.info("Output folder access error has occured");
            LOGGER.error("Output error", ex);            
        }
        LOGGER.info("Sorting successfully done in file");
    } 
}