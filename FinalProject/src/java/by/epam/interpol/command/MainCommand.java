/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command;

import by.epam.interpol.command.util.ConfigurationManager;
import by.epam.interpol.controller.SessionRequestContent;
import by.epam.interpol.entity.Person;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.logic.PersonLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Администратор
 */
class MainCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);
    
    private PersonLogic logic;

    public MainCommand(PersonLogic logic) {
        this.logic = logic;
    }

    @Override
    public String execute(SessionRequestContent requestContent) throws ProjectException {
        LOG.debug("****IN MAIN COMMAND******");
        
        String page = null;
        Person person = logic.findWantedOrMissed();
        LOG.debug("Someone found");
        page = ConfigurationManager.getProperty("path.page.main");
        requestContent.setRequestAttribute("person", person);
        LOG.debug("role is: " + requestContent.getRequestAttribute("role"));
        return page;
    }
    
}
