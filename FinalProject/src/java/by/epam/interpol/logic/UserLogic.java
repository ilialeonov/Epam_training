/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.logic;

import by.epam.interpol.controller.Controller;
import by.epam.interpol.dao.daoimpl.UserDaoImpl;
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
        Optional<Integer> id = userDao.findIdIfRegistered(login, password);
        pool.putbackConnection(connection);
        return id;
    }
    
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
        Optional<User> user = userDao.findEntityById(id);
        pool.putbackConnection(connection);
        return user;
    }
    
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
        boolean isFree = userDao.isFreeLogin(login);
        pool.putbackConnection(connection);
        return isFree;     
    }
    
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
        boolean isFree = userDao.isFreeMail(mail);
        pool.putbackConnection(connection);
        return isFree;     
    }
    
    public Optional<Integer> registerUser(String name, String mail, String login, String password) throws ProjectException {
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
        Optional<Integer> optIdUser = userDao.create(user, password);      
        pool.putbackConnection(connection);
        return optIdUser;
    }
}
