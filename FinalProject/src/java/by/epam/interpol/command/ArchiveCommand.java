/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command;

import by.epam.interpol.command.util.ActionCommand;
import by.epam.interpol.command.util.ConfigurationManager;
import by.epam.interpol.controller.SessionRequestContent;
import by.epam.interpol.entity.Person;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.logic.PersonLogic;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Leonov Ilia
 * takes control at archive of found persons
 */
public class ArchiveCommand implements ActionCommand{
    private static final Logger LOG = LogManager.getLogger(ArchiveCommand.class);
    
    private static final String PAGE_NUMBER = "pageNumber";
    private static final String PERSON_LIST = "list";
    private static final String DIRECTION = "direction";
    private static final String NEXT = "next";
    private static final String PREVIOUS = "previous";
    private static final int FIRST_PAGE = 1;
    private static final int PAGE_SIZE = 5;
        
    private static final String ARCHIVE_PAGE = "path.page.archive";
    
    PersonLogic logic;

    /**
     *
     * @param logic logic of control at persons
     */
    public ArchiveCommand(PersonLogic logic) {
        this.logic = logic;
    }

    
    @Override
    public String execute(SessionRequestContent requestContent) throws ProjectException {
        LOG.debug("****IN ARCHIVE COMMAND******");
        
        String page = null;
        int pageNumberPar;
        
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
        List<Person> showList = logic.findForPage(PAGE_SIZE, offset);
        requestContent.setRequestAttribute(PERSON_LIST, showList);
        
        requestContent.setRequestAttribute(PAGE_NUMBER, pageNumberPar);

        page = ConfigurationManager.getProperty(ARCHIVE_PAGE);    
        return page;
    }
    
}
