/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.sevice;

import by.epam.entity.Candy;
import java.io.IOException;
import java.util.Set;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author Администратор
 */
public class CandiesSaxBuilder extends AbstractCandiesBuilder{
    private static final Logger LOG = Logger.getLogger(CandiesSaxBuilder.class);
    
    public CandiesSaxBuilder() {
        super();
    }

    public CandiesSaxBuilder(Set<Candy> candies) {
        super(candies);
        
    }

    @Override
    public void buildSetCandies(String fileName) {
        try {
            XMLReader reader = XMLReaderFactory.createXMLReader();
            CandiesHandler handler = new CandiesHandler(candies);
            reader.setContentHandler(handler);
            reader.setFeature("http://apache.org/xml/features/validation/schema", true);
            reader.parse(fileName);
        } catch (SAXException ex) {
            LOG.info("exception parsing file has occured");
            LOG.error("SAXException at buildSetCandies(fileName)", ex);
        } catch (IOException ex) {
            LOG.info("error with access to file");
            LOG.error("IOException with access to file", ex);
        } 
        
    }
    
}
