/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command;

import by.epam.interpol.command.util.ActionCommand;
import by.epam.interpol.command.util.ConfigurationManager;
import by.epam.interpol.command.util.Validator;
import by.epam.interpol.controller.SessionRequestContent;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.logic.MailLogic;
import by.epam.interpol.logic.UserLogic;
import java.util.Base64;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ilia Leonov
 * registers user
 */
public class RegisterCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(RegisterCommand.class);
    
    private static final String NAME = "name";
    private static final String MAIL = "mail";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_REPEAT = "passwordRepeat";
    
    private static final String ID = "id";
    private static final String LOCALE = "locale";
    private static final String ROLE = "role";
    private static final String USER = "user";
    private static final String REGISTRATION = "registration";
    private static final String REG_MESSAGE = "you are registered";
    
    
    private static final String REGISTER_PAGE = "path.page.register";
    private static final String MAIN_CONTR = "controller.main";
    
    private UserLogic logic;

    /**
     *
     * @param logic logic of control at user
     */
    public RegisterCommand(UserLogic logic) {
        this.logic = logic;
    }

    @Override
    public String execute(SessionRequestContent requestContent) throws ProjectException {
        LOG.debug("*****IN REGISTER COMMAND*****");
        
        String page;
        String name = requestContent.getRequestParameter(NAME);
        String mail = requestContent.getRequestParameter(MAIL);
        String login = requestContent.getRequestParameter(LOGIN);
        String password = requestContent.getRequestParameter(PASSWORD);
        String passwordRepeat = requestContent.getRequestParameter(PASSWORD_REPEAT);
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        LOG.debug(encodedPassword);
        LOG.debug(password);
        LOG.debug(passwordRepeat);
        
        if (password.equals(passwordRepeat) 
                && Validator.areUserDataValid(name, mail, login, password)) {
            LOG.debug("users datas are valid");
            
            //checks if there is already such login in database;
            //checks if there is already such password in database;
            if(logic.isFreeLogin(login) && logic.isFreeMail(mail)) {
                
                //creates user and returns his ID
                Optional<Integer> optId = logic.registerUser(name, mail, login, encodedPassword);
                if (optId.isPresent()) {
                    requestContent.activateSession();
                    requestContent.setSessionRequestAttribute(LOGIN, login);

                    int id = optId.get();
                    requestContent.setSessionRequestAttribute(ID, id);

                    //setting user attributes to session
                    requestContent.setSessionRequestAttribute(NAME, name);
                    requestContent.setSessionRequestAttribute(MAIL, mail);
                    requestContent.setSessionRequestAttribute(LOCALE, 
                            requestContent.getRequestAttribute(LOCALE));
                    requestContent.setSessionRequestAttribute(ROLE, USER);

                    page = ConfigurationManager.getProperty(MAIN_CONTR);
                    MailLogic mailLogic = new MailLogic(mail, REGISTRATION, REG_MESSAGE);
                    mailLogic.send();
                } else {
                    requestContent.setRequestAttribute("errorOccured",
                        "register.errorOccured");
                    requestContent.setRequestAttribute("errorRegisterMessage",
                        "message.registererror");
                    page = ConfigurationManager.getProperty(REGISTER_PAGE);
                }
            } else {
                LOG.debug("Some of both is taken");
                requestContent.setRequestAttribute("errorOccured",
                        "register.errorOccured");
                requestContent.setRequestAttribute("errorRegisterMessage",
                        "message.loginMailError");
                page = ConfigurationManager.getProperty(REGISTER_PAGE);
            }
        } else {
            requestContent.setRequestAttribute("errorOccured",
                    "register.errorOccured");
            requestContent.setRequestAttribute("errorRegisterMessage",
                    "message.invalideData");
            page = ConfigurationManager.getProperty(REGISTER_PAGE);
        }
        return page;
    } 
}
