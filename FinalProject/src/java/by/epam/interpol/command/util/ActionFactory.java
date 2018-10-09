package by.epam.interpol.command.util;
import by.epam.interpol.controller.Controller;
import by.epam.interpol.command.ActionCommand;
import by.epam.interpol.command.CommandEnum;
import by.epam.interpol.exception.CommandException;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.controller.SessionRequestContent;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ActionFactory {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    
    public ActionCommand defineCommand(SessionRequestContent requestContent) 
            throws ProjectException {   
        LOG.debug("defining command");
        
        ActionCommand current;

        // extracting name
        String action = requestContent.getRequestParameter("command");
        LOG.debug(action);
        if (action == null || action.isEmpty()) {
            // why do we check if it must be setted anyway?
            // if not setted
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
