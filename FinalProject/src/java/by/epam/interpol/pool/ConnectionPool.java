/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.pool;

import by.epam.interpol.exception.TimeLimitExceededException;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Ilia Leonov
 */
public class ConnectionPool {
    private final static Logger LOG = LogManager.getLogger(ConnectionPool.class);
    
    private final static ResourceBundle resource 
            = ResourceBundle.getBundle("resources\\database");
    static {
        if (resource == null) {
            throw new RuntimeException();
        }
    }
//    at first timeout 
    private static int timeOut = ConnectionManager.timeOut;
    
//    ar first number of avaliable connections if another wasn't assigned is
    private static int maxNumberOfActiveConnections 
            = ConnectionManager.maxNumberOfActiveConnections;
    
//    at first number of idle connections if another wasn't assigned is    
    private static int maxNumberOfIdleConnections = 
            ConnectionManager.maxNumberOfIdleConnections;
    
    private static volatile ConnectionPool instance;
    private  BlockingQueue<WrapperConnector> activeConnections;
    private  BlockingQueue<WrapperConnector> idleConnections;
    private static int counter = 0;
    private static Lock lock = new ReentrantLock();
    
    private ConnectionPool() {
        this(maxNumberOfActiveConnections);
        LOG.debug("Registring driver");
        ConnectionManager.registerDriver();
        LOG.debug("Creating pool");
    }

    private ConnectionPool(int  maxNumberOfActiveConnections) {
        if (maxNumberOfActiveConnections <= 0) {
            throw new IllegalArgumentException("number <= 0");
        }
        this.maxNumberOfActiveConnections =  maxNumberOfActiveConnections;
        activeConnections = new LinkedBlockingQueue(maxNumberOfActiveConnections);
        idleConnections = new LinkedBlockingQueue(maxNumberOfIdleConnections);
    }
    
    /**
     *
     * @return instance of singletone pool
     */
    public static ConnectionPool getInstance(){
        LOG.debug("getting pool");
        if (instance == null){
            try {
                lock.lock();
            
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            } finally {
                lock.unlock();
            }           
        }
        return instance;
    }
    
    /**
     *
     * @return timeout
     */
    public int getTimeOut() {
        return timeOut;
    }

    /**
     *
     * @param timeOut timeout of attempt to get connection
     */
    public void setTimeOut(int timeOut) {
        closeAllConnections();
        this.timeOut = timeOut;
        activeConnections = new LinkedBlockingQueue(maxNumberOfActiveConnections);
        idleConnections = new LinkedBlockingQueue(maxNumberOfIdleConnections);
    }

    /**
     *
     * @return max number of active connections
     */
    public int getMaxNumberOfActiveConnections() {
        return maxNumberOfActiveConnections;
    }

    /**
     *
     * @param maxNumberOfActiveConnections max number of active connections
     */
    public void setMaxNumberOfActiveConnections(int maxNumberOfActiveConnections) {
        closeAllConnections();
        this.maxNumberOfActiveConnections = maxNumberOfActiveConnections;
        activeConnections = new LinkedBlockingQueue(maxNumberOfActiveConnections);
        idleConnections = new LinkedBlockingQueue(maxNumberOfIdleConnections);
    }

    /**
     *
     * @return max number of idle connections
     */
    public int getMaxNumberOfIdleConnections() {
        return maxNumberOfIdleConnections;
    }

    /**
     *
     * @param maxNumberOfIdleConnections max number of idle connections
     */
    public void setMaxNumberOfIdleConnections(int maxNumberOfIdleConnections) {
        this.maxNumberOfIdleConnections = maxNumberOfIdleConnections;
    }
    
    /**
     *
     * @return number of created connections
     */
    public int getNumberOfCreatedConnections(){
        return counter;
    }
    
    /**
     *
     * @return connection to DB
     * @throws TimeLimitExceededException
     */
    public WrapperConnector getConnection() throws TimeLimitExceededException { 
        WrapperConnector connection = null;
        try {
            lock.lock();
            if (idleConnections.isEmpty()
                    && counter < maxNumberOfActiveConnections) {
                counter++;
                connection = createAndGetConnection();
            } else {
                connection = getCreatedConnection();
            }
        } finally {
            lock.unlock();
        }
        return connection;
    }

    /**
     *
     * @param connection connection to DB
     */
    public void putbackConnection(WrapperConnector connection){
        activeConnections.remove(connection);
        if(idleConnections.size() < maxNumberOfIdleConnections) {
            LOG.debug("Puting connection back to pool");
            idleConnections.add(connection);
        } else {
            LOG.debug("Closing connection");
            connection.closeConnection();
            counter--;
        }   
    }
    
    /**
     *
     */
    public synchronized void closeAllConnections() {
        counter = 0;
        for (int i = 0; i < activeConnections.size(); i++){
            WrapperConnector connection = activeConnections.poll();
            if (! connection.getAutocommit()) {
                connection.rollBack();
            }
            connection.closeConnection();
        }
        for (int i = 0; i < idleConnections.size(); i++) {
            idleConnections.poll().closeConnection();
        }
    }

    private WrapperConnector createAndGetConnection() {
        LOG.debug("Creating and getting new connection");
        WrapperConnector connection = ConnectionManager.getConnector();
        activeConnections.add(connection);
        return connection;
    }

    private WrapperConnector getCreatedConnection() throws TimeLimitExceededException {
        LOG.debug("Getting ready connection");
        WrapperConnector connection = null;
        try {
            connection = idleConnections.poll(timeOut, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        if (connection == null) {
            throw new TimeLimitExceededException();
        }
        activeConnections.add(connection);
        return connection;
    }
    
    /**
     *
     */
    public void dissolvePool() {
        closeAllConnections();
        ConnectionManager.deregisterDrivers();
    }
}
