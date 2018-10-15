/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command;

import by.epam.interpol.command.util.ActionCommand;
import by.epam.interpol.command.util.ConfigurationManager;
import by.epam.interpol.command.util.PersonDefiner;
import by.epam.interpol.command.util.Validator;
import by.epam.interpol.controller.SessionRequestContent;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.logic.TestimoniesLogic;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ilia Leonov
 * points to testify by id or by name-panname
 */
public class TestifyCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(TestifyCommand.class);
    
    private static final String NAME = "name";
    private static final String PANNAME = "panname";
    private static final String PERSON = "person";
    private static final String ID = "id";
    private static final String TESTIMONY = "testimony";
    private static final String MAIN_PAGE = "path.page.main";
    private static final String TESTIFY_PAGE = "path.page.testify";
    private static final String TESTIM_CRIME_CONTR = "controller.testimoniesCrim";
    private static final String TESTIM_WANT_CONTR = "controller.testimoniesWant";
    private static final String TESTIFY_ERR_CONTR = "controller.testifyError";
    
    private TestimoniesLogic logic;
    private PersonDefiner definer;

    /**
     *
     * @param logic logic of control at testimonies
     */
    public TestifyCommand(TestimoniesLogic logic) {
        this.logic = logic;
    }

    @Override
    public String execute(SessionRequestContent requestContent) 
            throws ProjectException {
    LOG.debug("****IN TESTIFY COMMAND******");
        
        String page = null;
        definer = PersonDefiner.getInstance();
        
        boolean searchById = definer.defineIfsearchByID(requestContent);

        if (searchById) {
            String idPersonString = requestContent.getRequestParameter(ID);
            Integer idUser = (Integer) 
                    requestContent.getSessionRequestAttribute(ID);
            String testimony = requestContent.getRequestParameter(TESTIMONY);
            
            if(Validator.testimonyIsValid(testimony)) {
                //create new criminal
                int idPerson = Integer.parseInt(idPersonString);
                
                Optional<Integer> optInt = logic.testifyById(idPerson,
                        idUser, testimony);
                
                if (optInt.isPresent()) {
                    if (definer.defineIfPersonIsWanted(requestContent)) {
                        page = ConfigurationManager.getProperty(TESTIM_CRIME_CONTR);    
                    } else {
                        page = ConfigurationManager.getProperty(TESTIM_WANT_CONTR);
                    }
                } else {
                    page = ConfigurationManager.getProperty(MAIN_PAGE);
                }
            } else {
                requestContent.setRequestAttribute("errorOccured",
                    "testify.errorOccured");
                requestContent.setRequestAttribute("errorTestifyMessage",
                    "message.invalideTestimony");
                requestContent.setRequestAttribute(ID, 
                        requestContent.getRequestParameter(ID));
                requestContent.setRequestAttribute(PERSON, 
                        requestContent.getRequestParameter(PERSON));
                
                page = ConfigurationManager.getProperty(TESTIFY_ERR_CONTR);
            }
        } else {
            String name = requestContent.getRequestParameter(NAME)
                .replaceAll("</?script>", "");
            String panname = requestContent.getRequestParameter(PANNAME)
                    .replaceAll("</?script>", "");
            Integer idUser = (Integer) 
                    requestContent.getSessionRequestAttribute(ID);
            String testimony = requestContent.getRequestParameter(TESTIMONY);
            
            if(Validator.testimonyIsValid(testimony)) {

                Optional<Integer> optInt = logic.testifyByNamePanname(name, 
                        panname, idUser, testimony);
                if (optInt != null) {
                    if (optInt.isPresent()) {                   
                        if (definer.defineIfPersonIsWanted(requestContent)) {
                            page = ConfigurationManager.getProperty(TESTIM_CRIME_CONTR);    
                        } else {
                            page = ConfigurationManager.getProperty(TESTIM_WANT_CONTR);
                        }  
                    } else {
                        LOG.info("couldn't find searched by id");
                        page = ConfigurationManager.getProperty(MAIN_PAGE);
                    }
                } else {
                    LOG.info("couldn't find searched by id");
                    page = ConfigurationManager.getProperty(MAIN_PAGE);
                }
            } else {
                requestContent.setRequestAttribute("errorOccured",
                        "testify.errorOccured");
                requestContent.setRequestAttribute("errorTestifyMessage",
                        "message.invalideTestimony");
                
                requestContent.setRequestAttribute(NAME, 
                        requestContent.getRequestParameter(NAME));
                requestContent.setRequestAttribute(PANNAME, 
                        requestContent.getRequestParameter(PANNAME));
                requestContent.setRequestAttribute(PERSON, 
                        requestContent.getRequestParameter(PERSON));
                page = ConfigurationManager.getProperty(TESTIFY_PAGE);
            }
        }
        return page;
    } 
}
