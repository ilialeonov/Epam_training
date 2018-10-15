/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.dao;

import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.pool.WrapperConnector;
import java.util.Optional;

/**
 *
 * @author Ilia Leonov
 * abstract class to control user operations
 * @param <User> is type of class for this class
 */
 public abstract class AbstractUserDao <User>  extends AbstractDao<User>{

    /**
     *
     * @param connection connection to database
     */
    public AbstractUserDao(WrapperConnector connection) {
        super(connection);
    }

    /**
     *
     * @param login user login
     * @return true if login is free in DB, else false
     * @throws ProjectException
     */
    public  abstract boolean isFreeLogin(String login) throws ProjectException;
    
    /**
     *
     * @param mail user's mail
     * @return true if mail is free in DB, else false
     * @throws ProjectException
     */
    public  abstract boolean isFreeMail(String mail) throws ProjectException;
    
    /**
     *
     * @param login user,s login
     * @param password user's password
     * @return user's id or null if not found wrapped in Optional class
     * @throws ProjectException
     */
    public  abstract Optional<Integer> findIdIfRegistered(String login, 
            String password) throws ProjectException;
    
    /**
     *
     * @param user user
     * @param password user's password
     * @return user's id or null if not found wrapped in Optional class
     * @throws ProjectException
     */
    public abstract Optional<Integer> create(User user, String password) 
            throws ProjectException;
    
 }
