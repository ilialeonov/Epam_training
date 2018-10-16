/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.filter;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ilia Leonov
 * filter that sends information from session to request 
 * at the pages where session is not allowed
 */

public class LocaleFilter implements Filter{
    private static final Logger LOG = LogManager.getLogger(LocaleFilter.class);
    
    private static final String LOCALE = "locale";
    private static final String ROLE = "role";
    
    /**
     *
     * @param filterConfig filter configuration
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

//    sets locale attribute to request, registered users can change their locale 
//    thence for registered users who have session we use their locale
    
    /**
     *
     * @param request user's request
     * @param response user's response
     * @param chain chain of filters
     * @throws IOException
     * @throws ServletException
     */
        
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, 
            FilterChain chain) throws IOException, ServletException {
        LOG.debug("in locale filter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);
        
        LOG.debug(session);
        if(session == null) {                     
            if (request.getAttribute(LOCALE) == null) {
                Locale locale = request.getLocale();
                request.setAttribute(LOCALE, locale);
            }
        } else {
            Locale locale = (Locale) session.getAttribute(LOCALE);
            request.setAttribute(LOCALE, locale);
            String role = (String) session.getAttribute(ROLE);
            request.setAttribute(ROLE, role);
        }
        chain.doFilter(request, response);
    }

    /**
     *
     */
    @Override
    public void destroy() {
    }
}
