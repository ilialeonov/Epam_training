/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.util;

import by.epam.entity.Candy;
import by.epam.exception.NoSourceException;
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
    public void buildSetCandies(String fileName) throws NoSourceException {
        try {
            if (fileName == null) {
                throw new NoSourceException();
            }
            XMLReader reader = XMLReaderFactory.createXMLReader();
            CandiesHandler handler = new CandiesHandler(candies);
            reader.setContentHandler(handler);
            reader.setFeature("http://apache.org/xml/features/validation/schema", true);
            reader.parse(fileName);
        } catch (SAXException ex) {
            LOG.error("SAXException at buildSetCandies(fileName)", ex);
        } catch (IOException ex) {
            LOG.error("IOException with access to file", ex);
        } 
        
    }
    
}
