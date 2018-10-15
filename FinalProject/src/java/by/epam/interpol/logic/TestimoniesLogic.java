/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.logic;

import by.epam.interpol.daoimpl.PersonDaoImpl;
import by.epam.interpol.daoimpl.TestimonyDaoImpl;
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
        Optional<Integer> idCreated;
        try {
            idCreated = testimonyDao.create(testimony);
        } finally {
            pool.putbackConnection(connection);
        }
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
        Optional<Integer> idPerson; 
        try {
            idPerson = personDao.findIdByNamePanname(name, panname);
        } finally {
            pool.putbackConnection(connection);
        }
        
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
            try {
                idCreated = testimonyDao.create(testimony);
            } finally {
                pool.putbackConnection(connection);
            }
        }
        return idCreated;
    }

    public List<Testimony> findUserTestimonies(Integer idUser, int pageSize, 
            int offset, boolean isCriminal) throws ProjectException {
        LOG.debug("entering find testimonies for user logic");

        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        TestimonyDaoImpl testimonyDao = new TestimonyDaoImpl(connection);
        List<Testimony> testimonyList;
        try {
            testimonyList = testimonyDao.findAmountOfEntities(pageSize, 
                offset, idUser, isCriminal);
        } finally {
            pool.putbackConnection(connection);
        }
        return testimonyList;
    }

    public List<Testimony> findAdminTestimonies(int pageSize, int offset, 
            boolean isCriminal) throws ProjectException {
        LOG.debug("entering find testimonies for admin logic");
        LOG.debug("pageSize " + pageSize + " offset ");

        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        TestimonyDaoImpl testimonyDao = new TestimonyDaoImpl(connection);
        List<Testimony> testimonyList;
        try {
            testimonyList 
                    = testimonyDao.findAmountOfEntities(pageSize, offset, isCriminal);
        } finally {
            pool.putbackConnection(connection);
        } 
        return testimonyList;
    }
    
    public List<Testimony> findUserArchive(Integer idUser, int pageSize, int offset) 
            throws ProjectException {
        LOG.debug("entering find for page logic");

        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        TestimonyDaoImpl testimonyDao = new TestimonyDaoImpl(connection);
        List<Testimony> testimonyList;
        try {
            testimonyList 
                    = testimonyDao.findAmountOfEntities(pageSize, offset, idUser);
        } finally {
            pool.putbackConnection(connection);
        }
        return testimonyList;
    }

    public Optional<Testimony> assignPoints(int id, int points) 
            throws ProjectException {
        LOG.debug("entering assigning points logic");

        Testimony testimony = new Testimony();
        testimony.setId(id);
        testimony.setPoints(points);
        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        TestimonyDaoImpl testimonyDao = new TestimonyDaoImpl(connection);
        Optional<Testimony> optTestimony;
        try {
            optTestimony = testimonyDao.update(testimony);
        } finally {
            pool.putbackConnection(connection);
        }
        return optTestimony;
    }

    public List<Testimony> findAdminArchive(int pageSize, int offset) 
            throws ProjectException {
        LOG.debug("entering find for page logic");

        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        TestimonyDaoImpl testimonyDao = new TestimonyDaoImpl(connection);
        List<Testimony> testimonyList;
        try {
            testimonyList = testimonyDao.findAmountOfEntities(pageSize, offset);
        } finally {
            pool.putbackConnection(connection);
        }       
        return testimonyList;
    }
}
