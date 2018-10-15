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
import by.epam.interpol.logic.PersonLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 *
 * @author Leonov Ilia
 * points to delete person
 */
public class DeleteCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(DeleteCommand.class);
    
    private static final String NAME = "name";
    private static final String PANNAME = "panname";
    private static final String ID = "id";

    private static final String EDIT_BY_PAGE = "path.page.editBy";
    private static final String MAIN_CONTR = "controller.main";

    private PersonLogic logic;
    private PersonDefiner definer;

    /**
     *
     * @param logic logic of control at persons
     */
    public DeleteCommand(PersonLogic logic) {
        this.logic = logic;
    }

    @Override
    public String execute(SessionRequestContent requestContent) throws ProjectException {
    LOG.debug("****IN DELETE PERSON COMMAND******");
        
        String page = null;
        definer = PersonDefiner.getInstance();
        
        boolean searchById = definer.defineIfsearchByID(requestContent);
        LOG.debug("ID is " + searchById);
        if (searchById) {
            String idString = requestContent.getRequestParameter(ID);
            
            if(Validator.idIsValid(idString)) {
                //create new criminal
                int id = Integer.parseInt(idString);
                boolean deleted = logic.delete(id);
                
                if (deleted) {                    
                    page = ConfigurationManager.getProperty(MAIN_CONTR); 
                    LOG.debug(page);
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
                
                boolean deleted = logic.delete(name, panname);                
                if (deleted) {                   
                    page = ConfigurationManager.getProperty(MAIN_CONTR);    
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
