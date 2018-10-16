/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.tag.custom;

import by.epam.interpol.filter.LocaleFilter;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ilia Leonov
 */
@SuppressWarnings("serial")
public class ConversionTag extends BodyTagSupport {
    private static final Logger LOG = LogManager.getLogger(LocaleFilter.class);
    
    private static final String LOCALE = "locale";
    private static final String RU_LOCALE = "ru_RU";
    private static final String EN_LOCALE = "en_US";
    
    Double number;

    /**
     *
     * @return constant int that points what to do afteer
     * @throws JspException
     */
    @Override
    public int doAfterBody() throws JspException {

        ServletRequest request = pageContext.getRequest();
        Locale locale = (Locale) request.getAttribute(LOCALE);

        BodyContent content = null;
        String body = null;
            
        if (locale.toString().equals(RU_LOCALE)) {
            content = this.getBodyContent();
            body = content.getString();
            number = Double.parseDouble(body.trim());
            body = Double.toString(number * 66);
        }  else if (locale.toString().equals(EN_LOCALE)) {
            content = this.getBodyContent();
            body = content.getString().trim();
        }

        JspWriter out = content.getEnclosingWriter();
        try {
            out.write(body);
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
