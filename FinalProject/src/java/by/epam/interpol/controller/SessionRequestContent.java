/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.controller;

import by.epam.interpol.exception.ProjectException;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ilia Leonov
 * accums information about requestParams, requestAttributes session information
 * from request and sets to request after actions with. It's a wrapper for request
 */
public class SessionRequestContent {
    private static final Logger LOG = LogManager.getLogger(SessionRequestContent.class);
    
    private static final String PICTURE = "pic";
    
    private boolean sessionIsActive;
    private HashMap<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;
    private InputStream imageInputStream;
    private BufferedImage image;

    /**
     *Creates objects to save attrs, params
     */
    public SessionRequestContent() {
        requestAttributes = new HashMap<String, Object>();
        requestParameters = new HashMap<String, String[]>();
        sessionAttributes = new HashMap<String, Object>();
    }

    /**
     *
     * @param request request from user
     * @throws ProjectException
     */
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

    /**
     *
     * @param request request from req/res pair for user
     */
        public void insertAttributes(HttpServletRequest request) {

        // adds attributes to request
        requestAttributes.forEach((k,v) -> {
            request.setAttribute(k, v);
        });
        
        // adds attributes to session 
        if(sessionIsActive) {
            HttpSession session = request.getSession(true);
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
    
    /**
     * activates session 
     */
    public void activateSession(){
        sessionIsActive = true;
    }
    
//    invalidates session and restarts sessionAttributes

    /**
     * deactivates session
     */
        public void deactivateSession(){
        sessionIsActive = false;
        sessionAttributes = new HashMap<String, Object>();
    }
    
    /**
     *
     * @param name key to the attribute
     * @return Object in Map by this key
     */
    public Object getRequestAttribute(String name) {
        requestAttributes.forEach((k,v) -> {
            LOG.debug(k + " : " + v);
        });
        return requestAttributes.get(name);
    }
    
    /**
     *
     * @return image when it's setted
     */
    public BufferedImage getRequestImage() {
        return image;
    }
    
    /**
     *
     * @param name key to attribute
     * @param attribute attribute setted by key
     */
    public void setRequestAttribute(String name, Object attribute) {
        requestAttributes.put(name, attribute);
    }

    /**
     *
     * @return if session is active
     */
    public boolean isSessionIsActive() {
        return sessionIsActive;
    }

    /**
     *
     * @param sessionIsActive sets true to activate session
     * and false to deactivate
     */
    public void setSessionIsActive(boolean sessionIsActive) {
        this.sessionIsActive = sessionIsActive;
    }
    
    /**
     *
     * @param name key to attribute
     * @return Object value by this key
     */
    public Object getSessionRequestAttribute(String name) {
        Object attribute = null;
        if (sessionIsActive) {
            attribute = sessionAttributes.get(name);
        }
        return attribute;
    }
    
    /**
     *
     * @param name key to parameter
     * @return parameters by this key
     */
    public String[] getRequestParameters(String name) {
        return requestParameters.get(name);
    }
    
    /**
     *
     * @param name key to parameter
     * @return parameter value by this key
     */
    public String getRequestParameter(String name) {
        String param = null;
        if(requestParameters.containsKey(name)) {
            param = requestParameters.get(name)[0];
        }
        return param;
    }
    
    /**
     *
     * @param name key to session attribute
     * @param attribute Object value setted by this key
     */
    public void setSessionRequestAttribute(String name, Object attribute) {
        if (sessionIsActive) {
            sessionAttributes.put(name, attribute);
        }
    }
    
    /**
     *
     * @return Map of all pairs key-value attributes
     */
    public HashMap<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    /**
     *
     * @return map of all pairs key-value parameters
     */
    public Map<String, String[]> getRequestParameters() {
        return requestParameters;
    }

    /**
     *
     * @return map of all pairs key-value of session attributes
     */
    public HashMap<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    /**
     * remove all attributes
     */
    public void removeAttributes() {
        requestAttributes.forEach((k,v) -> {
            requestAttributes.put(k, null);
        }); 
    }
    
}
