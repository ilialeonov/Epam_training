/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.util;

import by.epam.entity.CandiesEnum;
import by.epam.entity.Candy;
import by.epam.entity.ChocoType;
import by.epam.entity.Ingridient;
import by.epam.entity.SweetType;
import by.epam.entity.Value;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.log4j.Logger;

/**
 *
 * @author uks50
 */
public class CandiesStaxBuilder extends AbstractCandiesBuilder{
    
    private static final Logger LOG = Logger.getLogger(CandiesStaxBuilder.class);
    
    private XMLInputFactory inputFactory = XMLInputFactory.newInstance();

    public CandiesStaxBuilder() {
    }

    public CandiesStaxBuilder(Set<Candy> candies) {
        super(candies);
    }

    @Override
    public void buildSetCandies(String fileName) {
        FileInputStream inputStream = null;
        XMLStreamReader reader = null;
        String name;
        
        try {
            inputStream = new FileInputStream(new File(fileName));
            reader = inputFactory.createXMLStreamReader(inputStream);
            
            while (reader.hasNext()) {
                int type = reader.next();
                
                if (type == XMLStreamReader.START_ELEMENT) {
                    name = reader.getLocalName();
                    
                    if (CandiesEnum.valueOf(
                            name.toUpperCase()).equals(CandiesEnum.CANDY)) {
                        Candy candy = buildCandy(reader);
                        candies.add(candy);
                    }
                }
            }
        } catch (XMLStreamException ex) {
            LOG.info("error in XML");
            LOG.error("error in buildSetCandies", ex);
        } catch (FileNotFoundException ex) {
            LOG.error("error with file access in buildSetCandies", ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                LOG.error("file can't be closed", ex);
            }
        }
    }

    private Candy buildCandy(XMLStreamReader reader) throws XMLStreamException{
        Candy candy = new Candy();
        
        String name;
        while(reader.hasNext()) {
            int type = reader.next();
            switch(type) {
                
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    
                    switch(CandiesEnum.valueOf(name.toUpperCase())) {
                        
                        case NAME:
                            candy.setName(getXMLText(reader));
                            break;
                        
                        case ENERGY:
                            String energyUnit = reader.getAttributeValue(0);
                            if(energyUnit != null) {
                                candy.setEnergyUnit(energyUnit);
                            }
                            candy.setEnergy(Integer.parseInt(getXMLText(reader)));
                            break;
                       
                        case TYPE:
                            String candyType = getXMLText(reader);
                            SweetType sweetType 
                                    = SweetType.valueOf(candyType.toUpperCase());
                            candy.setType(sweetType);
                            break;
                        
                        case INGRIDIENTS:
                            candy.setIngridient(getXMLIngridient(reader));
                            break;
                        
                        case VALUE:
                            candy.setValue(getXMLValue(reader));
                            break;
                        
                        case PRODUCTION:
                            candy.setProducer(getXMLText(reader));
                            break;
                            
                        case DELIVERYTIME:
                            XMLGregorianCalendar xcal;
                            try {
                                xcal = DatatypeFactory.newInstance()
                                        .newXMLGregorianCalendar(getXMLText(reader));
                            } catch (DatatypeConfigurationException ex) {
                                throw new XMLStreamException(ex);
                            }
                            Date date = xcal.toGregorianCalendar().getTime();
                            candy.setDeliveryTime(date);
                            break;
                        }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getName().getLocalPart();
                    if (CandiesEnum.valueOf(name.toUpperCase())
                            .equals(CandiesEnum.CANDY)) {
                        return candy;
                    }
                break;
                }
            }
    throw new XMLStreamException("unknown element in tag Candy");
    }

    private Ingridient getXMLIngridient(XMLStreamReader reader) 
            throws XMLStreamException{
        Ingridient ingridient = new Ingridient();
        int type;
        String name;
        
        String typeChoc = reader.getAttributeValue(0);
        if (typeChoc != null) {
            ChocoType chocoType = ChocoType.valueOf(typeChoc.toUpperCase());
            ingridient.setType(chocoType); 
        }
        
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch(CandiesEnum.valueOf(name.toUpperCase())) {
                        
                        case WATER:
                            String waterUnit = reader.getAttributeValue(0);
                            if(waterUnit != null) {
                                ingridient.setWaterUnit(waterUnit);
                            }
                            ingridient.setWater(Integer.parseInt(getXMLText(reader)));
                            break;
                        
                        case SUGAR:
                            String sugarUnit = reader.getAttributeValue(0);
                            ingridient.setSugarUnit(sugarUnit);
                            ingridient.setSugar(Integer.parseInt(getXMLText(reader)));
                            break;
                        
                        case FRUCTOSE:
                            String fructoseUnit = reader.getAttributeValue(0);
                            ingridient.setFructoseUnit(fructoseUnit);
                            ingridient.setFructose(Integer.parseInt(getXMLText(reader)));
                            break;
                        
                        case VANILIN:
                            String vanilinUnit = reader.getAttributeValue(0);
                            ingridient.setVanilinUnit(vanilinUnit);
                            ingridient.setVanilin(Integer.parseInt(getXMLText(reader)));
                            break;    
                        }
                break;
                    
                case XMLStreamConstants.END_ELEMENT:  
                    name = reader.getLocalName();
                    if (CandiesEnum.valueOf(name.toUpperCase())
                            .equals(CandiesEnum.INGRIDIENTS)) {
                        return ingridient;
                    }
                break;
            }
        }
        throw new XMLStreamException("unknown element in tag Ingridient");
    }

    private Value getXMLValue(XMLStreamReader reader) 
            throws XMLStreamException{
        Value value = new Value();
        int type;
        String name;
        
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch(CandiesEnum.valueOf(name.toUpperCase())) {
                        
                        case PROTEIN:
                            String proteinUnit = reader.getAttributeValue(0);
                            value.setProteinUnit(proteinUnit);
                            value.setProtein(Integer.parseInt(getXMLText(reader)));
                            break;
                        case FAT:
                            String fatUnit = reader.getAttributeValue(0);
                            value.setFatUnit(fatUnit);
                            value.setFat(Integer.parseInt(getXMLText(reader)));
                            break;
                        case CARBOHYDRATE:
                            String carbohydrateUnit = reader.getAttributeValue(0);
                            value.setCarbohydrateUnit(carbohydrateUnit);
                            value.setCarbohydrate(Integer.parseInt(getXMLText(reader)));
                            break;
                    }
                break;
                    
                case XMLStreamConstants.END_ELEMENT:  
                    name = reader.getLocalName();
                    if (CandiesEnum.valueOf(name.toUpperCase())
                            .equals(CandiesEnum.VALUE)) {
                        return value;
                    }
                break;
            }
        }
        
        throw new XMLStreamException("unknown element in tag Value");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException{
        String text = null;
        if(reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}