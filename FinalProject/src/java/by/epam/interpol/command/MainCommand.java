/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command;

import by.epam.interpol.command.util.ActionCommand;
import by.epam.interpol.command.util.ConfigurationManager;
import by.epam.interpol.controller.SessionRequestContent;
import by.epam.interpol.entity.Person;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.logic.PersonLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ilia Leonov
 * points to go to database, get last added person and return to the main page
 * with information about
 */
public class MainCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(MainCommand.class);
    
    private static final String PERSON = "person";
    
    private static final String MAIN_PAGE = "path.page.main";
    
    private PersonLogic logic;

    /**
     *
     * @param logic  logic of control at persons
     */
    public MainCommand(PersonLogic logic) {
        this.logic = logic;
    }

    @Override
    public String execute(SessionRequestContent requestContent) throws ProjectException {
        LOG.debug("****IN MAIN COMMAND******");
        
        String page = null;
        Person person = logic.findWantedOrMissed();
        page = ConfigurationManager.getProperty(MAIN_PAGE);
        requestContent.setRequestAttribute(PERSON, person);
        return page;
    }
    
}
