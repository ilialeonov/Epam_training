/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.filter;

import by.epam.interpol.controller.Controller;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Администратор
 */
@WebFilter ( urlPatterns = {"/jsp/*", "/controller", "/jsploged/*"} )
public class LocaleFilter implements Filter{
    private static final Logger LOG = LogManager.getLogger(LocaleFilter.class);
    
    private static final String LOCALE = "locale";
    private static final String ROLE = "role";
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

//    sets locale attribute to request, registered users can change their locale 
//    thence for registered users who have session we use their locale
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
            FilterChain chain) throws IOException, ServletException {
        LOG.debug("in locale filter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        LOG.debug(session);
        if(session == null) {            
            LOG.debug("SESSION = NULL");            
            if (request.getAttribute(LOCALE) == null) {
                Locale locale = request.getLocale();
                request.setAttribute(LOCALE, locale);
            }
            LOG.debug("user locale is " + request.getAttribute(LOCALE));
        } else {
            Locale locale = (Locale) session.getAttribute(LOCALE);
            request.setAttribute(LOCALE, locale);
            String role = (String) session.getAttribute(ROLE);
            request.setAttribute(ROLE, role);
            LOG.debug("WE HAVE SESSION NOW");
            LOG.debug("user locale is " + session.getAttribute(LOCALE));
            LOG.debug("user role is " + session.getAttribute(ROLE));
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
