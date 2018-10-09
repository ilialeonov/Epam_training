/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.dao;

import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.pool.WrapperConnector;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Администратор
 */
public abstract class AbstractDao <T> {
    private final static Logger LOG = LogManager.getLogger(AbstractDao.class);
    
    protected static WrapperConnector connection;
    
    public AbstractDao(WrapperConnector connection){
        this.connection = connection;
    }
    
    public abstract List<T> findAll() throws ProjectException;
    public abstract Optional<T> findEntityById(int id) throws ProjectException;
    public abstract boolean delete(int id) throws ProjectException;
    public abstract boolean delete(T entity) throws ProjectException;
    public abstract Optional<Integer> create(T entity) throws ProjectException;
    public abstract Optional<T> update(T entity) throws ProjectException;
    
    public void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
            LOG.error("exception closing statement", ex);
        }
    }
}
