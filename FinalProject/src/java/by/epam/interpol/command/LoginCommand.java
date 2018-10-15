/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command;

import by.epam.interpol.command.util.ActionCommand;
import by.epam.interpol.command.util.ConfigurationManager;
import by.epam.interpol.command.util.Validator;
import by.epam.interpol.logic.UserLogic;
import by.epam.interpol.controller.SessionRequestContent;
import by.epam.interpol.entity.User;
import by.epam.interpol.exception.ProjectException;
import java.util.Base64;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ilia Leonov
 * points to login user
 */
public class LoginCommand implements ActionCommand{
    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);
    
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String MAIL = "mail";
    private static final String LOCALE = "locale";
    private static final String ROLE = "role";
    private static final String USER = "user";
    private static final String ADMIN = "admin";
    
    private static final String WELCOME_PAGE = "path.page.welcome";
    private static final String LOGIN_PAGE = "path.page.login";
    
    private UserLogic logic;

    /**
     *
     * @param logic logic of control at user
     */
    public LoginCommand(UserLogic logic) {
        this.logic = logic;
    }
    
    @Override
    public String execute(SessionRequestContent requestContent) throws ProjectException {
        LOG.debug("****IN LOGIN COMMAND******");
        
        String page = null;
        String login = requestContent.getRequestParameter(LOGIN);
        String password = requestContent.getRequestParameter(PASSWORD);
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        if (requestContent.getRequestAttribute(ROLE) != null) {
            page = ConfigurationManager.getProperty(LOGIN_PAGE);
        }else if (Validator.isLoginPasswordValide(login, password)) {
            //find id by login-password
            Optional<Integer> optId = logic.findId(login, encodedPassword);
            if (optId.isPresent()) {
                requestContent.activateSession();
                requestContent.setSessionRequestAttribute(LOGIN, login);

                int id = optId.get();
                requestContent.setSessionRequestAttribute(ID, id);

                //find user by id
                Optional<User> optUser = logic.findUserById(id);
                User user = optUser.get();
                requestContent.setSessionRequestAttribute(NAME, user.getName());
                requestContent.setSessionRequestAttribute(MAIL, user.getMail());
                requestContent.setSessionRequestAttribute(LOCALE, 
                        requestContent.getRequestAttribute(LOCALE));
                if (user.isAdmin()) {
                    requestContent.setSessionRequestAttribute(ROLE, ADMIN);
                    requestContent.setRequestAttribute(ROLE, ADMIN);
                } else {
                    requestContent.setSessionRequestAttribute(ROLE, USER);
                    requestContent.setRequestAttribute(ROLE, USER);
                }
                page = ConfigurationManager.getProperty(WELCOME_PAGE);    
            } else {
                requestContent.setRequestAttribute("errorOccured",
                    "login.errorOccured");
                requestContent.setRequestAttribute("errorLoginPassMessage",
                    "message.loginerror");
                page = ConfigurationManager.getProperty(LOGIN_PAGE);
            }
        } else {
            requestContent.setRequestAttribute("errorOccured",
                    "login.errorOccured");
            requestContent.setRequestAttribute("errorLoginPassMessage",
                    "message.invalideData");
            page = ConfigurationManager.getProperty(LOGIN_PAGE);
        }
        return page;
    }
}
