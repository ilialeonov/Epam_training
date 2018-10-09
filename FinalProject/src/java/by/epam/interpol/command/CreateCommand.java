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
class CreateCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(CreateCommand.class);
    
    private static final String NAME = "name";
    private static final String PANNAME = "panname";
    private static final String AGE = "age";
    private static final String BIRTH_REGION = "birthReg";
    private static final String LAST_SEEN_REGION = "lastSeenReg";
    private static final String AWARD = "award";
    private static final String INFORMATION = "information";
    private static final String NO = "No";
    
    private PersonLogic logic;
    private PersonDefiner definer;

    public CreateCommand(PersonLogic logic) {
        this.logic = logic;
        definer = PersonDefiner.getInstance();
    }
    
    @Override
    public String execute(SessionRequestContent requestContent) throws ProjectException {
        LOG.debug("****IN CREATE COMMAND******");
        
        String page = null;
        BufferedImage photo = requestContent.getRequestImage();

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
        boolean isCriminal = definer.defineIfPersonIsWanted(requestContent);
        
        if(Validator.createCriminalDataIsValid(name, panname, age, birthPlace, 
                lastPlace, award, information, NO)) {
            //create new criminal
            int ageParsed = Integer.parseInt(age);
            int awardParsed = Integer.parseInt(award);
            Optional<Integer> idCreated = logic.createPerson(name, panname, ageParsed, photo, 
                    birthPlace, lastPlace, awardParsed, information, isCriminal);
            
            
            if (idCreated.isPresent()) {
                page = ConfigurationManager.getProperty("controller.main");    
            } else {
                LOG.info("couldn't create criminal");
                requestContent.setRequestAttribute("errorOccured",
                    "create.errorOccured");
                page = ConfigurationManager.getProperty("path.page.create");
            }
        } else {
            requestContent.setRequestAttribute("errorOccured",
                    "create.errorOccured");
            requestContent.setRequestAttribute("errorCreateMessage",
                    "message.invalideData");
            page = ConfigurationManager.getProperty("path.page.create");
        }
        return page;
    }
}
