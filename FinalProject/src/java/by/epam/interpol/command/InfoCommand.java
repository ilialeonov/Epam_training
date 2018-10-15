/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command;

import by.epam.interpol.command.util.ActionCommand;
import by.epam.interpol.command.util.ConfigurationManager;
import by.epam.interpol.command.util.PersonDefiner;
import by.epam.interpol.controller.SessionRequestContent;
import by.epam.interpol.entity.Testimony;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.logic.TestimoniesLogic;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ilia Leonov
 * points to show admin testimonies about several persons at page
 */
public class InfoCommand implements ActionCommand{
    private static final Logger LOG = LogManager.getLogger(InfoCommand.class);
    
    private static final String ID = "id";
    private static final String PERSON = "person";
    private static final String PAGE_NUMBER = "pageNumber";
    private static final String PERSON_LIST = "list";
    private static final String DIRECTION = "direction";
    private static final String NEXT = "next";
    private static final String PREVIOUS = "previous";
    private static final String TESTIMONIES_ADMIN_PAGE 
            = "path.page.testimoniesAdmin";
    private static final int FIRST_PAGE = 1;
    private static final int PAGE_SIZE = 5;
    
    private TestimoniesLogic logic;
    private PersonDefiner definer;
    
    /**
     *
     * @param logic  logic of control at testimonies
     */
    public InfoCommand(TestimoniesLogic logic) {
        this.logic = logic;
    }

    @Override
    public String execute(SessionRequestContent requestContent) throws ProjectException {
        LOG.debug("****IN INFO COMMAND******");
        
        Integer idUser = (Integer) 
                    requestContent.getSessionRequestAttribute(ID);
        
        String page = null;
        int pageNumberPar;
        definer = PersonDefiner.getInstance();
        
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
        List<Testimony> showList = logic.findAdminTestimonies(PAGE_SIZE, 
                offset, isCriminal);
        
        requestContent.setRequestAttribute(PERSON_LIST, showList);
        
        requestContent.setRequestAttribute(PAGE_NUMBER, pageNumberPar);
        requestContent.setRequestAttribute(PERSON, 
                requestContent.getRequestParameter(PERSON));

        page = ConfigurationManager.getProperty(TESTIMONIES_ADMIN_PAGE);    
        return page;
    }
}
