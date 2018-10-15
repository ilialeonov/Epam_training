/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.logic;

/**
 *
 * @author Администратор
 */

import by.epam.interpol.logic.mailservice.MailSessionCreator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MailLogic {
    private final static Logger LOG = LogManager.getLogger(MailLogic.class);
    
    private static final Session MAIL_SESSION 
            = (new MailSessionCreator()).createSession();
  
    private MimeMessage message;
    private String sendToEmail;
    private String mailSubject;
    private String mailText;
    
    
    public MailLogic(String sendToEmail,
            String mailSubject, String mailText) {
        this.sendToEmail = sendToEmail;
        this.mailSubject = mailSubject;
        this.mailText = mailText;
    }
    
    private void init() {
        MAIL_SESSION.setDebug(true);
        
        message = new MimeMessage(MAIL_SESSION);
        try {
            message.setSubject(mailSubject);
            message.setContent(mailText, "text/html");
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));
        } catch (AddressException e) {
            LOG.error("AddressException:Couldn't have sent at this address: " + sendToEmail);
        } catch (MessagingException e) {
            LOG.error("MessagingException:Couldn't have sent at this address: " + sendToEmail);
        }
    }
    
    public void send() {
        init();
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            LOG.error("MessagingException:Couldn't have sent at this address: " + sendToEmail + e);
        }
    }
}
