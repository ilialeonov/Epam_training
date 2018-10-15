/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command.util;

import by.epam.interpol.command.RegisterCommand;
import java.awt.image.BufferedImage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ilia Leonov
 * validates any information entered by user with given rules
 */
public class Validator {
    private static final Logger LOG = LogManager.getLogger(RegisterCommand.class);
    
    private static final String LOGIN_STRING_PATTERN = "\\w{3,12}";
    private static final String NAME_STRING_PATTERN = "[A-Z]([a-zA-Z]){1,10}";
    private static final String AGE_PATTERN = "[1-9][0-9]";
    private static final String POINTS_PATTERN = "([1-9][0-9])|(0)";
    private static final String AWARD_PATTERN = "[1-9][0-9]{1,7}";
    private static final String PASSWORD_STRING_PATTERN = "\\w{4,}";
    private static final String ID_PATTERN = "[1-9][0-9]{0,7}";
    private static final String MAIL_STRING_PATTERN 
            = "(\\w{5,})@(\\w+\\.)([a-z]{2,6})";

    private static final Pattern PATTERN_LOGIN 
            = Pattern.compile(LOGIN_STRING_PATTERN); 
    
    private static final Pattern PATTERN_NAME
            = Pattern.compile(NAME_STRING_PATTERN); 
    
    private static final Pattern PATTERN_PASSWORD 
            = Pattern.compile(PASSWORD_STRING_PATTERN);
    
    private static final Pattern PATTERN_MAIL 
            = Pattern.compile(MAIL_STRING_PATTERN);
    
    private static final Pattern PATTERN_AGE 
            = Pattern.compile(AGE_PATTERN);
    
    private static final Pattern PATTERN_POINTS
            = Pattern.compile(POINTS_PATTERN);
    
    private static final Pattern PATTERN_ID 
            = Pattern.compile(ID_PATTERN);
    
    private static final Pattern PATTERN_AWARD 
            = Pattern.compile(AWARD_PATTERN);
    
    /**
     *
     * @param login user's login
     * @param password user's password
     * @return true if login, password are valid and false if they are not
     */
    public static boolean isLoginPasswordValide(String login, String password) {
        LOG.debug("checking if user/password are valid");
        
        boolean isValide;
        
        if (login == null || password == null) {
            isValide = false;
        } else {
            Matcher matcherLogin = PATTERN_LOGIN.matcher(login);
            Matcher matcherPassword = PATTERN_PASSWORD.matcher(password);
            if (matcherLogin.matches() && matcherPassword.matches()) {
                isValide = true;
            } else {
                isValide = false;
            }
        }
        return isValide;
    }

    /**
     *
     * @param name user's name
     * @param mail user's mail
     * @param login user's login
     * @param password user's password
     * @return true if entered datas are valid and false if they are not
     */
    public static boolean areUserDataValid(String name, String mail, String login, String password) {
        LOG.debug("checking if register datas are valid");
        
        boolean isValide;
        
        if (name == null || mail == null 
                || login == null || password == null) {
            isValide = false;
        } else {
            Matcher matcherLogin = PATTERN_LOGIN.matcher(login);
            Matcher matcherPassword = PATTERN_PASSWORD.matcher(password);
            Matcher matcherMail = PATTERN_MAIL.matcher(mail);
            
            if (name != null && !name.isEmpty() && matcherLogin.matches() 
                    && matcherPassword.matches() && matcherMail.matches()) {
                isValide = true;
            } else {
                isValide = false;
            }
        }
        return isValide;
    }

    /**
     *
     * @param name person's name
     * @param panname person's panname
     * @param age person's age
     * @param birthPlace person's birthPlace
     * @param lastPlace person's last seen place
     * @param award award assigned for person
     * @param information information known at the moment
     * @param photo person's photo
     * @return true if entered datas are valid and false if they are not 
     */
    public static boolean createCriminalDataIsValid(String name, String panname, 
            String age, String birthPlace, String lastPlace, 
            String award, String information, BufferedImage photo) {
        LOG.debug("checking if register datas are valid");
        
        boolean isValide;
        
        if (information == null || name == null || panname == null || age == null
                || birthPlace == null || lastPlace == null || award == null || photo == null) {
            isValide = false;
        } else {
            Matcher matcherName = PATTERN_NAME.matcher(name);
            Matcher matcherPanname = PATTERN_NAME.matcher(panname);
            Matcher matcherBirthPlace = PATTERN_NAME.matcher(birthPlace);
            Matcher matcherLastPlace = PATTERN_NAME.matcher(lastPlace);
            Matcher matcherAge = PATTERN_AGE.matcher(age);
            Matcher matcherAward = PATTERN_AWARD.matcher(award);   

            if (matcherName.matches() && matcherPanname.matches()
                    && matcherBirthPlace.matches() && matcherLastPlace.matches()
                    && matcherAge.matches() && matcherAward.matches()) {
                isValide = true;
            } else {
                isValide = false;
            }
        }
        return isValide;
    }
    
    /**
     *
     * @param id any id
     * @return true if entered datas are valid and false if they are not
     */
    public static boolean idIsValid(String id) {
        LOG.debug("checking if id is valid");
        
        boolean isValide;
        
        if (id == null) {
            isValide = false;
        } else {
            Matcher matcherID = PATTERN_ID.matcher(id);

            if (matcherID.matches()) {
                isValide = true;
            } else {
                isValide = false;
            }
        }
        return isValide;
    }
    
    /**
     *
     * @param name user's name
     * @param panname user's panname
     * @return  true if entered datas are valid and false if they are not
     */
    public static boolean namePannameAreValid(String name, String panname) {
        LOG.debug("checking if name/panname are valid");
        
        boolean isValide;
        
        if (name == null || panname == null) {
            isValide = false;
        } else {
            Matcher matcherName = PATTERN_NAME.matcher(name);
            Matcher matcherPanname = PATTERN_NAME.matcher(panname);
            
            if (matcherName.matches() && matcherPanname.matches()) {
                isValide = true;
            } else {
                isValide = false;
            }
        }
        return isValide;
    }

    /**
     *
     * @param region any region
     * @return true if entered datas are valid and false if they are not
     */
    public static boolean isRegionValid(String region) {
        LOG.debug("checking if region is valid");
        
        boolean isValide;
        
        if (region == null || region.isEmpty()) {
            isValide = false;
        } else {
            Matcher matcherName = PATTERN_NAME.matcher(region);
    
            if (matcherName.matches()) {
                isValide = true;
            } else {
                isValide = false;
            }
        }
        return isValide;
    }

    /**
     *
     * @param testimony any testimony
     * @return true if entered datas are valid and false if they are not
     */
    public static boolean testimonyIsValid(String testimony) {
        LOG.debug("checking if name/panname are valid");
        
        boolean isValide;
        
        if (testimony == null || testimony.length() < 8 
                || testimony.length() > 10240) {
            isValide = false;
        } else {
            isValide = true;
        }
        return isValide;
    }

    /**
     *
     * @param pointsStr points assigned for ingormation
     * @return true if entered datas are valid and false if they are not
     */
    public static boolean pointsIsValid(String pointsStr) {
        LOG.debug("checking if points is valid");
        
        boolean isValide;
        
        if (pointsStr == null) {
            isValide = false;
        } else {
            Matcher matcherPoints = PATTERN_POINTS.matcher(pointsStr);

            if (matcherPoints.matches()) {
                isValide = true;
            } else {
                isValide = false;
            }
        }
        return isValide;
    }

    /**
     *
     * @param name person's name
     * @param panname person's panname
     * @param age person's age
     * @param birthPlace person's birthPlace
     * @param lastPlace person's last seen palce
     * @param award person's assigned award
     * @param information information known at the moment
     * @return true if entered datas are valid and false if they are not
     */
    public static boolean createCriminalDataIsValid(String name, String panname, 
            String age, String birthPlace, String lastPlace, String award, 
            String information)  {
        LOG.debug("checking if register datas are valid");
        
        boolean isValide;
        
        if (information == null || name == null || panname == null || age == null
                || birthPlace == null || lastPlace == null || award == null) {
            isValide = false;
        } else {
            Matcher matcherName = PATTERN_NAME.matcher(name);
            Matcher matcherPanname = PATTERN_NAME.matcher(panname);
            Matcher matcherBirthPlace = PATTERN_NAME.matcher(birthPlace);
            Matcher matcherLastPlace = PATTERN_NAME.matcher(lastPlace);
            Matcher matcherAge = PATTERN_AGE.matcher(age);
            Matcher matcherAward = PATTERN_AWARD.matcher(award);   

            if (matcherName.matches() && matcherPanname.matches()
                    && matcherBirthPlace.matches() && matcherLastPlace.matches()
                    && matcherAge.matches() && matcherAward.matches()) {
                isValide = true;
            } else {
                isValide = false;
            }
        }
        return isValide;
    }
}
