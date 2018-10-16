/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.logic;

import by.epam.interpol.controller.Controller;
import by.epam.interpol.daoimpl.UserDaoImpl;
import by.epam.interpol.entity.User;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.exception.TimeLimitExceededException;
import by.epam.interpol.pool.ConnectionPool;
import by.epam.interpol.pool.WrapperConnector;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Администратор
 */
public class UserLogic {
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    /**
     *
     * @param login user's login
     * @param password user's password
     * @return user's id or null if operation failed wrapped in Optioanl class
     * @throws ProjectException
     */
    public Optional<Integer> findId(String login, String password) throws ProjectException {
        LOG.debug("entering findId login logic");
        
        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        UserDaoImpl userDao = new UserDaoImpl(connection);
        Optional<Integer> id;
        try {
            id = userDao.findIdIfRegistered(login, password);
        } finally {
            pool.putbackConnection(connection);
        }
        return id;
    }
    
    /**
     *
     * @param id user's id
     * @return user or null if operation failed wrapped in Optioanl class
     * @throws ProjectException
     */
    public Optional<User> findUserById(int id) throws ProjectException {
        LOG.debug("entering findId login logic");
        
        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        UserDaoImpl userDao = new UserDaoImpl(connection);
        Optional<User> user;
        try {
            user = userDao.findEntityById(id);
        } finally {
            pool.putbackConnection(connection);
        }
        return user;
    }
    
    /**
     *
     * @param login user's login
     * @return true if login is free, else returns false
     * @throws ProjectException
     */
    public boolean isFreeLogin(String login) throws ProjectException{
        LOG.debug("checks if login is free");
        
        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting has exceeded", ex);
        }
        
        UserDaoImpl userDao = new UserDaoImpl(connection);     
        boolean isFree;
        try {
            isFree = userDao.isFreeLogin(login);
        } finally {
            pool.putbackConnection(connection);
        }
        return isFree;     
    }
    
    /**
     *
     * @param mail user's mail
     * @return true if mail is free, else returns false
     * @throws ProjectException
     */
    public boolean isFreeMail(String mail) throws ProjectException{
        LOG.debug("checks if mail is free");
        
        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting has exceeded", ex);
        }
        
        UserDaoImpl userDao = new UserDaoImpl(connection);     
        boolean isFree;
        try {
            isFree = userDao.isFreeMail(mail);
        } finally {
            pool.putbackConnection(connection);
        }
        return isFree;     
    }
    
    /**
     *
     * @param name user's name
     * @param mail user's mail
     * @param login user's login
     * @param password user's password
     * @return user's id or null if operation failed wrapped in Optioanl class
     * @throws ProjectException
     */
    public Optional<Integer> registerUser(String name, String mail, 
            String login, String password) throws ProjectException {
        LOG.debug("registring user");
        
        User user = new User();
        user.setName(name);
        user.setLogin(login);
        user.setMail(mail);
        user.setAdmin(false);
        
        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        UserDaoImpl userDao = new UserDaoImpl(connection);     
        Optional<Integer> optIdUser;
        try {
            optIdUser = userDao.create(user, password);
        } finally {
            pool.putbackConnection(connection);
        }
        return optIdUser;
    }
}
