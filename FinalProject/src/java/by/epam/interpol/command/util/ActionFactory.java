package by.epam.interpol.command.util;
import by.epam.interpol.controller.Controller;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.controller.SessionRequestContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ilia Leonov
 */
public class ActionFactory {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    
    /**
     *
     * @param requestContent consists requestParameters, requestAttributes,
     * sessionAttributes and session information
     * @return chosen command
     * @throws ProjectException
     */
    public ActionCommand defineCommand(SessionRequestContent requestContent) 
            throws ProjectException {   

        ActionCommand current;

        // extracting name
        String action = requestContent.getRequestParameter("command");
        if (action == null || action.isEmpty()) {
            throw new RuntimeException();
        }

        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            throw new ProjectException("there is no such command", e);
        }
        return current;
    }
}
