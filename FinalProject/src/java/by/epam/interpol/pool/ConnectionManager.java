/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.pool;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Администратор
 */
 class ConnectionManager {
    private final static Logger LOG = LogManager.getLogger(ConnectionManager.class);
    
    private final static ResourceBundle resource = ResourceBundle.getBundle("resources\\database");
    static {
        if (resource == null) {
            throw new RuntimeException();
        }
    }
    final static String URL = resource.getString("db.url");
    final static String USER = resource.getString("db.user");
    final static String PASSWORD = resource.getString("db.password");
    
    //    at first timeout
    static int timeOut = Integer.parseInt(resource.getString("db.timeOut"));
    
//    ar first number of avaliable connections if another wasn't assigned is
    static int maxNumberOfActiveConnections 
            = Integer.parseInt(resource.getString("db.numberOfActiveConnections"));  
    
//    at first number of idle connections if another wasn't assigned is    
    static int maxNumberOfIdleConnections = 
            Integer.parseInt(resource.getString("db.numberOfIdleConnections"));    
    
    public static WrapperConnector getConnector(){
        return new WrapperConnector(URL, USER, PASSWORD);
    }
    
    public static void registerDriver(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch(InstantiationException e) {
            throw new RuntimeException("Haven't managed to register driver", e);
        } catch(ClassNotFoundException e) {
            throw new RuntimeException("Haven't managed to register driver", e);
        } catch(IllegalAccessException e) {
            throw new RuntimeException("Haven't managed to register driver", e);
        }  
    }
    
    public static void deregisterDrivers(){
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()){
            Driver driver = drivers.nextElement();
            try {
                LOG.debug(String.format("deregistering jdbc driver: %s", driver));
                DriverManager.deregisterDriver(driver);
            } catch (SQLException ex) {
                throw new RuntimeException("Haven't managed to deregister driver", ex);
            }
        }
    }    
}
