/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command;

import by.epam.interpol.command.util.ConfigurationManager;
import by.epam.interpol.command.util.Validator;
import by.epam.interpol.controller.SessionRequestContent;
import by.epam.interpol.entity.User;
import by.epam.interpol.logic.UserLogic;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Администратор
 */
public class LogoutCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);
    
    private static final String ROLE = "role";
    
    private UserLogic logic;
    
    public LogoutCommand(UserLogic logic) {
        this.logic = logic;
    }

    @Override
    public String execute(SessionRequestContent requestContent) {
        LOG.debug("****IN LOGOUT COMMAND******");
        
        String page = null;
 
        if (requestContent.getRequestAttribute(ROLE) != null) {
            LOG.debug("user is registered");
            requestContent.deactivateSession();
            requestContent.removeAttributes();
            page = ConfigurationManager.getProperty("controller.main"); 

        } else {
            requestContent.setRequestAttribute("errorLoginPassMessage",
                    "message.logoutError");
            page = ConfigurationManager.getProperty("path.page.login");
        }
        return page;
    }
    
}
