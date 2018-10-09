/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.dao;

import by.epam.interpol.entity.User;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.pool.WrapperConnector;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Администратор
 */
 public abstract class AbstractUserDao<User>  extends AbstractDao<User>{

    public AbstractUserDao(WrapperConnector connection) {
        super(connection);
    }

    
    public  abstract boolean isFreeLogin(String login) throws ProjectException;
    
    public  abstract boolean isFreeMail(String mail) throws ProjectException;
    
    public  abstract Optional<Integer> findIdIfRegistered(String login, 
            String password) throws ProjectException;
    
    public abstract Optional<Integer> create(User user, String password) 
            throws ProjectException;
    
 }
