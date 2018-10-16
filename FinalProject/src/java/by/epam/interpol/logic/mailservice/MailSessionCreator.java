/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.logic.mailservice;

import by.epam.interpol.logic.MailLogic;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ilia Leonov
 */
public class MailSessionCreator {
    private final static Logger LOG = LogManager.getLogger(MailLogic.class);
    
    private String smtpHost;
    private String smtpPort;
    private String userName;
    private String userPassword;
    private Properties sessionProperties;
    private final static ResourceBundle configProperties 
            = ResourceBundle.getBundle("resources\\mail");
    static {
        LOG.debug("configProperties is not null");
        if (configProperties == null) {
            LOG.debug("configProperties is null");
            throw new RuntimeException();
        }
    }

    /**
     *
     */
    public MailSessionCreator() {
        smtpHost = configProperties.getString("mail.smtp.host");
        smtpPort = configProperties.getString("mail.smtp.port");
        userName = configProperties.getString("mail.user.name");
        userPassword = configProperties.getString("mail.user.password");
        
        // setting session properties
        sessionProperties = new Properties();
        sessionProperties.setProperty("mail.transport.protocol", "smtp");
        sessionProperties.setProperty("mail.host", smtpHost);
        sessionProperties.put("mail.smtp.auth", "true");
        sessionProperties.put("mail.smtp.port", smtpPort);
        sessionProperties.put("mail.smtp.socketFactory.port", smtpPort);
        sessionProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        sessionProperties.put("mail.smtp.socketFactory.fallback", "false");
        sessionProperties.setProperty("mail.smtp.quitwait", "false");
    }

    /**
     *
     * @return session by default properties
     */
    public Session createSession() {
        LOG.debug("creating session");
        LOG.debug(userName);
        LOG.debug(userPassword);
        
        Session s = Session.getDefaultInstance(sessionProperties,
                new Authenticator() { 
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, userPassword);
                    }
                });
        LOG.debug("returned new sesseion");
        return s;
    }
}
