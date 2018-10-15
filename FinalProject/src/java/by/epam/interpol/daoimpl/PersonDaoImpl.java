/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.daoimpl;

import by.epam.interpol.dao.AbstractPersonDao;
import by.epam.interpol.entity.Person;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.pool.WrapperConnector;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 *
 * @author Администратор
 */
public class PersonDaoImpl extends AbstractPersonDao{
    private final static Logger LOG = LogManager.getLogger(PersonDaoImpl.class);
    
    private static final String ID = "id_person";
    private static final String NAME = "name";
    private static final String PANNAME = "panname";
    private static final String AGE = "age";
    private static final String BIRTH_PLACE = "birthP";
    private static final String LAST_PLACE = "lastP";
    private static final String PHOTO = "photo";
    private static final String AWARD = "award";
    private static final String INFORMATION = "information";
    private static final String IS_CRIMINAL = "isCriminal";
    private static final String FOUND = "found";
    private static final String UTF_8 = "UTF-8";
    private static final String JPG = "jpg";
    
    private static final String SELECT_BY_ID = "SELECT id_person, name, panname, "
            + "age, t.last_seen_region , t.birth_region, t1.region AS lastP, "
            + "t2.region AS birthP, \n" 
            + "photo, award, information, isCriminal, found\n" 
            + "FROM interpol.person  t\n" 
            + "JOIN interpol.region t1 ON t1.id_region = t.last_seen_region\n" 
            + "JOIN interpol.region t2 ON t2.id_region = t.birth_region\n"  
            + "WHERE `id_person` = ? AND `found` = b'0'";
    
    private static final String SELECT_ID_BY_NAME_PANNAME = "SELECT id_person\n"
            +"FROM interpol.person \n" 
            +"WHERE `name` = ? AND `panname` = ?";
    
    private static final String SELECT_BY_NAME_PANNAME = "SELECT id_person, name, panname, "
            + "age, t.last_seen_region , t.birth_region, t1.region AS lastP, "
            + "t2.region AS birthP, \n" 
            + "photo, award, information, isCriminal, found\n" 
            + "FROM interpol.person  t \n" 
            + "JOIN interpol.region t1 ON t1.id_region = t.last_seen_region\n" 
            + "JOIN interpol.region t2 ON t2.id_region = t.birth_region\n"  
            + "WHERE `name` = ? AND `panname` = ? AND `found` = b'0'";
    
    private static final String SELECT_WITH_OFFSET = "SELECT id_person, name, "
            + "panname, age, t.last_seen_region , t.birth_region, t1.region AS lastP, \n" 
            +"t2.region AS birthP, photo, award, information, isCriminal\n" 
            +"FROM interpol.person  t JOIN interpol.region t1 ON t1.id_region = t.last_seen_region\n" 
            +"JOIN interpol.region t2 ON t2.id_region = t.birth_region WHERE isCriminal = ? \n" 
            +"AND `found` = b'0' ORDER BY panname LIMIT ? OFFSET ?";
    
    private static final String SELECT_WITH_OFFSET_ARCHIVE = "SELECT id_person, name, "
            + "panname, age, t.last_seen_region , t.birth_region, t1.region AS lastP, \n" 
            +"t2.region AS birthP, photo, award, information, isCriminal\n" 
            +"FROM interpol.person  t JOIN interpol.region t1 ON t1.id_region = t.last_seen_region\n" 
            +"JOIN interpol.region t2 ON t2.id_region = t.birth_region WHERE \n" 
            +"`found` = b'1' ORDER BY panname LIMIT ? OFFSET ?";
    
    private static final String SELECT_BY_REGION = "SELECT id_person, name, \n" 
            +"panname, age, t.last_seen_region , t.birth_region, t1.region AS lastP, \n" 
            +"t2.region AS birthP, found, photo, award, information, isCriminal\n" 
            +"FROM interpol.person t JOIN interpol.region t1 ON t1.id_region = t.last_seen_region\n" 
            +"JOIN interpol.region t2 ON t2.id_region = t.birth_region  \n" 
            +"WHERE (t.birth_region = (SELECT `id_region` FROM interpol.region WHERE `region` = ?)\n" 
            +"OR\n" 
            +"t.last_seen_region = (SELECT `id_region` FROM interpol.region WHERE `region` = ?))\n" 
            +"AND `found` = b'0' ORDER BY panname LIMIT ? OFFSET ?";
    
    private static final String CREATE_PERSON = "INSERT INTO interpol.person "
            + "(`name`, `panname`, `age`, `photo`, `birth_region`, "
            + "`last_seen_region`, `award`, `information`, `isCriminal`, `found`) \n" 
            + "VALUE (?, ?, ?, ?, "
            + "(SELECT `id_region` FROM interpol.region WHERE `region` = ?),"
            + " (SELECT `id_region` FROM interpol.region WHERE `region` = ?),"
            + " ?, ?, ?, b'0')";
    
    private static final String UPDATE_PERSON = "UPDATE interpol.person\n" +
            "SET `name` = ?, `panname` = ?, `age` = ?, `photo` = ?,\n" +
            "`birth_region` = (SELECT `id_region` FROM interpol.region WHERE `region` = ?),\n" +
            "`last_seen_region` = (SELECT `id_region` FROM interpol.region WHERE `region` = ?),\n" +
            " `award` = ?, `information` = ?, `found` = ?\n" +
            "WHERE `id_person` = ?";
    
    private static final String DELETE_PERSON = "DELETE\n" 
            +"FROM interpol.person\n" 
            +"WHERE id_person = ?;";
    
    private static final String DELETE_PERSON_BY_N_P = "DELETE\n" 
            +"FROM interpol.person\n" 
            +"WHERE id_person = ?;";
    
    
    private static final String UPDATE_SET_FOUND_BY_ID = "UPDATE interpol.person\n" 
            +"SET `found` = b'1'\n" 
            +"WHERE `id_person` = ?";
    
    private static final String UPDATE_PERSON_NO_PHOTO = "UPDATE interpol.person\n" +
            "SET `name` = ?, `panname` = ?, `age` = ?, \n" +
            "`birth_region` = (SELECT `id_region` FROM interpol.region WHERE `region` = ?),\n" +
            "`last_seen_region` = (SELECT `id_region` FROM interpol.region WHERE `region` = ?),\n" +
            " `award` = ?, `information` = ?, `found` = ?\n" +
            "WHERE `id_person` = ?";

    private static final String FIND_LAST = "SELECT id_person, name, panname, "
            + "age, t.last_seen_region , t.birth_region, t1.region AS lastP, "
            + "t2.region AS birthP, \n" 
            + "photo, award, information, isCriminal\n" 
            + "FROM interpol.person  t\n" 
            + "JOIN interpol.region t1 ON t1.id_region = t.last_seen_region\n" 
            + "JOIN interpol.region t2 ON t2.id_region = t.birth_region\n" 
            + "WHERE `found` = b'0' ORDER BY id_person DESC LIMIT 1";
    
    
    
    public PersonDaoImpl(WrapperConnector connection) {
        super(connection);
    }

    public Optional<Integer> create(Person person) throws ProjectException {
        
        LOG.debug("going to database to create wanted criminal");
        
        PreparedStatement statement = null;
        Integer autoId = null;
        
        try {
            statement = connection.prepareStatement(CREATE_PERSON, 
                    Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, person.getName());
            statement.setString(2, person.getPanname());
            statement.setInt(3, person.getAge());
            BufferedImage image = person.getPhoto();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try { 
                ImageIO.write(image, "jpg", os);
            } catch (IOException ex) {
                throw new ProjectException();
            }
            InputStream fis = new ByteArrayInputStream(os.toByteArray());
            statement.setBlob(4, fis);
            statement.setString(5, person.getBirthPlace());
            statement.setString(6, person.getLastPlace());
            statement.setInt(7, person.getAward());
            statement.setString(8, person.getInformation());
            statement.setBoolean(9, person.isIsCriminal());
            int result = statement.executeUpdate();
            if (result == 1) {
                
//                here we get id for user and set it
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                autoId = rs.getInt(1);
                LOG.debug("autoID " + autoId);
                person.setId(autoId);
            }
        } catch (SQLException ex) {
            throw new ProjectException("Error trying to get access to get all users", ex);
        } 
        finally {
            close(statement);
        }
        return Optional.ofNullable(autoId);
    }
    
    public Person findLast() throws ProjectException{
        LOG.debug("trying to find last person");
        
        Person person = null;
        Statement statement = null;
        try {
            person = new Person();
            statement = connection.getStatement();
            ResultSet resultSet = statement.executeQuery(FIND_LAST);
            while (resultSet.next()){
                person.setId(resultSet.getInt(ID));
                person.setName(resultSet.getString(NAME));
                person.setPanname(resultSet.getString(PANNAME));
                person.setAge(resultSet.getInt(AGE));
                person.setBirthPlace(resultSet.getString(BIRTH_PLACE));
                person.setLastPlace(resultSet.getString(LAST_PLACE));
                
                InputStream photoInputStream = new BufferedInputStream (
                        resultSet.getBinaryStream(PHOTO));
                BufferedImage image;
                try {
                    image = ImageIO.read(photoInputStream);
                } catch (IOException ex) {
                    throw new ProjectException("Error trying to get access to "
                            + "find last", ex);
                }
                person.setPhoto(image);
                person.setAward(resultSet.getInt(AWARD));
                person.setInformation(resultSet.getString(INFORMATION));
                person.setIsCriminal(resultSet.getBoolean(IS_CRIMINAL));
                if (image != null) {
                    person = setEncodedImage(person);
                }
            }
        } catch (SQLException ex) {
            LOG.error("exception:", ex);
            throw new ProjectException("Error trying to get access to get all users", ex);
        } 
        finally {
            close(statement);
        }
        return person;
    }    

    @Override
    public List<Person> findAll() throws ProjectException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<Person> findEntityById(int id) throws ProjectException {
        LOG.debug("trying to find person by id = " + id);
        
        Person person = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SELECT_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                person = new Person();
                person.setId(resultSet.getInt(ID));
                person.setName(resultSet.getString(NAME));
                person.setPanname(resultSet.getString(PANNAME));
                person.setAge(resultSet.getInt(AGE));
                person.setBirthPlace(resultSet.getString(BIRTH_PLACE));
                person.setLastPlace(resultSet.getString(LAST_PLACE));
                InputStream photoInputStream = resultSet.getBinaryStream(PHOTO);
                if (photoInputStream != null) {
                    BufferedImage image;
                    try {
                        image = ImageIO.read(photoInputStream);
                    } catch (IOException ex) {
                        LOG.error("exception:", ex);
                        throw new ProjectException("Error trying to get access to "
                                + "find by id", ex);
                    }
                    person.setPhoto(image);
                    if(image != null) {
                        person = setEncodedImage(person);
                    }
                }
                person.setAward(resultSet.getInt(AWARD));
                person.setInformation(resultSet.getString(INFORMATION));
                person.setIsCriminal(resultSet.getBoolean(IS_CRIMINAL));
                person.setStatus(resultSet.getBoolean(FOUND));
            }
        } catch (SQLException ex) {
            LOG.error("exception:", ex);
            throw new ProjectException("Error trying to get access to get person", ex);
        } finally {
            close(statement);
        }
        return Optional.ofNullable(person);
    }
    
    public Optional<Integer> findIdByNamePanname(String name, String panname) 
            throws ProjectException {
        LOG.debug("trying to find person by name, panname ");
        PreparedStatement statement = null;
        Integer id = null;
        try {
            statement = connection.prepareStatement(SELECT_ID_BY_NAME_PANNAME);
            statement.setString(1, name);
            statement.setString(2, panname);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                id = resultSet.getInt(ID);     
            }
        } catch (SQLException ex) {
            LOG.error("exception:", ex);
            throw new ProjectException("Error trying to find person", ex);
        } finally {
            close(statement);
        }
        return Optional.ofNullable(id);
    }
    
    @Override
    public Optional<Person> findEntityByNamePanname(String name, String panname) 
            throws ProjectException {
        LOG.debug("trying to find person by name, panname ");
        Person person = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SELECT_BY_NAME_PANNAME);
            statement.setString(1, name);
            statement.setString(2, panname);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                person = new Person();
                person.setId(resultSet.getInt(ID));
                person.setName(resultSet.getString(NAME));
                person.setPanname(resultSet.getString(PANNAME));
                person.setAge(resultSet.getInt(AGE));
                person.setBirthPlace(resultSet.getString(BIRTH_PLACE));
                person.setLastPlace(resultSet.getString(LAST_PLACE));
                InputStream photoInputStream = resultSet.getBinaryStream(PHOTO);
                if (photoInputStream != null) {
                    BufferedImage image;
                    try {
                        image = ImageIO.read(photoInputStream);
                    } catch (IOException ex) {
                        LOG.error("exception:", ex);
                        throw new ProjectException("Error trying to get access to "
                                + "find by id", ex);
                    }
                    person.setPhoto(image);
                    if (image != null) {
                        person = setEncodedImage(person);
                    }
                }
                person.setAward(resultSet.getInt(AWARD));
                person.setInformation(resultSet.getString(INFORMATION));
                person.setIsCriminal(resultSet.getBoolean(IS_CRIMINAL));
                person.setStatus(resultSet.getBoolean(FOUND));
            }
        } catch (SQLException ex) {
            LOG.error("exception:", ex);
            throw new ProjectException("Error trying to find person", ex);
        } finally {
            close(statement);
        }
        return Optional.ofNullable(person);
    }    

    @Override
    public boolean delete(int id) throws ProjectException {
        LOG.debug("going to database to delete person");
        
        PreparedStatement statement = null;
        boolean deleted = true;
        try {
            statement = connection.prepareStatement(DELETE_PERSON);
            
            statement.setInt(1, id);
            
            int result = statement.executeUpdate();
            if (result != 1) {
                deleted = false;
            }
        } catch (SQLException ex) {
            LOG.error("exception:", ex);
            throw new ProjectException("Error trying to get access to get all users", ex);
        } 
        finally {
            close(statement);
        }
        return deleted;
    }

    @Override
    public boolean delete(Person person) throws ProjectException {
        LOG.debug("going to database to delete person");
        
        PreparedStatement statement = null;
        boolean deleted = true;
        try {
            statement = connection.prepareStatement(DELETE_PERSON_BY_N_P);
            
            statement.setString(1, person.getName());
            statement.setString(2, person.getPanname());
            
            int result = statement.executeUpdate();
            if (result < 1) {
                deleted = false;
            }
        } catch (SQLException ex) {
            LOG.error("exception:", ex);
            throw new ProjectException("Error trying to get access to get all users", ex);
        } 
        finally {
            close(statement);
        }
        return deleted;
    }

    @Override
    public Optional<Person> update(Person person) throws ProjectException {
        LOG.debug("going to database to update person");
        
        PreparedStatement statement = null;
  
        try {
            statement = connection.prepareStatement(UPDATE_PERSON);
            
            statement.setString(1, person.getName());
            statement.setString(2, person.getPanname());
            statement.setInt(3, person.getAge());
            BufferedImage image = person.getPhoto(); 
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try { 
                ImageIO.write(image, JPG, os);
            } catch (IOException ex) {
                throw new ProjectException();
            }
            InputStream fis = new ByteArrayInputStream(os.toByteArray());
            statement.setBlob(4, fis);
            statement.setString(5, person.getBirthPlace());
            statement.setString(6, person.getLastPlace());
            statement.setInt(7, person.getAward());
            statement.setString(8, person.getInformation());
            statement.setBoolean(9, person.getStatus());
            LOG.debug(person.getStatus());
            statement.setInt(10, person.getId());
            int result = statement.executeUpdate();
            if (result != 1) {
                person = null;
            }
        } catch (SQLException ex) {
            LOG.error("exception:", ex);
            throw new ProjectException("Error trying to get access to get all users", ex);
        } 
        finally {
            close(statement);
        }
        return Optional.ofNullable(person);
    }
    
    @Override
    public Optional<Person> updateNoPhoto(Person person) throws ProjectException {
        LOG.debug("going to database to create wanted criminal");
        
        PreparedStatement statement = null;
  
        try {
            statement = connection.prepareStatement(UPDATE_PERSON_NO_PHOTO);
            
            statement.setString(1, person.getName());
            statement.setString(2, person.getPanname());
            statement.setInt(3, person.getAge());
            statement.setString(4, person.getBirthPlace());
            statement.setString(5, person.getLastPlace());
            statement.setInt(6, person.getAward());
            statement.setString(7, person.getInformation());
            statement.setBoolean(8, person.getStatus());
            statement.setInt(9, person.getId());
            int result = statement.executeUpdate();
            if (result != 1) {
                person = null;
            }
        } catch (SQLException ex) {
            LOG.error("exception:", ex);
            throw new ProjectException("Error trying to get access to get all users", ex);
        } 
        finally {
            close(statement);
        }
        return Optional.ofNullable(person);
    }
    
    public Optional<Person> updateSetFoundById(Person person) throws ProjectException {
        LOG.debug("going to database to set the object is found");
        
        PreparedStatement statement = null;
  
        try {
            statement = connection.prepareStatement(UPDATE_SET_FOUND_BY_ID);
            
            statement.setInt(1, person.getId());
            int result = statement.executeUpdate();
            if (result != 1) {
                person = null;
            }
        } catch (SQLException ex) {
            LOG.error("exception:", ex);
            throw new ProjectException("Error trying to get access to get all users", ex);
        } 
        finally {
            close(statement);
        }
        return Optional.ofNullable(person);
    }

    public List<Person> findAmountOfEntities(int pageSize, int offset, boolean criminal) throws ProjectException {
        LOG.debug("trying to find personList for page");
        
        List<Person> personList = null;
        PreparedStatement statement = null;
        try {
            personList = new ArrayList<>();
            statement = connection.prepareStatement(SELECT_WITH_OFFSET);
            statement.setBoolean(1, criminal);
            statement.setInt(2, pageSize);
            statement.setInt(3, offset);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Person person = new Person();
                person.setId(resultSet.getInt(ID));
                person.setName(resultSet.getString(NAME));
                person.setPanname(resultSet.getString(PANNAME));
                person.setAge(resultSet.getInt(AGE));
                person.setBirthPlace(resultSet.getString(BIRTH_PLACE));
                person.setLastPlace(resultSet.getString(LAST_PLACE));
                InputStream photoInputStream = resultSet.getBinaryStream(PHOTO);
                if (photoInputStream != null) {
                    BufferedImage image;
                    try {
                        image = ImageIO.read(photoInputStream);
                    } catch (IOException ex) {
                        LOG.error("exception:", ex);
                        throw new ProjectException("Error trying to get access to "
                                + "find by id", ex);
                    }
                    person.setPhoto(image);
                    if(image != null) {
                        person = setEncodedImage(person);
                    }
                }
                person.setAward(resultSet.getInt(AWARD));
                person.setInformation(resultSet.getString(INFORMATION));
                person.setIsCriminal(resultSet.getBoolean(IS_CRIMINAL));
                personList.add(person);
            }
        } catch (SQLException ex) {
            LOG.error("exception:", ex);
            throw new ProjectException("Error trying to find person", ex);
        } finally {
            close(statement);
        }
        return personList;
    }

    public List<Person> findByRegion(int pageSize, int offset, String region) throws ProjectException {
        LOG.debug("trying to find personList by region");
        
        List<Person> personList = null;
        PreparedStatement statement = null;
        try {
            personList = new ArrayList<>();
            statement = connection.prepareStatement(SELECT_BY_REGION);
            statement.setString(1, region);
            statement.setString(2, region);
            statement.setInt(3, pageSize);
            statement.setInt(4, offset);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Person person = new Person();
                person.setId(resultSet.getInt(ID));
                person.setName(resultSet.getString(NAME));
                person.setPanname(resultSet.getString(PANNAME));
                person.setAge(resultSet.getInt(AGE));
                person.setBirthPlace(resultSet.getString(BIRTH_PLACE));
                person.setLastPlace(resultSet.getString(LAST_PLACE));
                InputStream photoInputStream = resultSet.getBinaryStream(PHOTO);
                if (photoInputStream != null) {
                    BufferedImage image;
                    try {
                        image = ImageIO.read(photoInputStream);
                    } catch (IOException ex) {
                        LOG.error("exception:", ex);
                        throw new ProjectException("Error trying to get access to "
                                + "find by id", ex);
                    }
                    person.setPhoto(image);
                    person = setEncodedImage(person);
                }
                person.setAward(resultSet.getInt(AWARD));
                person.setInformation(resultSet.getString(INFORMATION));
                person.setIsCriminal(resultSet.getBoolean(IS_CRIMINAL));
                personList.add(person);
            }
        } catch (SQLException ex) {
            LOG.error("exception:", ex);
            throw new ProjectException("Error trying to find person", ex);
        } finally {
            close(statement);
        }
        return personList;
    }
    
    public List<Person> findAmountOfEntities(int pageSize, int offset) throws ProjectException {
        LOG.debug("trying to find personList for page");
        
        List<Person> personList = null;
        PreparedStatement statement = null;
        try {
            personList = new ArrayList<>();
            statement = connection.prepareStatement(SELECT_WITH_OFFSET_ARCHIVE);
            statement.setInt(1, pageSize);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Person person = new Person();
                person.setId(resultSet.getInt(ID));
                person.setName(resultSet.getString(NAME));
                person.setPanname(resultSet.getString(PANNAME));
                person.setAge(resultSet.getInt(AGE));
                person.setBirthPlace(resultSet.getString(BIRTH_PLACE));
                person.setLastPlace(resultSet.getString(LAST_PLACE));
                InputStream photoInputStream = resultSet.getBinaryStream(PHOTO);
                if (photoInputStream != null) {
                    BufferedImage image;
                    try {
                        image = ImageIO.read(photoInputStream);
                    } catch (IOException ex) {
                        LOG.error("exception:", ex);
                        throw new ProjectException("Error trying to get access to "
                                + "find by id", ex);
                    }
                    person.setPhoto(image);
                    if(image != null) {
                        person = setEncodedImage(person);
                    }
                }
                person.setAward(resultSet.getInt(AWARD));
                person.setInformation(resultSet.getString(INFORMATION));
                person.setIsCriminal(resultSet.getBoolean(IS_CRIMINAL));
                personList.add(person);
            }
        } catch (SQLException ex) {
            LOG.error("exception:", ex);
            throw new ProjectException("Error trying to find person", ex);
        } finally {
            close(statement);
        }
        return personList;
    }    
    
    private Person setEncodedImage(Person person) throws ProjectException {
        BufferedImage image = person.getPhoto();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try { 
            ImageIO.write(image, JPG, os);
        } catch (IOException ex) {
            throw new ProjectException(ex);
        }
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        try {
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            byte[] encodeBase64 = Base64.encodeBase64(buffer.toByteArray());
            String base64Encoded = new String(encodeBase64, UTF_8);
            person.setBase64image(base64Encoded);
        } catch (IOException ex) {
            LOG.error("exception:", ex);
            throw new ProjectException("Couldn't find image", ex);
        }
        return person;
    }



}
