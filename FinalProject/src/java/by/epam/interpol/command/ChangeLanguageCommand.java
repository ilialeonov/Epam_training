/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command;

import by.epam.interpol.command.util.ActionCommand;
import by.epam.interpol.command.util.ConfigurationManager;
import by.epam.interpol.controller.SessionRequestContent;
import by.epam.interpol.exception.ProjectException;
import java.util.Locale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Администратор
 */
public class ChangeLanguageCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(ChangeLanguageCommand.class);
    
    private static final String LOCALE = "locale";
    private static final String CHOSEN_LOCALE = "chosenLocale";
    private static final String RU = "ru_RU";
    private static final String MAIN_CONTR = "controller.main";
    
    private static Locale localeRu = new Locale("ru", "RU"); 
    private static Locale localeEn = new Locale("en", "US"); 
    
    @Override
    public String execute(SessionRequestContent requestContent) throws ProjectException {
        LOG.debug("ChandeLanguage  command");
        
        String chosenLocaleStr = requestContent.getRequestParameter(CHOSEN_LOCALE);
        
        Locale changed = (chosenLocaleStr.equals(RU)) ? localeRu : localeEn;
        requestContent.setSessionRequestAttribute(LOCALE, changed);
        LOG.debug("LOCALE IS CHANGED");
        return ConfigurationManager.getProperty(MAIN_CONTR);
    }
}
