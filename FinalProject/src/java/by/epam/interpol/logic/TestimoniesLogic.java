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
 * @author Ilia Leonov
 */
public class TestimoniesLogic {
    private static final Logger LOG = LogManager.getLogger(TestimoniesLogic.class);
    
    /**
     *
     */
    public TestimoniesLogic() {
        
    }

    /**
     *
     * @param idPerson person's id
     * @param idUser iser's id
     * @param information information about person by person's id
     * @return person's id or null if not found wrapped in Optional class
     * @throws ProjectException
     */
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

    /**
     *
     * @param name person's name
     * @param panname person's panname
     * @param idUser user's id
     * @param information information about person by person's id
     * @return person's id or null if not found wrapped in Optional class
     * @throws ProjectException
     */
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

    /**
     *
     * @param idUser user's id
     * @param pageSize amount of elements at the page
     * @param offset offset to watch elements in DB
     * @param isCriminal person's status
     * @return List of testimonies for user's persons he testified
     * @throws ProjectException
     */
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

    /**
     *
     * @param pageSize amount of elements on page
     * @param offset offset to watch testimonies in DB
     * @param isCriminal person's status
     * @return List of testimonies for admin 
     * @throws ProjectException
     */
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
    
    /**
     *
     * @param idUser user's id
     * @param pageSize amount of elements on the page
     * @param offset offset to watch elements in DB
     * @return lisr of user's testimonies in archive 
     * @throws ProjectException
     */
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

    /**
     *
     * @param id testimonies id
     * @param points points assigned for the testimony
     * @return testimony or null if not found wrapped in Optional class
     * @throws ProjectException
     */
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

    /**
     *
     * @param pageSize amount of elements at the page
     * @param offset offset to watch elements in DB
     * @return list of testimonies
     * @throws ProjectException
     */
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
