/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.dao;

import by.epam.interpol.entity.Testimony;
import by.epam.interpol.pool.WrapperConnector;

/**
 *
 * @author Ilia Leonov
 * abstract class to control testimony operations
 */
public abstract class AbstractTestimonyDao extends AbstractDao<Testimony>{

    /**
     *
     * @param connection connection to database
     */
    public AbstractTestimonyDao(WrapperConnector connection) {
        super(connection);
    }
}