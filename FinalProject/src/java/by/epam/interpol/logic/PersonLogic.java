/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.logic;

import by.epam.interpol.dao.daoimpl.PersonDaoImpl;
import by.epam.interpol.entity.Person;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.exception.TimeLimitExceededException;
import by.epam.interpol.pool.ConnectionPool;
import by.epam.interpol.pool.WrapperConnector;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;
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
        Optional<Integer> idCreated = personDao.create(person);
        pool.putbackConnection(connection);
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
        Person person = personDao.findLast();

        pool.putbackConnection(connection);
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
        Optional<Person> person = personDao.findEntityById(id);

        pool.putbackConnection(connection);
        return person;
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
        Optional<Person> person = personDao.findEntityByNamePanname(name, panname);
        pool.putbackConnection(connection);
        return person;
    }

    public Optional<Person> editPerson(int id, String name, String panname, int ageParsed, 
            BufferedImage photo, String birthPlace, String lastPlace, 
            int awardParsed, String information, boolean status) throws ProjectException {
        
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
        person.setStatus(status);
        
        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        PersonDaoImpl personDao = new PersonDaoImpl(connection);
        Optional<Person> optPerson = personDao.update(person);
        pool.putbackConnection(connection);
        return optPerson;
    }
    
    public Optional<Person> editPersonNoPhoto(int id, String name, String panname, int ageParsed, 
            String birthPlace, String lastPlace, 
            int awardParsed, String information, boolean status) throws ProjectException {
        
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
        person.setStatus(status);
        
        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = null;

        try {
            connection = pool.getConnection();
        } catch (TimeLimitExceededException ex) {
            throw new ProjectException("time limit for waiting exceeded", ex);
        }
        
        PersonDaoImpl personDao = new PersonDaoImpl(connection);
        Optional<Person> optPerson = personDao.updateNoPhoto(person);
        pool.putbackConnection(connection);
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
        List<Person> personList 
                = personDao.findAmountOfEntities(pageSize, offset, isCriminal);
        pool.putbackConnection(connection);
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
        List<Person> personList 
                = personDao.findAmountOfEntities(pageSize, offset);
        pool.putbackConnection(connection);
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
        List<Person> personList 
                = personDao.findByRegion(pageSize, offset, region);
        pool.putbackConnection(connection);
        return personList;
    }

}
