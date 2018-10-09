/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command.util;

import by.epam.interpol.command.ActionCommand;
import by.epam.interpol.command.CommandEnum;
import by.epam.interpol.controller.Controller;
import by.epam.interpol.controller.SessionRequestContent;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.pool.ConnectionPool;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Администратор
 */
public class PersonDefiner {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    
    private static final String PERSON = "person";
    private static final String ID = "id";

    private static PersonDefiner instance;
    private static Lock lock = new ReentrantLock();
    
    private PersonDefiner() {
    }
    
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

    public boolean defineIfPersonIsWanted(SessionRequestContent requestContent) 
            throws ProjectException {   
        LOG.debug("defining person");
        
        boolean current = false;

        // extracting person
        String person = requestContent.getRequestParameter(PERSON);
        if (person == null || person.isEmpty()) {
            throw new RuntimeException();
        } else if (person.equals("isCriminal")) {
            current = true;
        }
        return current;
    }
    
    public boolean defineIfsearchByID(SessionRequestContent requestContent) 
            throws ProjectException {   
        LOG.debug("defining person");
        
        boolean current = false;

        // extracting person
        String id = requestContent.getRequestParameter(ID);
        if (id != null && !id.isEmpty()) {
            current = true;  
        }
        return current;
    }
}
