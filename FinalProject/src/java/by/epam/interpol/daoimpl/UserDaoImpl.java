/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.daoimpl;

import by.epam.interpol.dao.AbstractUserDao;
import by.epam.interpol.entity.User;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.pool.WrapperConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 *
 * @author Администратор
 */
public class UserDaoImpl extends AbstractUserDao<User>{
    private final static Logger LOG = LogManager.getLogger(UserDaoImpl.class);
    
    private final String ID = "id_user";
    private final String NAME = "name";
    private final String LOGIN = "login";
    private final String MAIL = "mail";
    private final String MONEY = "money";
    private final String STATUS = "is_admin";
    
    private static final String SELECT_ALL = "SELECT * FROM interpol.user;";
    private static final String SELECT_BY_LOGIN = "SELECT `id_user` FROM interpol.user "
            + "WHERE `login` = ? AND `password` = md5(?);";
    private static final String SELECT_BY_ID = "SELECT * FROM interpol.user " 
            + "WHERE `id_user` = ?";
    private static final String DELETE_BY_ID = "DELETE FROM interpol.user " 
            + "WHERE `id_user` = ?";
    private static final String CREATE_USER = "INSERT INTO interpol.user"
            + "(`name`, `login`, `password`, `mail`, `money`, `is_admin`)\n" 
            + "VALUES(?, ?, md5(?), ?, 0, ?)";
    private static final String UPDATE_USER = "UPDATE interpol.user\n"
            + "SET `name` = ?, `login` = ?, `password` = md5(?), `mail` = ?\n" 
            + "WHERE `id_user` = ?";
    private static final String UPDATE_MONEY = "UPDATE interpol.user\n"
            + "SET `money` = `money` + ?\n" 
            + "WHERE `id_user` = ?";
    private static final String SELECT_LOGIN = "SELECT `*` FROM interpol.user " 
            + "WHERE `login` = ?";
    private static final String SELECT_MAIL = "SELECT `*` FROM interpol.user " 
            + "WHERE `mail` = ?";
    
    public UserDaoImpl(WrapperConnector connection) {
        super(connection);
    }  

    @Override
    public boolean isFreeLogin(String login) throws ProjectException {
        LOG.debug("checks if login is free in dao");
        boolean isFree = true;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SELECT_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                LOG.debug("Such login already exists");
                isFree = false;
            }
        } catch (SQLException ex) {
            throw new ProjectException("Error trying to get access to get all users", ex);
        } 
        finally {
            close(statement);
        }
        LOG.debug(isFree);
        return isFree;
    }
    
    @Override
    public boolean isFreeMail(String mail) throws ProjectException {
        LOG.debug("checks if login is free");
        boolean isFree = true;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SELECT_MAIL);
            statement.setString(1, mail);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                LOG.debug("Such mail already exists");
                isFree = false;
            }
        } catch (SQLException ex) {
            throw new ProjectException("Error trying to get access to get all users", ex);
        } 
        finally {
            close(statement);
        }
        return isFree;
    }
    
    @Override
    public List<User> findAll() throws ProjectException {
        LOG.debug("trying to get all users");
        
        List<User> users = null;
        Statement statement = null;
        try {
            users = new ArrayList<User>();
            statement = connection.getStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt(ID));
                user.setName(resultSet.getString(NAME));
                user.setLogin(resultSet.getString(LOGIN));
                user.setMail(resultSet.getString(MAIL));
                user.setAdmin(resultSet.getBoolean(STATUS));
                users.add(user);
            }
        } catch (SQLException ex) {
            throw new ProjectException("Error trying to get access to get all users", ex);
        } 
        finally {
            close(statement);
        }
        return users;
    }
    
    public Optional<Integer> findIdIfRegistered(String login, String password) throws ProjectException {
        LOG.debug("trying to find user id if he was registered ");
        
        PreparedStatement statement = null;
        Integer id = null;
        try {
            statement = connection.prepareStatement(SELECT_BY_LOGIN);
            statement.setString(1, login);
            statement.setString(2, password);
            LOG.debug(password);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                id = resultSet.getInt(ID);
            }  
        } catch (SQLException ex) {
            throw new ProjectException("Error trying to LOGIN", ex);
        } finally {
            close(statement);
        }
        return Optional.ofNullable(id);
        
    }

    @Override
    public Optional<User> findEntityById(int id) throws ProjectException {
        LOG.debug("trying to find user by id = " + id);
        
        User user = null;
        PreparedStatement statement = null;
        try {
            user = new User();
            statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt(ID));
                user.setName(resultSet.getString(NAME));
                user.setLogin(resultSet.getString(LOGIN));
                user.setMail(resultSet.getString(MAIL));
                user.setMoney(resultSet.getDouble(MONEY));
                user.setAdmin(resultSet.getBoolean(STATUS));
            }
        } catch (SQLException ex) {
            throw new ProjectException("Error trying to get access to get user", ex);
        } finally {
            close(statement);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public boolean delete(int id) throws ProjectException {
        LOG.debug("trying to delte user by id = " + id);

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_BY_ID);
            statement.setInt(1, id);
            int result = statement.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (SQLException ex) {
            throw new ProjectException("Error trying to delete user", ex);
        } finally {
            close(statement);
        }
        return false;
    }

    @Override
    public boolean delete(User user) throws ProjectException {
        return delete(user.getId());
    }

//    returns id of user or -1 if couldn't create one
    @Override
    public Optional<Integer> create(User user) throws ProjectException {
        throw new RuntimeException();
    }
    
    public Optional<Integer> create(User user, String password) throws ProjectException {
        LOG.debug("trying to create user");

        PreparedStatement statement = null;
        Integer autoId = null;
        try {
            statement = connection.prepareStatement(CREATE_USER, 
                    Statement.RETURN_GENERATED_KEYS);
     
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            // IN case we have this login in base yet...
            statement.setString(3, password);
            statement.setString(4, user.getMail());
            statement.setBoolean(5, user.isAdmin());
            int result = statement.executeUpdate();
            if (result == 1) {
                
//                here we get id for user and set it
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                autoId = rs.getInt(1);
                user.setId(autoId);
            }
        } catch (SQLException ex) {
            throw new ProjectException("Error trying to get access to create user", ex);
        } finally {
            close(statement);
        }
        return Optional.ofNullable(autoId);  
    }

    @Override
    public Optional<User> update(User user) throws ProjectException {
        String password = "get new pass from page";
        return update(user, password);
    }
    
    private Optional<User> update(User user, String newPass) throws ProjectException {    
        LOG.debug("trying to update user");

        PreparedStatement statement = null;
        try {

            statement = connection.prepareStatement(UPDATE_USER);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            // IN case we have this login in base yet...
            statement.setString(3, newPass);
            statement.setString(4, user.getMail());
            statement.setInt(5, user.getId());
            
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ProjectException("Error trying to get access to update user", ex);
        } finally {
            close(statement);
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> updateMoney(User user) throws ProjectException {
        LOG.debug("trying to update user");

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_MONEY);
            statement.setDouble(1, user.getMoney());
            statement.setInt(2, user.getId());

           int result = statement.executeUpdate();
           if (result != 1) {
               user = null;
           }
        } catch (SQLException ex) {
            throw new ProjectException("Error trying to get access to update user", ex);
        } finally {
            close(statement);
        }
        return Optional.ofNullable(user);
    }

}
