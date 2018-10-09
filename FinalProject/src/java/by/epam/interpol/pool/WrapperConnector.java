/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WrapperConnector {
    private final static Logger LOG = LogManager.getLogger(WrapperConnector.class);
       
    private Connection connection;
    
    public WrapperConnector(String url, String user, String password) {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            LOG.error("Error with access to DB", ex);
        }
    }
    
    public void setAutocommit(boolean isAuto) {
        try {
            connection.setAutoCommit(isAuto);
        } catch (SQLException ex) {
            LOG.error("error setting autocommit");
        }
    }
    
    public void commit(){
        try {
            connection.commit();
        } catch (SQLException ex) {
            LOG.error("SQL error commiting");
        }
    }
    
    public void rollBack(){
        try {
            connection.rollback();
        } catch (SQLException ex) {
            LOG.error("SQL error with rollback");
        }
    }
    
    public boolean getAutocommit() {
        try {
            return connection.getAutoCommit();
        } catch (SQLException ex) {
            LOG.error("error getting autocommit");
        }
        return false;
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException{
        if (connection != null) {
            PreparedStatement pStatement = connection.prepareStatement(sql);
            if (pStatement != null) {
                return pStatement;
            }
        }
        throw new SQLException();
    }
        
    public Statement getStatement() throws SQLException {
        if (connection != null) {
            Statement statement = connection.createStatement();
            if (statement != null) {
                return statement;
            }
        }
        throw new SQLException("connection or statement is null");
    }
    
    public void closePreparedStatement(PreparedStatement pStatement) {
        if (pStatement != null) {
            try {
                pStatement.close();
            } catch (SQLException ex) {
                LOG.error("An exception closing preparedStatement", ex);
            }
        }
    }
    
    public void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOG.error("An exception closing statement", e);
            }
        }
    }
    
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.error("An exception closing connection", e);
            }
        }
    }

    public PreparedStatement prepareStatement(String CREATE_USER, 
            int RETURN_GENERATED_KEYS) throws SQLException {
        if (connection != null) {    
            PreparedStatement stmt = 
                    connection.prepareStatement(CREATE_USER, RETURN_GENERATED_KEYS);
            if (stmt != null) {
                return stmt;
            }
        }
        throw new SQLException();    
    }
}
