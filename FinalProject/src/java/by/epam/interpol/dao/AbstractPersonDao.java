/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.dao;

import by.epam.interpol.entity.Person;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.pool.WrapperConnector;
import java.util.Optional;

/**
 *
 * @author Ilia Leonov
 * 
 */
public abstract class AbstractPersonDao extends AbstractDao<Person>{

    /**
     *
     * @param connection
     */
    public AbstractPersonDao(WrapperConnector connection) {
        super(connection);
    }
    
    /**
     *
     * @return searched person
     * @throws ProjectException
     */
    public abstract Person findLast() throws ProjectException;
    
    /**
     *
     * @param name person's name
     * @param panname person's panname
     * @return person or null if not found wrapped in Optional class
     * @throws ProjectException
     */
    public abstract Optional<Person> findEntityByNamePanname(
            String name, String panname) throws ProjectException;
    
    /**
     *
     * @param person person to update
     * @return person or null if not found wrapped in Oprtional class
     * @throws ProjectException
     */
    public abstract Optional<Person> updateNoPhoto(Person person) 
            throws ProjectException;
    
}