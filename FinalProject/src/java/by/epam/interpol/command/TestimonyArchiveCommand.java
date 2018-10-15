/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command;

import by.epam.interpol.command.util.ActionCommand;
import by.epam.interpol.command.util.ConfigurationManager;
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
 * points to show testimony archove to user
 */
public class TestimonyArchiveCommand implements ActionCommand{
    private static final Logger LOG = LogManager.getLogger(TestimonyArchiveCommand.class);
    
    private static final String ID = "id";
    private static final String PAGE_NUMBER = "pageNumber";
    private static final String PERSON_LIST = "list";
    private static final String DIRECTION = "direction";
    private static final String NEXT = "next";
    private static final String PREVIOUS = "previous";
    private static final String TESTIMONIES_PAGE = "path.page.testimoniesUserArchive";
    private static final int FIRST_PAGE = 1;
    private static final int PAGE_SIZE = 5;
    
    private TestimoniesLogic logic;
    
    /**
     *
     * @param logic logic of control at testimonies
     */
    public TestimonyArchiveCommand(TestimoniesLogic logic) {
        this.logic = logic;
    }

    @Override
    public String execute(SessionRequestContent requestContent) throws ProjectException {
        LOG.debug("****IN TESTIMONIES COMMAND******");
        
        Integer idUser = (Integer) 
                    requestContent.getSessionRequestAttribute(ID);
        
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
        List<Testimony> showList = logic.findUserArchive(idUser, 
                PAGE_SIZE, offset);
        
        requestContent.setRequestAttribute(PERSON_LIST, showList);
        
        requestContent.setRequestAttribute(PAGE_NUMBER, pageNumberPar);

        page = ConfigurationManager.getProperty(TESTIMONIES_PAGE);    
        return page;
    }
}
