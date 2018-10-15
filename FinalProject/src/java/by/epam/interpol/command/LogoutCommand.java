/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command;

import by.epam.interpol.command.util.ActionCommand;
import by.epam.interpol.command.util.ConfigurationManager;
import by.epam.interpol.controller.SessionRequestContent;
import by.epam.interpol.logic.UserLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ilia Leonov
 * points to logout
 */
public class LogoutCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(LogoutCommand.class);
    
    private static final String MAIN_CONTR = "controller.main";
    private static final String LOGIN_PAGE = "path.page.login";
    
    private static final String ROLE = "role";
    
    private UserLogic logic;
    
    /**
     *
     * @param logic logic of control at user
     */
    public LogoutCommand(UserLogic logic) {
        this.logic = logic;
    }

    @Override
    public String execute(SessionRequestContent requestContent) {
        LOG.debug("****IN LOGOUT COMMAND******");
        
        String page = null;
 
        if (requestContent.getRequestAttribute(ROLE) != null) {
            requestContent.deactivateSession();
            requestContent.removeAttributes();
            page = ConfigurationManager.getProperty(MAIN_CONTR); 

        } else {
            requestContent.setRequestAttribute("errorLoginPassMessage",
                    "message.logoutError");
            page = ConfigurationManager.getProperty(LOGIN_PAGE);
        }
        return page;
    }
    
}
