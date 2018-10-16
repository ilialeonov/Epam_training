/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.logic;

import by.epam.interpol.daoimpl.PersonDaoImpl;
import by.epam.interpol.daoimpl.TestimonyDaoImpl;
import by.epam.interpol.daoimpl.UserDaoImpl;
import by.epam.interpol.entity.Person;
import by.epam.interpol.entity.User;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.exception.TimeLimitExceededException;
import by.epam.interpol.pool.ConnectionPool;
import by.epam.interpol.pool.WrapperConnector;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 *
 * @author Ilia Leonov
 * logic of person
 */
public class PersonLogic {
    private static final Logger LOG = LogManager.getLogger(PersonLogic.class);
    
    /**
     *
     */
    public PersonLogic() {
    }

    /**
     *
     * @param name person's name
     * @param panname person's panname
     * @param age person's age
     * @param photo person's photo
     * @param birthPlace person's birthPlace
     * @param lastPlace person's lastPlace
     * @param award award for person
     * @param information person's information
     * @param isCriminal person's status
     * @return person's id or null if not found wrapped in Optional class
     * @throws ProjectException
     */
    public Optional<Integer> createPerson(String name, String panname, int age, 
            BufferedImage photo, String birthPlace, String lastPlace, int award, 
            String information, boolean isCriminal) throws ProjectException {
        
        LOG.debug("entering create criminal logic");
        
        Person person = new Person();
        person.setName(name);
        person.setPanname(panname);
        person.setAge(age);
        person.setPhoto(photo);
        person.setBirthPlace(birthPlace);
        person.setLastPlace(lastPlace);
        person.setAward(award);
        person.setInformation(information);
        person.setIsCriminal(isCriminal);
        
        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        PersonDaoImpl personDao = new PersonDaoImpl(connection);
        Optional<Integer> idCreated;
        try {        
            idCreated = personDao.create(person);
        } finally {
            pool.putbackConnection(connection);
        }
        return idCreated;
    }

    /**
     *
     * @return some person
     * @throws ProjectException
     */
    public Person findWantedOrMissed() throws ProjectException {
        LOG.debug("entering find any criminal logic");

        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        PersonDaoImpl personDao = new PersonDaoImpl(connection);
        Person person;
        try {
            person = personDao.findLast();
        } finally {
            pool.putbackConnection(connection);
        }
        return person;
    }
    
    /**
     *
     * @param id person's id
     * @return person or null wrapped in Optional class
     * @throws ProjectException
     */
    public Optional<Person> findEntityById(int id) throws ProjectException {
        LOG.debug("entering find by id logic");

        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        PersonDaoImpl personDao = new PersonDaoImpl(connection);
        Optional<Person> person; 
        try {
            person = personDao.findEntityById(id);
        } finally {
            pool.putbackConnection(connection);
        }
        return person;
    }  
    
    /**
     *
     * @param id person's id
     * @return person or null if not found wrapped in Optional class
     * @throws ProjectException
     */
    public Optional<Person> setFound(int id) throws ProjectException {
        LOG.debug("entering find by id logic");

        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        connection.setAutocommit(false);
        PersonDaoImpl personDao = new PersonDaoImpl(connection);
        Optional<Person> optPerson; 
        try {
            optPerson = personDao.findEntityById(id);
            if(optPerson.isPresent() && optPerson.get() != null) {
                optPerson = personDao.updateSetFoundById(optPerson.get());
                int award = optPerson.get().getAward();
                makePayments(connection, id, award);
            }
        } catch (ProjectException ex) {
            connection.rollBack();
            throw new ProjectException();
        } finally {
            connection.commit();
        }
        pool.putbackConnection(connection);
        return optPerson;
    }
    
    /**
     *
     * @param name person's name
     * @param panname person's panname
     * @return person or null if not found wrapped in Optional class
     * @throws ProjectException
     */
    public Optional<Person> setFound(String name, String panname) 
            throws ProjectException {
        LOG.debug("entering find by id logic");

        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        connection.setAutocommit(false);
        PersonDaoImpl personDao = new PersonDaoImpl(connection);
        Optional<Person> optPerson; 
        try {
            optPerson = personDao.findEntityByNamePanname(name, panname);
            if(optPerson.isPresent()) {
                optPerson = personDao.updateSetFoundById(optPerson.get());
                int award = optPerson.get().getAward();
                makePayments(connection, optPerson.get().getId(), award);
            }
        } catch (ProjectException ex) {
            connection.rollBack();
            throw new ProjectException();
        } finally {
            connection.commit();
        }
        pool.putbackConnection(connection);
        return optPerson;
    }

    /**
     *
     * @param name person's name
     * @param panname person's panname
     * @return person or null if not found wrapped in Optional class
     * @throws ProjectException
     */
    public Optional<Person> findEntityByNamePanname(String name, String panname) throws ProjectException {
        LOG.debug("entering find by name panname logic");

        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        PersonDaoImpl personDao = new PersonDaoImpl(connection);
        Optional<Person> person; 
        try {
            person = personDao.findEntityByNamePanname(name, panname);
        } finally {
            pool.putbackConnection(connection);
        }
        return person;
    }

    /**
     *
     * @param id person's id
     * @param name person's name
     * @param panname person's panname
     * @param ageParsed person's age
     * @param photo person's photo
     * @param birthPlace person's birthPlace
     * @param lastPlace person's last seen place
     * @param awardParsed person's award garanted
     * @param information person's information at the moment
     * @return person or null if not found wrapped in Optional class
     * @throws ProjectException
     */
    public Optional<Person> editPerson(int id, String name, String panname, int ageParsed, 
            BufferedImage photo, String birthPlace, String lastPlace, 
            int awardParsed, String information) throws ProjectException {
        
        LOG.debug("entering edit person logic");
        
        Person person = new Person();
        person.setId(id);
        person.setName(name);
        person.setPanname(panname);
        person.setAge(ageParsed);
        person.setPhoto(photo);
        person.setBirthPlace(birthPlace);
        person.setLastPlace(lastPlace);
        person.setAward(awardParsed);
        person.setInformation(information);

        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        PersonDaoImpl personDao = new PersonDaoImpl(connection);
        Optional<Person> optPerson;
        try {
            optPerson = personDao.update(person);
        } finally {
            pool.putbackConnection(connection);
        }
        return optPerson;
    }
    
    /**
     * @param id person's id
     * @param name person's name
     * @param panname person's panname
     * @param ageParsed person's age
     * @param birthPlace person's birthPlace
     * @param lastPlace person's last seen place
     * @param awardParsed person's award garanted
     * @param information person's information at the moment
     * @return person or null if not found wrapped in Optional class
     * @throws ProjectException
     */
    public Optional<Person> editPersonNoPhoto(int id, String name, String panname, int ageParsed, 
            String birthPlace, String lastPlace, 
            int awardParsed, String information) throws ProjectException {
        
        LOG.debug("entering edit person logic");
        
        Person person = new Person();
        person.setId(id);
        person.setName(name);
        person.setPanname(panname);
        person.setAge(ageParsed);
        person.setBirthPlace(birthPlace);
        person.setLastPlace(lastPlace);
        person.setAward(awardParsed);
        person.setInformation(information);

        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        PersonDaoImpl personDao = new PersonDaoImpl(connection);
        Optional<Person> optPerson; 
        try {
            optPerson= personDao.updateNoPhoto(person);
        } finally {
            pool.putbackConnection(connection);
        }
        return optPerson;
    }

    /**
     *
     * @param pageSize amount of  elements on page
     * @param offset offset to watch elements in DB
     * @param isCriminal person's status
     * @return list of persons
     * @throws ProjectException
     */
    public List<Person> findForPage(int pageSize, int offset, boolean isCriminal) 
            throws ProjectException {
        LOG.debug("entering find for page logic");

        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        PersonDaoImpl personDao = new PersonDaoImpl(connection);
        List<Person> personList; 
        try {
            personList = personDao.findAmountOfEntities(pageSize, offset, isCriminal);
        } finally {
            pool.putbackConnection(connection);
        }
        return personList;
    }
    
    /**
     *
     * @param pageSize amount of  elements on page
     * @param offset offset to watch elements in DB
     * @return list of persons
     * @throws ProjectException
     */
    public List<Person> findForPage(int pageSize, int offset) 
            throws ProjectException {
        LOG.debug("entering find for page logic");

        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        PersonDaoImpl personDao = new PersonDaoImpl(connection);
        List<Person> personList; 
        try {
            personList= personDao.findAmountOfEntities(pageSize, offset);
        } finally {
            pool.putbackConnection(connection);
        }   
        return personList;
    }

    /**
     *
     * @param pageSize amount of  elements on page
     * @param offset offset to watch elements in DB
     * @param region chosen region
     * @return list of persons
     * @throws ProjectException
     */
    public List<Person> findByRegion(int pageSize, int offset, String region) throws ProjectException {
        LOG.debug("entering find by region logic");

        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        PersonDaoImpl personDao = new PersonDaoImpl(connection);
        List<Person> personList; 
        try {
            personList = personDao.findByRegion(pageSize, offset, region);
        } finally {
            pool.putbackConnection(connection);
        }
        return personList;
    }

    /**
     *
     * @param id person's id
     * @return true if deleted and false if not
     * @throws ProjectException
     */
    public boolean delete(int id) throws ProjectException {
        LOG.debug("entering delete logic");

        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        PersonDaoImpl personDao = new PersonDaoImpl(connection);
        boolean deleted; 
        try {
            deleted = personDao.delete(id);
        } finally {
            pool.putbackConnection(connection);
        }   
        return deleted;
        
    }
    
    /**
     *
     * @param name person's name
     * @param panname person's panname
     * @return true if deleted and false if not
     * @throws ProjectException
     */
    public boolean delete(String name, String panname) throws ProjectException {
        LOG.debug("entering delete logic");

        Person person = new Person();
        person.setName(name);
        person.setPanname(panname);
        
        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        PersonDaoImpl personDao = new PersonDaoImpl(connection);
        boolean deleted; 
        try {
            deleted = personDao.delete(person);
        } finally {
            pool.putbackConnection(connection);
        }   
        return deleted;
    }
        
    private void makePayments(WrapperConnector connection, int id, int award) 
            throws ProjectException {
        TestimonyDaoImpl testimonyDao = new TestimonyDaoImpl(connection);
        
        int sumPoints = testimonyDao.findSumPoints(id);
        
        if (sumPoints > 0) {
            double koeff = (double) award / sumPoints;
            Map <Integer, Integer> mapSumPoints = testimonyDao.findEachUserSumPoints(id);

            UserDaoImpl userDao = new UserDaoImpl(connection);

            Set<Integer> keysSet = mapSumPoints.keySet();
            Iterator<Integer> iterator = keysSet.iterator();

            while (iterator.hasNext()) {
                Integer idUser = iterator.next();
                Integer points = mapSumPoints.get(idUser);
                User user = new User();
                user.setId(idUser);
                double money = (double) Math.round(points * koeff * 100) / 100;
                user.setMoney(money);
                userDao.updateMoney(user);
            }  
        }
    }

}
