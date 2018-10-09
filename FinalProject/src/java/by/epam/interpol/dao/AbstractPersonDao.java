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
 * @author Администратор
 */
public abstract class AbstractPersonDao extends AbstractDao<Person>{

    public AbstractPersonDao(WrapperConnector connection) {
        super(connection);
    }
    
    public abstract Person findLast() throws ProjectException;
    
    public abstract Optional<Person> findEntityByNamePanname(
            String name, String panname) throws ProjectException;
    
    public abstract Optional<Person> updateNoPhoto(Person person) 
            throws ProjectException;
    
}