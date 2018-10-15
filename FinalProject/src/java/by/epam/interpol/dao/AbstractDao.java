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
 * @author Ilia Leonov
 * abstract class with basic dao methods
 * @param <T> any entity 
 */
public abstract class AbstractDao <T> {
    private final static Logger LOG = LogManager.getLogger(AbstractDao.class);

    protected static WrapperConnector connection;
    
    /**
     *
     * @param connection connerction to database from pool connection
     */
    public AbstractDao(WrapperConnector connection){
        this.connection = connection;
    }
    
    /**
     *
     * @return all entities
     * @throws ProjectException
     */
    public abstract List<T> findAll() throws ProjectException;

    /**
     *
     * @param id entities id
     * @return entity or null if entity not found wrapped in Optional class
     * @throws ProjectException
     */
    public abstract Optional<T> findEntityById(int id) throws ProjectException;

    /**
     *
     * @param id entities id
     * @return true if deleted and false if not
     * @throws ProjectException
     */
    public abstract boolean delete(int id) throws ProjectException;

    /**
     *
     * @param entity some entity
     * @return true if successfully deleted and false if not
     * @throws ProjectException
     */
    public abstract boolean delete(T entity) throws ProjectException;

    /**
     *
     * @param entity some entity
     * @return entities id or null if entity not created wrapped on Optional class
     * @throws ProjectException
     */
    public abstract Optional<Integer> create(T entity) throws ProjectException;

    /**
     *
     * @param entity some entity
     * @return entity or null if entity not updated wrapped in Optional class
     * @throws ProjectException
     */
    public abstract Optional<T> update(T entity) throws ProjectException;
    
    /**
     *
     * @param statement some statement
     */
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
