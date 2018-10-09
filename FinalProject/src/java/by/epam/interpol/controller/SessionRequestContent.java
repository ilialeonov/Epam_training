/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.controller;

import by.epam.interpol.controller.Controller;
import by.epam.interpol.exception.ProjectException;
import com.oreilly.servlet.MultipartRequest;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Администратор
 */
public class SessionRequestContent {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    
    private static final String PICTURE = "pic";
    
    private boolean sessionIsActive;
    private HashMap<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;
    private InputStream imageInputStream;
    private BufferedImage image;

    public SessionRequestContent() {
        requestAttributes = new HashMap<String, Object>();
        requestParameters = new HashMap<String, String[]>();
        sessionAttributes = new HashMap<String, Object>();
    }

    public void extractValues(HttpServletRequest request) throws ProjectException {
     
        LOG.debug("Extracting from request to request content");
        
        // setting requestAttributes
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            requestAttributes.put(attributeName, request.getAttribute(attributeName));
        }
        LOG.debug("setting requestParameters");
        
        //setting requestParameters
        requestParameters = request.getParameterMap();
        requestParameters.forEach((k,v) -> {
            LOG.debug(k+" : "+v);
        
        });
        LOG.debug("getting Map");
        Enumeration<String> names= request.getParameterNames();
        while (names.hasMoreElements()) {
            LOG.debug(names.nextElement());
        }
        if (requestParameters.containsKey("multimedia")) {
            LOG.debug("contaons key");
            try {
                Part filePart = request.getPart(PICTURE);
                if(filePart != null) {
                    imageInputStream = new BufferedInputStream (filePart.getInputStream());
                    image = ImageIO.read(imageInputStream);
                }
            } catch (IOException ex) {
                throw new ProjectException("Couln't have got access to folder", ex);
            } catch (ServletException ex) {
                throw new ProjectException("Server error has occured", ex);
            }
        } 
        
        

        //setting sessionAttributes
        HttpSession session = request.getSession(false);
        if(session != null) {
            sessionIsActive = true;
            Enumeration<String> attributeSessionNames = session.getAttributeNames();
            while (attributeSessionNames.hasMoreElements()) {
                String attributeName = attributeSessionNames.nextElement();
                sessionAttributes.put(attributeName, session.getAttribute(attributeName));
            }
        } else {
            sessionIsActive = false;
        }
    }
    
    // adds attributes to request 
    public void insertAttributes(HttpServletRequest request) {

        // adds attributes to request
        requestAttributes.forEach((k,v) -> {
            request.setAttribute(k, v);
        });
        
        // adds attributes to session 
        if(sessionIsActive) {
            HttpSession session = request.getSession(true);
            LOG.debug("*****SESSIONREQUESTCONTENT*****");
            LOG.debug("sessionIsActive " + sessionIsActive);
            sessionAttributes.forEach((k,v) -> {
                session.setAttribute(k, v);
            });
        } else {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
        }
    }
    
    public void activateSession(){
        sessionIsActive = true;
    }
    
//    invalidates session and restarts sessionAttributes
    public void deactivateSession(){
        sessionIsActive = false;
        sessionAttributes = new HashMap<String, Object>();
    }
    
    public Object getRequestAttribute(String name) {
        requestAttributes.forEach((k,v) -> {
            LOG.debug(k + " : " + v);
        });
        return requestAttributes.get(name);
    }
    
    public BufferedImage getRequestImage() {
        return image;
    }
    
    public void setRequestAttribute(String name, Object attribute) {
        requestAttributes.put(name, attribute);
    }

    public boolean isSessionIsActive() {
        return sessionIsActive;
    }

    public void setSessionIsActive(boolean sessionIsActive) {
        this.sessionIsActive = sessionIsActive;
    }
    
    public Object getSessionRequestAttribute(String name) {
        Object attribute = null;
        if (sessionIsActive) {
            attribute = sessionAttributes.get(name);
        }
        return attribute;
    }
    
    public String[] getRequestParameters(String name) {
        return requestParameters.get(name);
    }
    
    public String getRequestParameter(String name) {
        String param = null;
        if(requestParameters.containsKey(name)) {
            param = requestParameters.get(name)[0];
        }
        return param;
    }
    
    public void setSessionRequestAttribute(String name, Object attribute) {
        if (sessionIsActive) {
            sessionAttributes.put(name, attribute);
        }
    }
    
    public HashMap<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public Map<String, String[]> getRequestParameters() {
        return requestParameters;
    }

    public HashMap<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public void removeAttributes() {
        requestAttributes.forEach((k,v) -> {
            requestAttributes.put(k, null);
        }); 
    }
    
}
