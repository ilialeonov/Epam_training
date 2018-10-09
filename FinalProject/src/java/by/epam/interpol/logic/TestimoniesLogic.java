/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.logic;

import by.epam.interpol.dao.daoimpl.PersonDaoImpl;
import by.epam.interpol.dao.daoimpl.TestimonyDaoImpl;
import by.epam.interpol.entity.Person;
import by.epam.interpol.entity.Testimony;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.exception.TimeLimitExceededException;
import by.epam.interpol.pool.ConnectionPool;
import by.epam.interpol.pool.WrapperConnector;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Администратор
 */
public class TestimoniesLogic {
    private static final Logger LOG = LogManager.getLogger(TestimoniesLogic.class);
    
    public TestimoniesLogic() {
        
    }

    public Optional<Integer> testifyById(int idPerson, 
            int idUser, String information) throws ProjectException {
        
        LOG.debug("entering testify logic");
        
        Testimony testimony = new Testimony();
        testimony.setPersonId(idPerson);
        testimony.setUserId(idUser);
        testimony.setTestimony(information);
        
        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        TestimonyDaoImpl testimonyDao = new TestimonyDaoImpl(connection);
        Optional<Integer> idCreated = testimonyDao.create(testimony);
        pool.putbackConnection(connection);
        return idCreated;
    }

    public Optional<Integer> testifyByNamePanname(String name, String panname, 
            Integer idUser, String information) throws ProjectException {
        
        LOG.debug("entering testify logic");
        
        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        PersonDaoImpl personDao = new PersonDaoImpl(connection);
        Optional<Integer> idPerson 
                = personDao.findIdByNamePanname(name, panname);
        pool.putbackConnection(connection);
        
        Testimony testimony = new Testimony();
        
        Optional<Integer> idCreated = null;
        
        if (idPerson.isPresent()) {
            testimony.setPersonId(idPerson.get());
            testimony.setUserId(idUser);
            testimony.setTestimony(information);

            pool = ConnectionPool.getInstance();
            connection = null;

            try {
                connection = pool.getConnection();
            } catch (TimeLimitExceededException ex) {
                throw new ProjectException("time limit for waiting exceeded", ex);
            }

            TestimonyDaoImpl testimonyDao = new TestimonyDaoImpl(connection);
            idCreated = testimonyDao.create(testimony);
            pool.putbackConnection(connection);
        }
        return idCreated;
    }

    public List<Testimony> findForPage(Integer idUser, int pageSize, int offset, 
            boolean isCriminal) throws ProjectException {
        LOG.debug("entering find for page logic");

        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        TestimonyDaoImpl testimonyDao = new TestimonyDaoImpl(connection);
        List<Testimony> testimonyList 
                = testimonyDao.findAmountOfEntities(pageSize, 
                        offset, idUser, isCriminal);
        pool.putbackConnection(connection);
        return testimonyList;
    }

    public List<Testimony> findForPage(Integer idUser, int pageSize, int offset) throws ProjectException {
        LOG.debug("entering find for page logic");

        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        TestimonyDaoImpl testimonyDao = new TestimonyDaoImpl(connection);
        List<Testimony> testimonyList 
                = testimonyDao.findAmountOfEntities(pageSize, offset, idUser);
        pool.putbackConnection(connection);
        return testimonyList;
    }
    
}
