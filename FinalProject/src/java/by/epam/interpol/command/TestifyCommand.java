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
import by.epam.interpol.logic.TestimoniesLogic;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Администратор
 */
class TestifyCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(FindCommand.class);
    
    private static final String NAME = "name";
    private static final String PANNAME = "panname";
    private static final String PERSON = "person";
    private static final String ID = "id";
    private static final String TESTIMONY = "testimony";
    
    private TestimoniesLogic logic;
    private PersonDefiner definer;

    public TestifyCommand(TestimoniesLogic logic) {
        this.logic = logic;
        definer = PersonDefiner.getInstance();
    }

    @Override
    public String execute(SessionRequestContent requestContent) 
            throws ProjectException {
    LOG.debug("****IN TESTIFY COMMAND******");
        
        String page = null;
        
        boolean searchById = definer.defineIfsearchByID(requestContent);

        if (searchById) {
            String idPersonString = requestContent.getRequestParameter(ID);
            Integer idUser = (Integer) 
                    requestContent.getSessionRequestAttribute(ID);
            String testimony = requestContent.getRequestParameter(TESTIMONY)
                .replaceAll("</?script>", "");
            
            if(Validator.testimonyIsValid(testimony)) {
                //create new criminal
                int idPerson = Integer.parseInt(idPersonString);
                
                Optional<Integer> optInt = logic.testifyById(idPerson,
                        idUser, testimony);
                
                if (optInt.isPresent()) {
                    if (definer.defineIfPersonIsWanted(requestContent)) {
                    page = ConfigurationManager.getProperty(
                            "controller.testimoniesCrim");    
                    } else {
                        page = ConfigurationManager.getProperty(
                                "controller.testimoniesWant");
                    }
                } else {
                    LOG.info("couldn't testify");
                    page = ConfigurationManager.getProperty("path.page.main");
                }
            } else {
                LOG.info("couldn't testify");
                requestContent.setRequestAttribute("errorOccured",
                    "testify.errorOccured");
                requestContent.setRequestAttribute("errorTestifyMessage",
                    "message.invalideTestimony");
                requestContent.setRequestAttribute(ID, 
                        requestContent.getRequestParameter(ID));
                requestContent.setRequestAttribute(PERSON, 
                        requestContent.getRequestParameter(PERSON));
                
                page = ConfigurationManager.getProperty("controller.testifyError");
            }
        } else {
            String name = requestContent.getRequestParameter(NAME)
                .replaceAll("</?script>", "");
            String panname = requestContent.getRequestParameter(PANNAME)
                    .replaceAll("</?script>", "");
            Integer idUser = (Integer) 
                    requestContent.getSessionRequestAttribute(ID);
            String testimony = requestContent.getRequestParameter(TESTIMONY)
                .replaceAll("</?script>", "");
            
            if(Validator.testimonyIsValid(testimony)) {

                Optional<Integer> optInt = logic.testifyByNamePanname(name, 
                        panname, idUser, testimony);
                if (optInt != null) {
                    if (optInt.isPresent()) {                   
                        if (definer.defineIfPersonIsWanted(requestContent)) {
                            page = ConfigurationManager.getProperty(
                                "controller.testimoniesCrim");    
                        } else {
                            page = ConfigurationManager.getProperty(
                                    "controller.testimoniesWant");
                        }  
                    } else {
                        LOG.info("couldn't find searched by id");
                        page = ConfigurationManager.getProperty("path.page.main");
                    }
                } else {
                    LOG.info("couldn't find searched by id");
                    page = ConfigurationManager.getProperty("path.page.main");
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
                page = ConfigurationManager.getProperty("path.page.testify");
            }
        }
        return page;
    } 
}
