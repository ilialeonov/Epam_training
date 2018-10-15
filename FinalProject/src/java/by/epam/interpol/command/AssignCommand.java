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
import by.epam.interpol.entity.Testimony;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.logic.TestimoniesLogic;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Leonov Ilia
 * takes control at archive of found persons
 */
public class AssignCommand implements ActionCommand{
    private static final Logger LOG = LogManager.getLogger(AssignCommand.class);
    
    private static final String ID = "id";
    private static final String POINTS = "points";
    private static final String CRIMINAL = "isCriminal";
    private static final String MISSED = "isMissed";
    private static final String TESTIMONIES_CONTR = "controller.testimoniesAdmin";
    private static final String PERSON_QUERY = "&person=";
    
    private TestimoniesLogic logic;
    private PersonDefiner definer;
    
    /**
     *
     * @param logic logic of control at testimonies
     */
    public AssignCommand(TestimoniesLogic logic) {
        this.logic = logic;
    }

    @Override
    public String execute(SessionRequestContent requestContent) throws ProjectException {
        LOG.debug("****IN TESTIMONIES COMMAND******");

        definer = PersonDefiner.getInstance();
        boolean isCriminal = definer.defineIfPersonIsWanted(requestContent);
        
        String idStr = requestContent.getRequestParameter(ID);
        String pointsStr = requestContent.getRequestParameter(POINTS);

        if(Validator.pointsIsValid(pointsStr)) {
            
            int idParsed = Integer.parseInt(idStr);
            int pointsParsed = Integer.parseInt(pointsStr);
            
            Optional<Testimony> optTestimony = logic.assignPoints(idParsed,
                    pointsParsed);
            if (!optTestimony.isPresent()) {
                requestContent.setRequestAttribute("errorOccured",
                    "testify.errorOccured");
            requestContent.setRequestAttribute("errorAssignMessage",
                    "assign.invalideId");
            }
        } else {

            requestContent.setRequestAttribute("errorOccured",
                    "testify.errorOccured");
            requestContent.setRequestAttribute("errorAssignMessage",
                    "assign.invalideData");
        }
        LOG.debug("total success");
        String page = ConfigurationManager.getProperty(TESTIMONIES_CONTR)
                    + PERSON_QUERY + (isCriminal ? CRIMINAL : MISSED); 
            
        return page;
    }
}
