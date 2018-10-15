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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ilia Leonov
 * shows test result
 */
public class TestResultCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(TestResultCommand.class);
    
    private static final String TERM = "term";
    private static final int QUESTIONS_AMOUNT = 7;
    private static final int TOTAL_PARAMS_COUNT = 8;
    private static final double COEFFICIENT = 2.2;
    private static final String TEST_PAGE = "path.page.test";
    private static final String TEST_RESULT_PAGE = "path.page.testRes";
    
    public TestResultCommand() {
    }

    @Override
    public String execute(SessionRequestContent requestContent) throws ProjectException {
        LOG.debug("****IN TESTRESULT COMMAND******");
        
        String page = null;
        Map<String, String[]> parameters = requestContent.getRequestParameters();
        int sum = 0;
        Set<String> parameterKeys = parameters.keySet();
        if(parameterKeys.size()< TOTAL_PARAMS_COUNT) {
            LOG.debug("test.errorOccured");
            requestContent.setRequestAttribute("testError", "test.errorOccured");
            page = ConfigurationManager.getProperty(TEST_PAGE); 
        } else {
            Iterator <String> parameterKeysIterator = parameterKeys.iterator();
            parameterKeysIterator.next();
            while (parameterKeysIterator.hasNext()) {
                String paramValue = parameterKeysIterator.next();
                sum += Integer.parseInt(parameters.get(paramValue)[0]);

            }
            int psychTerm = (int) ((sum - QUESTIONS_AMOUNT)*COEFFICIENT);
            requestContent.setRequestAttribute(TERM, psychTerm);
            page = ConfigurationManager.getProperty(TEST_RESULT_PAGE); 
        }
        return page;
    }    
}
