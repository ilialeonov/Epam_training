/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command.util;

import by.epam.interpol.controller.Controller;
import by.epam.interpol.controller.SessionRequestContent;
import by.epam.interpol.exception.ProjectException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ilia Leonov
 * defines person status, defines if person is searched by id 
 */
public class PersonDefiner {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    
    private static final String PERSON = "person";
    private static final String ID = "id";
    private static final String IS_CRIMINAL = "isCriminal";
    private static final String IS_MISSED = "isMissed";
    
    private static PersonDefiner instance;
    private static Lock lock = new ReentrantLock();
    
    private PersonDefiner() {
    }
    
    /**
     *
     * @return instance of class
     */
    public static PersonDefiner getInstance(){
        LOG.debug("getting pool");
        if (instance == null){
            lock.lock();
            if (instance == null) {
                instance = new PersonDefiner();
            }
            lock.unlock();
        }
        return instance;
    }

    /**
     *
     * @param requestContent consists requestParameters, requestAttributes 
     * and session information 
     * @return person status, true - if person is wanted, false - if person is
     * missed
     * @throws ProjectException
     */
    public boolean defineIfPersonIsWanted(SessionRequestContent requestContent) 
            throws ProjectException {   
        LOG.debug("defining person");
        
        boolean current = false;

        // extracting person
        String person = requestContent.getRequestParameter(PERSON);
        if (person == null || person.isEmpty()) {
            throw new ProjectException();
        } else if (person.equals(IS_CRIMINAL)) {
            current = true;
        } else if (!person.equals(IS_MISSED)) {
            throw new ProjectException();
        }
        return current;
    }
    
    /**
     *
     * @param requestContent consists requestParameters, requestAttributes 
     * and session information 
     * @return if person is searched by id: true -if by id, false - if by 
     * name and panname
     * @throws ProjectException
     */
    public boolean defineIfsearchByID(SessionRequestContent requestContent) 
            throws ProjectException {   
        LOG.debug("defining person");
        
        boolean current = false;

        // extracting person
        String id = requestContent.getRequestParameter(ID);
        LOG.debug(id);
        if (id != null && !id.isEmpty()) {
            current = true;  
        }
        return current;
    }
}
