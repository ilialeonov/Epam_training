/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command;

import by.epam.interpol.command.util.ActionCommand;
import by.epam.interpol.command.util.ConfigurationManager;
import by.epam.interpol.logic.UserLogic;
import by.epam.interpol.controller.SessionRequestContent;
import by.epam.interpol.entity.User;
import by.epam.interpol.exception.ProjectException;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ilia Leonov
 * shows to user information about his balance
 */
public class PurseCommand implements ActionCommand{
    private static final Logger LOG = LogManager.getLogger(PurseCommand.class);

    private static final String ID = "id";

    private static final String PURSE_PAGE = "path.page.purse";

    private UserLogic logic;

    /**
     *
     * @param logic  logic of control at user
     */
    public PurseCommand(UserLogic logic) {
        this.logic = logic;
    }
    
    @Override
    public String execute(SessionRequestContent requestContent) throws ProjectException {
        LOG.debug("****IN PURSE COMMAND******");
        
        int id = (Integer) requestContent.getSessionRequestAttribute(ID);

        Optional<User> optUser = logic.findUserById(id);
        if (optUser.isPresent()) {
            requestContent.setRequestAttribute("user", optUser.get());   
        } else {
            requestContent.setRequestAttribute("errorOccured",
                "login.errorOccured");
            requestContent.setRequestAttribute("errorMessage",
                "message.purseerror");
        }
        return ConfigurationManager.getProperty(PURSE_PAGE);
    }
}
