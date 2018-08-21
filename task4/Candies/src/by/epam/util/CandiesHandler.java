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
import by.epam.exception.NoSuchSweetTypeException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Администратор
 */
class CandiesHandler extends DefaultHandler{
    private static final Logger LOG = Logger.getLogger(CandiesHandler.class);
    
    private Set<Candy> candies;
    private Candy currentCandy;
    private CandiesEnum currentEnum;
    private Attributes attributes;
    private String attribute;
    private Ingridient ingridient;
    private Value value;
    
    public CandiesHandler() {
    }

    public CandiesHandler(Set<Candy> candies) {
        this.candies = candies;
    }

    public Set<Candy> getCandies() {
        return candies;
    }

    public void setCandies(Set<Candy> candies) {
        this.candies = candies;
    }
    
    @Override
    public void startElement(String uri, String localName, 
            String qName, Attributes attributes){
        if(CandiesEnum.valueOf(localName.toUpperCase())
                .equals(CandiesEnum.CANDY)) {
            currentCandy = new Candy();
        } else if (CandiesEnum.valueOf(localName.toUpperCase())
                .equals(CandiesEnum.INGRIDIENTS)) {
            ingridient = new Ingridient();
            if(attributes.getValue(0) != null) {
                attribute = attributes.getValue(0);
                ChocoType chocoType 
                        = ChocoType.valueOf(attribute.toUpperCase());
                if (chocoType.equals(ChocoType.BITTER) 
                        | chocoType.equals(ChocoType.MILK) 
                        | chocoType.equals(ChocoType.RUBY) 
                        | chocoType.equals(ChocoType.WHITE))
                ingridient.setType(chocoType);
            }
        } else if (CandiesEnum.valueOf(localName.toUpperCase())
                .equals(CandiesEnum.VALUE)){
            value = new Value();
        } else{
            currentEnum = CandiesEnum.valueOf(localName.toUpperCase());
            this.attributes = attributes;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String s = new String(ch, start, length).trim();
        if(s.isEmpty()) {
            return;
        }
        if (currentEnum != null) {
            switch (currentEnum) {
                case NAME: 
                    currentCandy.setName(s);
                    break;
                case ENERGY:
                    int l = Integer.parseInt(s);
                    currentCandy.setEnergy(l);
                    attribute = attributes.getValue(0);
                    currentCandy.setEnergyUnit(attribute);
                    break;
                case TYPE:
                    switch (SweetType.valueOf(s.toUpperCase())) {
                        case CARAMEL:
                            currentCandy.setType(SweetType.CARAMEL);
                            break;
                        case CHOCOLATENUTS:
                            currentCandy.setType(SweetType.CHOCOLATENUTS);
                            break;
                        case CHOCOLATESOUFFLE:
                            currentCandy.setType(SweetType.CHOCOLATESOUFFLE);
                            break;
                        default:
                            throw new SAXException(new NoSuchSweetTypeException());
                    } break;
                case WATER:
                    ingridient.setWater(Integer.parseInt(s));
                    attribute = attributes.getValue(0);
                    ingridient.setWaterUnit(attribute);
                    break;
                case SUGAR:
                    ingridient.setSugar(Integer.parseInt(s));
                    attribute = attributes.getValue(0);
                    ingridient.setSugarUnit(attribute);
                    break;
                case FRUCTOSE:
                    ingridient.setFructose(Integer.parseInt(s));
                    attribute = attributes.getValue(0);
                    ingridient.setFructoseUnit(attribute);
                    break;
                case VANILIN:
                    ingridient.setVanilin(Integer.parseInt(s));
                    attribute = attributes.getValue(0);
                    ingridient.setVanilinUnit(attribute);
                    break;
                case PROTEIN:
                    value.setProtein(Integer.parseInt(s));
                    attribute = attributes.getValue(0);
                    value.setProteinUnit(attribute);
                    break;
                case FAT:
                    value.setFat(Integer.parseInt(s));
                    attribute = attributes.getValue(0);
                    value.setFatUnit(attribute);
                    break;
                case CARBOHYDRATE:
                    value.setCarbohydrate(Integer.parseInt(s));
                    attribute = attributes.getValue(0);
                    value.setCarbohydrateUnit(attribute);
                    break;
                case PRODUCTION:
                    currentCandy.setProducer(s);
                    break;
                case DELIVERYTIME:
                    Date date = null;
                    try {
                    XMLGregorianCalendar xcal = DatatypeFactory.newInstance()
                                           .newXMLGregorianCalendar(s);
                    date = xcal.toGregorianCalendar().getTime();
                        currentCandy.setDeliveryTime(date);
                    } catch (DatatypeConfigurationException ex){
                        throw new SAXException(ex);
                    }
                    break;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if(CandiesEnum.valueOf(localName.toUpperCase())
                .equals(CandiesEnum.CANDY)) {
            candies.add(currentCandy);
        } else if (CandiesEnum.valueOf(localName.toUpperCase())
                .equals(CandiesEnum.INGRIDIENTS)) {
            currentCandy.setIngridient(ingridient);
        } else if (CandiesEnum.valueOf(localName.toUpperCase())
                .equals(CandiesEnum.VALUE)) {
            currentCandy.setValue(value);
        }
    }

}
