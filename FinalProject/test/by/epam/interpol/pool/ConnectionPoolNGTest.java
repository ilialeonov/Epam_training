/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.pool;

import by.epam.interpol.exception.TimeLimitExceededException;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Администратор
 */
public class ConnectionPoolNGTest {
    private static final Logger LOG = LogManager.getLogger(ConnectionPoolNGTest.class);
    
    public ConnectionPoolNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception { 
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        ConnectionPool pool = ConnectionPool.getInstance();
        pool.closeAllConnections();
    }

    /**
     * Тест метод getInstance, класса ConnectionPool.
     */
    @Test
    public void testGetInstance() {
        LOG.info("getInstance");
        /*
        * Checks if pool returns the same instance
        */
        
        ConnectionPool expResult = ConnectionPool.getInstance();
        ConnectionPool result = ConnectionPool.getInstance();
        assertTrue(result == expResult);
    }

    @Test
    public void testGetConnection() throws Exception {
        LOG.info("getConnection");
        /*
        * Checks if pool uses idle connections before creating new ones
        */
        
        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector result = pool.getConnection();
        assertNotNull(result);
        pool.putbackConnection(result);
    }
    
    @Test
    public void testGetConnectionBeforeCreate() throws Exception {
        LOG.info("getConnection");
        /*
        * Checks if pool uses idle connections before creating new ones
        */
        
        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector exp1 = pool.getConnection();
        pool.putbackConnection(exp1);
        WrapperConnector exp2 = pool.getConnection();
        pool.putbackConnection(exp2);
        WrapperConnector exp3 = pool.getConnection();
        pool.putbackConnection(exp3);
        WrapperConnector exp4 = pool.getConnection();
        pool.putbackConnection(exp4);
        int result = 1;
        int expResult = pool.getNumberOfCreatedConnections();
        assertEquals(result, expResult);
    }

    @Test(expectedExceptions = SQLException.class)
    public void testPutbackConnection() 
            throws InterruptedException, TimeLimitExceededException, SQLException {
        LOG.info("putbackConnection");
        /*
        * Five connections go to idleconnections
        * Sixth connection will be closed
        */
        
        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection1 = pool.getConnection();
        WrapperConnector connection2 = pool.getConnection();
        WrapperConnector connection3 = pool.getConnection();
        WrapperConnector connection4 = pool.getConnection();
        WrapperConnector connection5 = pool.getConnection();
        WrapperConnector connection = pool.getConnection();
        pool.putbackConnection(connection1);
        pool.putbackConnection(connection2);
        pool.putbackConnection(connection3);
        pool.putbackConnection(connection4);
        pool.putbackConnection(connection5);
        pool.putbackConnection(connection);
        connection.getStatement();
    }

    /**
     * Тест метод closeAllConnections, класса ConnectionPool.
     */
    @Test(expectedExceptions = SQLException.class)
    public void testCloseAllConnections() 
            throws SQLException, InterruptedException, TimeLimitExceededException {
        LOG.info("closeAllConnections");
        /*
        * Any connection after close operation will give SQLException
        */
        ConnectionPool pool = ConnectionPool.getInstance();
        WrapperConnector connection = pool.getConnection();
        pool.putbackConnection(connection);
        pool.closeAllConnections();
        connection.getStatement();
    }
}
