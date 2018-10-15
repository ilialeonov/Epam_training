/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command;

import by.epam.interpol.command.util.ActionCommand;
import by.epam.interpol.command.util.ConfigurationManager;
import by.epam.interpol.command.util.Validator;
import by.epam.interpol.controller.SessionRequestContent;
import by.epam.interpol.entity.Person;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.logic.PersonLogic;
import java.awt.image.BufferedImage;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 *
 * @author Leonov Ilia
 * takes control with editing persons
 */
public class EditCommand implements ActionCommand{
    private static final Logger LOG = LogManager.getLogger(EditCommand.class);
    
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PANNAME = "panname";
    private static final String AGE = "age";
    private static final String PHOTO = "pic";
    private static final String BIRTH_REGION = "birthReg";
    private static final String LAST_SEEN_REGION = "lastSeenReg";
    private static final String AWARD = "award";
    private static final String INFORMATION = "information";
    private static final String STATUS = "status";
    private static final String PERSON_TO_EDIT = "personToEdit";
    
    private static final String EDIT_PAGE = "path.page.edit";
    private static final String CREATE_PAGE = "path.page.create";
    private static final String MAIN_CONTR = "controller.main";
    
    private PersonLogic logic;

    /**
     *
     * @param logic logic of control at testimonies
     */
    public EditCommand(PersonLogic logic) {
        this.logic = logic;
    }

    @Override
    public String execute(SessionRequestContent requestContent) throws ProjectException {
        LOG.debug("****IN EDIT COMMAND******");
        
        String page;
        Optional<Person> optPerson;
        
        BufferedImage photo = requestContent.getRequestImage();
        int id = Integer.parseInt(requestContent.getRequestParameter(ID));
        String name = requestContent.getRequestParameter(NAME);
        String panname = requestContent.getRequestParameter(PANNAME);
        String age = requestContent.getRequestParameter(AGE);
        String birthPlace = requestContent.getRequestParameter(BIRTH_REGION);
        String lastPlace = requestContent.getRequestParameter(LAST_SEEN_REGION);
        String award = requestContent.getRequestParameter(AWARD);
        String information = requestContent.getRequestParameter(INFORMATION);

        if(Validator.createCriminalDataIsValid(name, panname, age, birthPlace, 
                lastPlace, award, information)) {
            //create new criminal
            int ageParsed = Integer.parseInt(age);
            int awardParsed = Integer.parseInt(award);
            
            if (photo != null) {
                optPerson = logic.editPerson(id, name, panname, 
                        ageParsed, photo, birthPlace, lastPlace, awardParsed, 
                        information);
            } else {
                optPerson = logic.editPersonNoPhoto(id, name, panname, 
                        ageParsed, birthPlace, lastPlace, awardParsed, 
                        information);
            }
            if (optPerson.isPresent()) {
                page = ConfigurationManager.getProperty(MAIN_CONTR);    
            } else {
                requestContent.setRequestAttribute("errorOccured",
                    "editBy.errorOccured");
                page = ConfigurationManager.getProperty(CREATE_PAGE);
            }
        } else {
            requestContent.setRequestAttribute("errorOccured",
                    "editBy.errorOccured");
            requestContent.setRequestAttribute("errorEditByMessage",
                    "message.invalideData");
            requestContent.setRequestAttribute(PERSON_TO_EDIT,
                    requestContent.getRequestAttribute(PERSON_TO_EDIT));
            page = ConfigurationManager.getProperty(EDIT_PAGE);
        }
        return page;
    }
    
}
