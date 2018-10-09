/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command;

import by.epam.interpol.command.util.ConfigurationManager;
import by.epam.interpol.command.util.PersonDefiner;
import by.epam.interpol.command.util.Validator;
import by.epam.interpol.controller.SessionRequestContent;
import by.epam.interpol.entity.Person;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.logic.PersonLogic;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Администратор
 */
class ShowCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(CreateCommand.class);
    
    private static final String PERSON = "person";
    private static final String PAGE_NUMBER = "pageNumber";
    private static final String PERSON_LIST = "list";
    private static final String DIRECTION = "direction";
    private static final String NEXT = "next";
    private static final String PREVIOUS = "previous";
    private static final int FIRST_PAGE = 1;
    private static final int PAGE_SIZE = 5;
    
    
    private PersonLogic logic;
    private PersonDefiner definer;

    public ShowCommand(PersonLogic logic) {
        this.logic = logic;
        definer = PersonDefiner.getInstance();
    }

    @Override
    public String execute(SessionRequestContent requestContent) throws ProjectException {
        LOG.debug("****IN SHOW COMMAND******");
        
        String page = null;
        int pageNumberPar;
        
        boolean isCriminal = definer.defineIfPersonIsWanted(requestContent);
        String direction = requestContent.getRequestParameter(DIRECTION);
        
        if (direction != null) {
            pageNumberPar = 
                Integer.parseInt(requestContent.getRequestParameter(PAGE_NUMBER));
            
            if (direction.equals(NEXT)) {
                pageNumberPar++;
            } else if (direction.equals(PREVIOUS)) {
                if (pageNumberPar > FIRST_PAGE) {
                pageNumberPar--;
                }
            }            
        } else {
            pageNumberPar = FIRST_PAGE;
        }
        int offset = (pageNumberPar - FIRST_PAGE)*PAGE_SIZE;
        
//        finds several persons for page
        List<Person> showList = logic.findForPage(PAGE_SIZE, offset, isCriminal);
        requestContent.setRequestAttribute(PERSON_LIST, showList);
        
        requestContent.setRequestAttribute(PAGE_NUMBER, pageNumberPar);
        requestContent.setRequestAttribute(PERSON, 
                requestContent.getRequestParameter(PERSON));

        page = ConfigurationManager.getProperty("path.page.show");    
        return page;
    }
    
}
