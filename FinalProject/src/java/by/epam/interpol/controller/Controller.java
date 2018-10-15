package by.epam.interpol.controller;

import by.epam.interpol.command.util.ActionCommand;
import by.epam.interpol.command.util.ActionFactory;
import by.epam.interpol.command.util.ConfigurationManager;
import by.epam.interpol.exception.ProjectException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author IIlia Leonov
 * servlet parsing HEAD request and definin get or post void to call
 * and calls the void
 */
public class Controller extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    
    private static final String ERROR_PAGE = "path.page.error";
    
    /**
     *
     * @param request request from user
     * @param response response to user
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        LOG.debug("command was posted");
        LOG.debug(request.getParameter("login"));
        try {
            ActionFactory client = new ActionFactory();
            SessionRequestContent requestContent = new SessionRequestContent();
            requestContent.extractValues(request);
            ActionCommand command = client.defineCommand(requestContent);
            LOG.debug(command);
            String page = command.execute(requestContent);
            requestContent.insertAttributes(request); 
            request.getRequestDispatcher(page).forward(request, response);
        } catch (ProjectException ex) {
            response.sendRedirect(ConfigurationManager.getProperty(ERROR_PAGE));
        }      
    }
        
    /**
     *
     * @param req request from user
     * @param resp response to user
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        doPost(req, resp);
    }    
}
