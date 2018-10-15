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
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Leonov Ilia
 * points to create person
 */
public class CreateCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(CreateCommand.class);
    
    private static final String NAME = "name";
    private static final String PANNAME = "panname";
    private static final String AGE = "age";
    private static final String BIRTH_REGION = "birthReg";
    private static final String LAST_SEEN_REGION = "lastSeenReg";
    private static final String AWARD = "award";
    private static final String INFORMATION = "information";

    private static final String CREATE_PAGE = "path.page.create";
    private static final String MAIN_CONTR = "controller.main";
    
    private PersonLogic logic;
    private PersonDefiner definer;

    /**
     *
     * @param logic logic of control at persons
     */
    public CreateCommand(PersonLogic logic) {
        this.logic = logic;
        
    }
    
    @Override
    public String execute(SessionRequestContent requestContent) throws ProjectException {
        LOG.debug("****IN CREATE COMMAND******");
        
        String page;
        definer = PersonDefiner.getInstance();
        BufferedImage photo = requestContent.getRequestImage();

        String name = requestContent.getRequestParameter(NAME);
        String panname = requestContent.getRequestParameter(PANNAME);
        String age = requestContent.getRequestParameter(AGE);
        String birthPlace = requestContent.getRequestParameter(BIRTH_REGION);
        String lastPlace = requestContent.getRequestParameter(LAST_SEEN_REGION);
        String award = requestContent.getRequestParameter(AWARD);
        String information = requestContent.getRequestParameter(INFORMATION);
        boolean isCriminal = definer.defineIfPersonIsWanted(requestContent);
        
        if(Validator.createCriminalDataIsValid(name, panname, age, birthPlace, 
                lastPlace, award, information, photo)) {
            //create new criminal
            int ageParsed = Integer.parseInt(age);
            int awardParsed = Integer.parseInt(award);
            Optional<Integer> idCreated = logic.createPerson(name, panname, 
                    ageParsed, photo, birthPlace, lastPlace, awardParsed, 
                    information, isCriminal);
            
            if (idCreated.isPresent()) {
                page = ConfigurationManager.getProperty(MAIN_CONTR);    
            } else {
                requestContent.setRequestAttribute("errorOccured",
                    "create.errorOccured");
                page = ConfigurationManager.getProperty(CREATE_PAGE);
            }
        } else {
            requestContent.setRequestAttribute("errorOccured",
                    "create.errorOccured");
            requestContent.setRequestAttribute("errorCreateMessage",
                    "message.invalideData");
            page = ConfigurationManager.getProperty(CREATE_PAGE);
        }
        return page;
    }
}
