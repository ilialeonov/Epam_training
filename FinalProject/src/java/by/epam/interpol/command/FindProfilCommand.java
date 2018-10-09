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
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Администратор
 */
class FindProfilCommand implements ActionCommand {
private static final Logger LOG = LogManager.getLogger(FindCommand.class);
    
    private static final String PERSON = "person";
    private static final String NAME = "name";
    private static final String PANNAME = "panname";
    private static final String ID = "id";

    private PersonLogic logic;
    private PersonDefiner definer;

    public FindProfilCommand(PersonLogic logic) {
        this.logic = logic;
        definer = PersonDefiner.getInstance();
    }

    @Override
    public String execute(SessionRequestContent requestContent) throws ProjectException {
    LOG.debug("****IN FINDPROFIL COMMAND******");
        
        String page = null;
        
        boolean searchById = definer.defineIfsearchByID(requestContent);
        boolean isCriminal = definer.defineIfPersonIsWanted(requestContent);
 
        if (searchById) {
            String idString = requestContent.getRequestParameter(ID)
                .replaceAll("</?script>", "");
            
            if(Validator.idIsValid(idString)) {
                //create new criminal
                int id = Integer.parseInt(idString);
                
                Optional<Person> optPerson = logic.findEntityById(id);
                
                if (optPerson.isPresent()) {
                    if (optPerson.get().isIsCriminal() == isCriminal) {
                        requestContent.setRequestAttribute(PERSON, optPerson.get());
                        page = ConfigurationManager.getProperty("path.page.testify");    
                    } else {
                        LOG.info("couldn't find searched by id");
                        requestContent.setRequestAttribute("errorOccured",
                            "editBy.errorOccured");
                        requestContent.setRequestAttribute("errorEditByMessage",
                        "message.invalidePerson");
                        page = ConfigurationManager.getProperty("path.page.chosePerson");
                    }
                }
            } else {
                requestContent.setRequestAttribute("errorOccured",
                        "editBy.errorOccured");
                requestContent.setRequestAttribute("errorEditByMessage",
                        "message.invalideData");
                page = ConfigurationManager.getProperty("path.page.chosePerson");
            }
        } else {
            
            String name = requestContent.getRequestParameter(NAME)
                .replaceAll("</?script>", "");
            String panname = requestContent.getRequestParameter(PANNAME)
                    .replaceAll("</?script>", "");
            
            if(Validator.namePannameAreValid(name, panname)) {
                //create new criminal
                
                Optional<Person> optPerson = logic.findEntityByNamePanname(name, panname);
                if (optPerson.isPresent() && optPerson.get().isIsCriminal() == isCriminal) {
                    requestContent.setRequestAttribute(PERSON, optPerson.get());                    
                    page = ConfigurationManager.getProperty("path.page.testify");    
                } else {
                    LOG.info("couldn't find searched by id");
                    requestContent.setRequestAttribute("errorOccured",
                        "editBy.errorOccured");
                    requestContent.setRequestAttribute("errorEditByMessage",
                    "message.invalidePerson");
                    page = ConfigurationManager.getProperty("path.page.chosePerson");

                }
            } else {
                requestContent.setRequestAttribute("errorOccured",
                        "editBy.errorOccured");
                requestContent.setRequestAttribute("errorEditByMessage",
                        "message.invalideData");
                page = ConfigurationManager.getProperty("path.page.chosePerson");
            }
        }
        return page;   
    }
}