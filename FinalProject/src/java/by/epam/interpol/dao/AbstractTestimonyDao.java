/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.dao;

import by.epam.interpol.entity.Person;
import by.epam.interpol.entity.Testimony;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.pool.WrapperConnector;
import java.util.Optional;

/**
 *
 * @author Администратор
 */
public abstract class AbstractTestimonyDao extends AbstractDao<Testimony>{

    public AbstractTestimonyDao(WrapperConnector connection) {
        super(connection);
    }
}