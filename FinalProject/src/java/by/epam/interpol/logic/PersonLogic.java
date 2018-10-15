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
 * @author Администратор
 */
public class PersonLogic {
    private static final Logger LOG = LogManager.getLogger(PersonLogic.class);
    
    public PersonLogic() {
    }

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
