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
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Администратор
 */
public class EditCommand implements ActionCommand{
    private static final Logger LOG = LogManager.getLogger(CreateCommand.class);
    
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
    private static final String YES = "Yes";

    private PersonLogic logic;

    public EditCommand(PersonLogic logic) {
        this.logic = logic;
    }

    @Override
    public String execute(SessionRequestContent requestContent) throws ProjectException {
        LOG.debug("****IN EDIT COMMAND******");
        
        String page = null;
        Optional<Person> optPerson;
        BufferedImage photo = requestContent.getRequestImage();

        LOG.debug("photo:" + photo);
        int id = Integer.parseInt(requestContent.getRequestParameter(ID));
        String name = requestContent.getRequestParameter(NAME)
                .replaceAll("</?script>", "");
        String panname = requestContent.getRequestParameter(PANNAME)
                .replaceAll("</?script>", "");
        String age = requestContent.getRequestParameter(AGE);
        String birthPlace = requestContent.getRequestParameter(BIRTH_REGION)
                .replaceAll("</?script>", "");
        String lastPlace = requestContent.getRequestParameter(LAST_SEEN_REGION)
                .replaceAll("</?script>", "");
        String award = requestContent.getRequestParameter(AWARD);
        String information = requestContent.getRequestParameter(INFORMATION)
                .replaceAll("</?script>", "");
        String status = requestContent.getRequestParameter(STATUS)
                .replaceAll("</?script>", "");
        
        if(Validator.createCriminalDataIsValid(name, panname, age, birthPlace, 
                lastPlace, award, information, status)) {
            //create new criminal
            int ageParsed = Integer.parseInt(age);
            int awardParsed = Integer.parseInt(award);
            boolean found = status.equals(YES) ? true:false;
            if (photo != null) {
                optPerson = logic.editPerson(id, name, panname, 
                        ageParsed, photo, birthPlace, lastPlace, awardParsed, 
                        information, found);
            } else {
                optPerson = logic.editPersonNoPhoto(id, name, panname, 
                        ageParsed, birthPlace, lastPlace, awardParsed, 
                        information, found);
            }
            if (optPerson.isPresent()) {
                page = ConfigurationManager.getProperty("controller.main");    
            } else {
                LOG.info("couldn't edit person");
                requestContent.setRequestAttribute("errorOccured",
                    "editBy.errorOccured");
                page = ConfigurationManager.getProperty("path.page.create");
            }
        } else {
            requestContent.setRequestAttribute("errorOccured",
                    "editBy.errorOccured");
            requestContent.setRequestAttribute("errorEditByMessage",
                    "message.invalideData");
            requestContent.setRequestAttribute("personToEdit",
                    requestContent.getRequestAttribute("personToEdit"));
            page = ConfigurationManager.getProperty("path.page.edit");
        }
        return page;
    }
    
}
