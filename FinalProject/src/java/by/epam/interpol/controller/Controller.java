package by.epam.interpol.controller;

import by.epam.interpol.command.ActionCommand;
import by.epam.interpol.command.util.ActionFactory;
import by.epam.interpol.exception.ProjectException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



@WebServlet("/controller")
@MultipartConfig(fileSizeThreshold = 128*128,maxFileSize = 1024*1024*10,maxRequestSize = 1024*1024*50)
public class Controller extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    
    private static final String ERROR_PAGE = "";//todo
    
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
            response.sendRedirect(ERROR_PAGE);
        }      
    }
        
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        doPost(req, resp);
    }    
}
