/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command;

import by.epam.interpol.command.ActionCommand;
import by.epam.interpol.command.util.ConfigurationManager;
import by.epam.interpol.command.util.Validator;
import by.epam.interpol.controller.SessionRequestContent;
import by.epam.interpol.entity.Person;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Администратор
 */
public class TestCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(CreateCommand.class);
    
    public TestCommand() {
        
    }

    @Override
    public String execute(SessionRequestContent requestContent) {
        LOG.debug("****IN TEST COMMAND******");
        return ConfigurationManager.getProperty("path.page.region"); 
    } 
}
