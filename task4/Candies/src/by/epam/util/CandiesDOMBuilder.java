/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.util;

import by.epam.entity.Candy;
import by.epam.entity.ChocoType;
import by.epam.entity.Ingridient;
import by.epam.entity.SweetType;
import by.epam.entity.Value;
import java.io.IOException;
import java.util.Date;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CandiesDOMBuilder extends AbstractCandiesBuilder{
    
    private static final Logger LOG = Logger.getLogger(CandiesDOMBuilder.class);
    
    private DocumentBuilderFactory factory;
    private DocumentBuilder docBuilder = null;
    private Document doc;
    private Node nodeInCandy;
    private Node nodeInIngridient;
    private Node nodeInValue;
    
    
    {
        try {
            factory = DocumentBuilderFactory.newInstance();
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            LOG.info("error building new document");
        }
    }

    public CandiesDOMBuilder() {
    }

    public void buildSetCandies(String fileName) {
        try {
            doc = docBuilder.parse(fileName);
            Element root = doc.getDocumentElement();
            NodeList candyList = root.getElementsByTagName("candy");
            for (int i = 0; i < candyList.getLength(); i++) {
                Element candyElement = (Element) candyList.item(i);
                Candy candy = buildCandy(candyElement);
                candies.add(candy);
            }
        } catch (SAXException ex) {
            LOG.info("exception  parsing");
            LOG.error("exception building document ", ex);
        } catch (IOException ex) {
            LOG.info("exception  with access to file");
            LOG.error("IOException  with access to file", ex);
        }  
    }
    
    private Candy buildCandy(Element candyElement) throws SAXException {
        Candy candy = new Candy();
        
        nodeInCandy = getElementByName(candyElement, "name");
        candy.setName(getElementTextContext(nodeInCandy));
        
        nodeInCandy = getElementByName(candyElement, "energy");
        int energy = Integer.parseInt(
                getElementTextContext(nodeInCandy));
        candy.setEnergy(energy);
        if (nodeInCandy.hasAttributes()) {
            String energyAttribute = getElementAttribute(nodeInCandy);
            candy.setEnergyUnit(energyAttribute);
        }
        
        nodeInCandy = getElementByName(candyElement, "type");
        String type = getElementTextContext(nodeInCandy);
        SweetType sweetType = SweetType.valueOf(type.toUpperCase());
        candy.setType(sweetType);
        
        Ingridient ingridient = new Ingridient();
        nodeInCandy = getElementByName(candyElement, "ingridients");
        
        if (nodeInCandy.hasAttributes()) {
            String ingridientType = getElementAttribute(nodeInCandy);
                ChocoType chocoType = ChocoType.valueOf(ingridientType.toUpperCase());
                ingridient.setType(chocoType);
        }
        
        nodeInIngridient = getElementByName(nodeInCandy, "water");
        int water = Integer.parseInt(
                getElementTextContext(nodeInIngridient));
        ingridient.setWater(water);
        if (nodeInCandy.hasAttributes()) {
            String waterAttribute = getElementAttribute(nodeInIngridient);
            ingridient.setWaterUnit(waterAttribute);
        }
        
        nodeInIngridient = getElementByName(nodeInCandy, "sugar");
        int sugar = Integer.parseInt(
                getElementTextContext(nodeInIngridient));
        ingridient.setSugar(sugar);
        String sugarAttribute = getElementAttribute(nodeInIngridient);
        ingridient.setSugarUnit(sugarAttribute);
        
        nodeInIngridient = getElementByName(nodeInCandy, "fructose");
        int fructose = Integer.parseInt(
                getElementTextContext(nodeInIngridient));
        ingridient.setFructose(fructose);
        String fructoseAttribute = getElementAttribute(nodeInIngridient);
        ingridient.setFructoseUnit(fructoseAttribute);
        
        nodeInIngridient = getElementByName(nodeInCandy, "vanilin");
        int vanilin = Integer.parseInt(
                getElementTextContext(nodeInIngridient));
        ingridient.setVanilin(vanilin);
        String vanilinAttribute = getElementAttribute(nodeInIngridient);
        ingridient.setVanilinUnit(vanilinAttribute);
        
        candy.setIngridient(ingridient);
        
        Value value = new Value();
        nodeInCandy = getElementByName(candyElement, "value");
        
        nodeInValue = getElementByName(nodeInCandy, "protein");
        int protein = Integer.parseInt(
                getElementTextContext(nodeInValue));
        value.setProtein(protein);
        String proteinAttribute = getElementAttribute(nodeInValue);
        value.setProteinUnit(proteinAttribute);
        
        nodeInValue = getElementByName(nodeInCandy, "fat");
        int fat = Integer.parseInt(
                getElementTextContext(nodeInValue));
        value.setFat(fat);
        String fatAttribute = getElementAttribute(nodeInValue);
        value.setFatUnit(fatAttribute);
        
        nodeInValue = getElementByName(nodeInCandy, "carbohydrate");
        int carbohydrate = Integer.parseInt(
                getElementTextContext(nodeInValue));
        value.setCarbohydrate(carbohydrate);
        String carbohydrateAttribute 
                = getElementAttribute(nodeInValue);
        value.setCarbohydrateUnit(carbohydrateAttribute);
        
        candy.setValue(value);
        
        nodeInCandy = getElementByName(candyElement, "production");
        candy.setProducer(getElementTextContext(nodeInCandy));
        
        nodeInCandy = getElementByName(candyElement, "deliveryTime");
        String dateString = getElementTextContext(nodeInCandy);
        try {
        XMLGregorianCalendar xcal = DatatypeFactory.newInstance()
                               .newXMLGregorianCalendar(dateString);
        Date date = xcal.toGregorianCalendar().getTime();
            candy.setDeliveryTime(date);
        } catch (DatatypeConfigurationException ex){
            throw new SAXException(ex);
        }        
        return candy;
    }
    
    private String getElementTextContext(Node elementNode) {
        String text = elementNode.getTextContent();
        return text;
    }
    
    private Node getElementByName(Node parentElement, String elementName){
        Node childElement = null;
        NodeList nList = parentElement.getChildNodes();
        for (int i = 0; i < nList.getLength(); i++) {
            if (nList.item(i).getNodeName().equals(elementName)) {
                childElement = nList.item(i);
                break;
            }
        }        
        return childElement;
    }
    
    private String getElementAttribute(Node element) {
        NamedNodeMap nodeMap = element.getAttributes();
        Node nodeAttribute = nodeMap.item(0);
        String attributeValue = nodeAttribute.getTextContent();
        return attributeValue;
    }
    
}
