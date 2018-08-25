/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.util;

import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 *
 * @author Администратор
 */
public class SchemaValidator {
    private final static Logger LOG = Logger.getLogger(SchemaValidator.class);
    
    static String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
    static SchemaFactory factory = SchemaFactory.newInstance(language);
    
    public static void validate(String filePath, String schemaPath) {
        try {
            String language = XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI;
            Schema schema = factory.newSchema(new File(schemaPath));
            Validator validator = schema.newValidator();
            Source source = new StreamSource(new File(filePath));
            validator.validate(source);
            LOG.info("file is valid");
        } catch (SAXException ex) {
            LOG.error("Not valid exception has occured", ex);
        } catch (IOException ex) {
            LOG.error("File access error", ex);
        }
        
    }
}
