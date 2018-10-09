/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command;

import by.epam.interpol.command.util.ConfigurationManager;
import by.epam.interpol.controller.SessionRequestContent;
import by.epam.interpol.exception.ProjectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Администратор
 */
public class RedirectCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(RedirectCommand.class);
    
    private static final String PERSON = "person";
    private static final String EDIT = "edit";
    private static final String CREATE = "create";
    private static final String REGION = "region";
    private static final String TEST = "test";
    private static final String TESTIFY = "testify";
    private static final String OP = "op";
    
    @Override
    public String execute(SessionRequestContent requestContent) throws ProjectException {
        LOG.debug("Redirect command");
        String page = null;
        String operation = requestContent.getRequestParameter(OP);
        if(operation.equals(EDIT)) {
            page = ConfigurationManager.getProperty("path.page.editBy");
        } else if (operation.equals(CREATE)){
            page = ConfigurationManager.getProperty("path.page.create");
        } else if (operation.equals(REGION)){
            page = ConfigurationManager.getProperty("path.page.choseRegion");
        } else if (operation.equals(TEST)){
            page = ConfigurationManager.getProperty("path.page.test");
        } else if (operation.equals(TESTIFY)) {
            page = ConfigurationManager.getProperty("path.page.chosePerson");
        }
        return page;
    }
}
