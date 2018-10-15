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
import by.epam.interpol.entity.Person;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.logic.PersonLogic;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ilia Leonov
 * points to find person by id or name-panname
 */
public class FindCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(FindCommand.class);
    
    private static final String PERSON = "personToEdit";
    private static final String NAME = "name";
    private static final String PANNAME = "panname";
    private static final String ID = "id";

    private static final String EDIT_PAGE = "path.page.edit";
    private static final String EDIT_BY_PAGE = "path.page.editBy";
    
    private PersonLogic logic;
    private PersonDefiner definer;

    /**
     *
     * @param logic logic of control at testimonies
     */
    public FindCommand(PersonLogic logic) {
        this.logic = logic;
    }

    @Override
    public String execute(SessionRequestContent requestContent) throws ProjectException {
    LOG.debug("****IN FIND COMMAND******");
        
        String page = null;
        definer = PersonDefiner.getInstance();
        
        boolean searchById = definer.defineIfsearchByID(requestContent);
        boolean isCriminal = definer.defineIfPersonIsWanted(requestContent);
 
        if (searchById) {
            String idString = requestContent.getRequestParameter(ID);
            
            if(Validator.idIsValid(idString)) {
                //create new criminal
                int id = Integer.parseInt(idString);
                
                Optional<Person> optPerson = logic.findEntityById(id);
                if (optPerson.isPresent()) {
                    if (optPerson.get().isIsCriminal() == isCriminal) {
                        requestContent.setRequestAttribute(PERSON, optPerson.get());
                        page = ConfigurationManager.getProperty(EDIT_PAGE);    
                    } else {
                        requestContent.setRequestAttribute("errorOccured",
                            "editBy.errorOccured");
                        requestContent.setRequestAttribute("errorEditByMessage",
                        "message.invalidePerson");
                        page = ConfigurationManager.getProperty(EDIT_BY_PAGE);
                    }
                } else {
                    requestContent.setRequestAttribute("errorOccured",
                            "editBy.errorOccured");
                    requestContent.setRequestAttribute("errorEditByMessage",
                            "message.invalideData");
                    page = ConfigurationManager.getProperty(EDIT_BY_PAGE);
                }
            } else {
                requestContent.setRequestAttribute("errorOccured",
                        "editBy.errorOccured");
                requestContent.setRequestAttribute("errorEditByMessage",
                        "message.invalideData");
                page = ConfigurationManager.getProperty(EDIT_BY_PAGE);
            }
        } else {
            
            String name = requestContent.getRequestParameter(NAME);
            String panname = requestContent.getRequestParameter(PANNAME);
            
            if(Validator.namePannameAreValid(name, panname)) {
                //create new criminal
                
                Optional<Person> optPerson = logic.findEntityByNamePanname(name, 
                        panname);
                
                if (optPerson.isPresent() && optPerson.get().isIsCriminal() 
                        == isCriminal) {
                    requestContent.setRequestAttribute(PERSON, optPerson.get());                    
                    page = ConfigurationManager.getProperty(EDIT_PAGE);    
                } else {
                    requestContent.setRequestAttribute("errorOccured",
                        "editBy.errorOccured");
                    requestContent.setRequestAttribute("errorEditByMessage",
                    "message.invalidePerson");
                    page = ConfigurationManager.getProperty(EDIT_BY_PAGE);

                }
            } else {
                requestContent.setRequestAttribute("errorOccured",
                        "editBy.errorOccured");
                requestContent.setRequestAttribute("errorEditByMessage",
                        "message.invalideData");
                page = ConfigurationManager.getProperty(EDIT_BY_PAGE);
            }
        }
        return page;
    }
    
}
