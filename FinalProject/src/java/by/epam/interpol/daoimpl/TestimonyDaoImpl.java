/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.daoimpl;

import by.epam.interpol.dao.AbstractTestimonyDao;
import by.epam.interpol.entity.Person;
import by.epam.interpol.entity.Testimony;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.pool.WrapperConnector;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 *
 * @author Ilia Leonov
 * operations with db in testimonies
 */
public class TestimonyDaoImpl extends AbstractTestimonyDao{
    private final static Logger LOG = LogManager.getLogger(TestimonyDaoImpl.class);
    
    private final String ID = "id";
    private final String IDPerson = "person_id";
    private final String NAME = "name";
    private final String PANNAME = "panname";
    private final String AGE = "age";
    private final String BIRTH_PLACE = "birthP";
    private final String LAST_PLACE = "lastP";
    private final String PHOTO = "photo";
    private final String WATCHED = "watched";
    private final String AWARD = "award";
    private final String POINTS = "points";
    private final String TESTIMONY = "testimony";
    private final String SUM = "sum";
    private final String USER_ID = "user_id";
    
    private static final String CREATE_TESTIMONY = "INSERT INTO " 
            +"interpol.person_testimony (`user_id`, `person_id`, `testimony`, `watched`)\n" 
            +"VALUE (?, ?, ?, b'0')";
    
    private static final String UPDATE_TESTIMONY = "UPDATE interpol.person_testimony\n" 
            +"SET `points` = ?, `watched` = b'1' WHERE\n" 
            +"id = ?";

    private static final String SELECT_USER_WITH_OFFSET = "SELECT person_id, name, "
            + "panname, age, t2.region AS birthP, t3.region AS lastP,\n" 
            +" photo, award, testimony, found, points, watched \n"
            + "FROM interpol.person_testimony t LEFT JOIN interpol.person t1 ON \n" 
            +"t.person_id = t1.id_person JOIN interpol.region t2 ON\n" 
            +"t2.id_region = t1.birth_region JOIN interpol.region t3 ON\n" 
            +"t3.id_region = t1.last_seen_region\n" 
            +"WHERE user_id = ? AND isCriminal = ? AND found = b'0' AND watched = b'0' "
            + "ORDER BY id DESC LIMIT ? OFFSET ?";
    
    private static final String SELECT_ADMIN_WITH_OFFSET = "SELECT "
            + "id, person_id, name, "
            + "panname, age, t2.region AS birthP, t3.region AS lastP,\n" 
            +" photo, award, testimony, found, points, watched \n"
            + "FROM interpol.person_testimony t LEFT JOIN interpol.person t1 ON \n" 
            +"t.person_id = t1.id_person JOIN interpol.region t2 ON\n" 
            +"t2.id_region = t1.birth_region JOIN interpol.region t3 ON\n" 
            +"t3.id_region = t1.last_seen_region\n" 
            +"WHERE isCriminal = ? AND found = b'0' AND watched = b'0' "
            + "ORDER BY id DESC LIMIT ? OFFSET ?";
    
    private static final String SELECT_ADMIN_ARCHIVE = "SELECT "
            + "id, person_id, name, "
            + "panname, age, t2.region AS birthP, t3.region AS lastP,\n" 
            +" photo, award, testimony, found, points, watched \n"
            + "FROM interpol.person_testimony t LEFT JOIN interpol.person t1 ON \n" 
            +"t.person_id = t1.id_person JOIN interpol.region t2 ON\n" 
            +"t2.id_region = t1.birth_region JOIN interpol.region t3 ON\n" 
            +"t3.id_region = t1.last_seen_region\n" 
            +"WHERE watched = b'1' "
            + "ORDER BY id DESC LIMIT ? OFFSET ?";

    private static final String SELECT_ARCHIVE_WITH_OFFSET = "SELECT person_id, name, "
            + "panname, age, t2.region AS birthP, t3.region AS lastP,\n" 
            +" photo, award, testimony, found, points, watched \n"
            + "FROM interpol.person_testimony t LEFT JOIN interpol.person t1 ON \n" 
            +"t.person_id = t1.id_person JOIN interpol.region t2 ON\n" 
            +"t2.id_region = t1.birth_region JOIN interpol.region t3 ON\n" 
            +"t3.id_region = t1.last_seen_region\n" 
            +"WHERE user_id = ? AND watched = b'1' "
            + "ORDER BY id DESC LIMIT ? OFFSET ?";
    
    private static final String SELECT_SUM_POINTS = "SELECT SUM(points) AS sum\n" 
            +"FROM interpol.person_testimony \n" 
            +"GROUP BY person_id HAVING person_id = ?;";
    
    private static final String SELECT_EACH_USER_SUM_POINTS = "SELECT SUM(points) AS sum, user_id\n" 
            +"FROM interpol.person_testimony \n" 
            +"GROUP BY user_id, person_id HAVING person_id = ?;";
    
    /**
     *
     * @param connection connection to DB
     */
    public TestimonyDaoImpl(WrapperConnector connection) {
        super(connection);
    }

    public Optional<Integer> create(Testimony testimony) throws ProjectException {
        
        LOG.debug("going to database to create testimony");
        
        PreparedStatement statement = null;
        Integer autoId = null;
        
        try {
            statement = connection.prepareStatement(CREATE_TESTIMONY, 
                    Statement.RETURN_GENERATED_KEYS);
            
            statement.setInt(1, testimony.getUserId());
            statement.setInt(2, testimony.getPersonId());
            statement.setString(3, testimony.getTestimony());
            
            int result = statement.executeUpdate();
            if (result == 1) {
                
//                here we get id for testimony and set it
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                autoId = rs.getInt(1);
                testimony.setId(autoId);
            }
        } catch (SQLException ex) {
            throw new ProjectException("Error trying to add testimony", ex);
        } 
        finally {
            close(statement);
        }
        return Optional.ofNullable(autoId);
    }
 
    /**
     *
     * @param pageSize amount of elements on page
     * @param offset offset to watch elements in DB
     * @param userId user's id
     * @param isCriminal person's status
     * @return List of testimony elements
     * @throws ProjectException
     */
    public List<Testimony> findAmountOfEntities(int pageSize, int offset, int userId, 
            boolean isCriminal) throws ProjectException {
        
        LOG.debug("trying to find personList for page");
        
        List<Testimony> testimonyList = null;
        PreparedStatement statement = null;
        try {
            testimonyList = new ArrayList<>();
            statement = connection.prepareStatement(SELECT_USER_WITH_OFFSET);
            statement.setInt(1, userId);
            statement.setBoolean(2, isCriminal);
            statement.setInt(3, pageSize);
            statement.setInt(4, offset);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Testimony testimony = new Testimony();
                Person person = new Person();
                
                person.setId(resultSet.getInt(IDPerson));
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
                        throw new ProjectException("Error trying to get access to "
                                + "find by id", ex);
                    }
                    person.setPhoto(image);
                    if(image != null) {
                        person = setEncodedImage(person);
                    }
                }
                person.setAward(resultSet.getInt(AWARD));
                testimony.setPerson(person);
                testimony.setWatched(resultSet.getBoolean(WATCHED));
                testimony.setPoints(resultSet.getInt(POINTS));
                testimony.setTestimony(resultSet.getString(TESTIMONY));
                testimonyList.add(testimony);
            }
        } catch (SQLException ex) {
            throw new ProjectException("Error trying to find person", ex);
        } finally {
            close(statement);
        }
        return testimonyList;
    } 
    
    /**
     *
     * @param pageSize amount of elements on page
     * @param offset offset to watch elements in DB
     * @param idUser user's id
     * @return List of testimony elements
     * @throws ProjectException
     */
    public List<Testimony> findAmountOfEntities(int pageSize, int offset, 
            Integer idUser) throws ProjectException {
        LOG.debug("trying to find personList for page");
        
        List<Testimony> testimonyList = null;
        PreparedStatement statement = null;
        try {
            testimonyList = new ArrayList<>();
            statement = connection.prepareStatement(SELECT_ARCHIVE_WITH_OFFSET);
            statement.setInt(1, idUser);
            statement.setInt(2, pageSize);
            statement.setInt(3, offset);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Testimony testimony = new Testimony();
                Person person = new Person();
                
                person.setId(resultSet.getInt(IDPerson));
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
                testimony.setPerson(person);
                testimony.setPoints(resultSet.getInt(POINTS));
                testimony.setWatched(resultSet.getBoolean(WATCHED));
                testimony.setTestimony(resultSet.getString(TESTIMONY));
                testimonyList.add(testimony);
            }
        } catch (SQLException ex) {
            throw new ProjectException("Error trying to find person", ex);
        } finally {
            close(statement);
        }
        return testimonyList;
    }
    
    /**
     *
     * @param pageSize amount of elements on page
     * @param offset offset to watch elements in DB
     * @param isCriminal person's status
     * @return List of testimony elements
     * @throws ProjectException
     */
    public List<Testimony> findAmountOfEntities(int pageSize, int offset, 
            boolean isCriminal) throws ProjectException {
        LOG.debug("trying to find personList for page");
        
        List<Testimony> testimonyList = null;
        PreparedStatement statement = null;
        try {
            testimonyList = new ArrayList<>();
            statement = connection.prepareStatement(SELECT_ADMIN_WITH_OFFSET);
            statement.setBoolean(1, isCriminal);
            statement.setInt(2, pageSize);
            statement.setInt(3, offset);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Testimony testimony = new Testimony();
                Person person = new Person();
                
                person.setId(resultSet.getInt(IDPerson));
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
                        throw new ProjectException("Error trying to get access to "
                                + "find by id", ex);
                    }
                    person.setPhoto(image);
                    if(image != null) {
                        person = setEncodedImage(person);
                    }
                }
                person.setAward(resultSet.getInt(AWARD));
                testimony.setPerson(person);
                testimony.setId(resultSet.getInt(ID));
                testimony.setPoints(resultSet.getInt(POINTS));
                testimony.setWatched(resultSet.getBoolean(WATCHED));
                testimony.setTestimony(resultSet.getString(TESTIMONY));
                testimonyList.add(testimony);
            }
        } catch (SQLException ex) {
            throw new ProjectException("Error trying to find person", ex);
        } finally {
            close(statement);
        }
        return testimonyList;
    }
    
    /**
     *
     * @param pageSize amount of elements on page
     * @param offset offset watching in DB
     * @return List of testimonies
     * @throws ProjectException
     */
    public List<Testimony> findAmountOfEntities(int pageSize, int offset) 
            throws ProjectException {
        LOG.debug("trying to find personList for page");
        
        List<Testimony> testimonyList = null;
        PreparedStatement statement = null;
        try {
            testimonyList = new ArrayList<>();
            statement = connection.prepareStatement(SELECT_ADMIN_ARCHIVE);
            statement.setInt(1, pageSize);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Testimony testimony = new Testimony();
                Person person = new Person();
                
                person.setId(resultSet.getInt(IDPerson));
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
                        throw new ProjectException("Error trying to get access to "
                                + "find by id", ex);
                    }
                    person.setPhoto(image);
                    if(image != null) {
                        person = setEncodedImage(person);
                    }
                }
                person.setAward(resultSet.getInt(AWARD));
                testimony.setPerson(person);
                testimony.setId(resultSet.getInt(ID));
                testimony.setPoints(resultSet.getInt(POINTS));
                testimony.setWatched(resultSet.getBoolean(WATCHED));
                testimony.setTestimony(resultSet.getString(TESTIMONY));
                testimonyList.add(testimony);
            }
        } catch (SQLException ex) {
            throw new ProjectException("Error trying to find person", ex);
        } finally {
            close(statement);
        }
        return testimonyList;
    }
    
    /**
     *
     * @param id person's id
     * @return sum of all points by this id
     * @throws ProjectException
     */
    public int findSumPoints(int id) throws ProjectException {
        LOG.debug("trying to find sum of points by id");
        
        PreparedStatement statement = null;
        int sum = 0;
        try {
            statement = connection.prepareStatement(SELECT_SUM_POINTS);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                sum = resultSet.getInt(SUM);
            }
        } catch (SQLException ex) {
            throw new ProjectException("Error trying to find person", ex);
        } finally {
            close(statement);
        }
        return sum;
    }
    
    /**
     *
     * @param id person's id
     * @return Map of each user who testified by this id
     * @throws ProjectException
     */
    public Map findEachUserSumPoints(int id) throws ProjectException {
        LOG.debug("trying to find each user sum of points by id");
        
        PreparedStatement statement = null;
        Map <Integer, Integer> pointsMap = new HashMap<>();
        try {
            statement = connection.prepareStatement(SELECT_EACH_USER_SUM_POINTS);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                pointsMap.put(resultSet.getInt(USER_ID), 
                        resultSet.getInt(SUM));
            }
        } catch (SQLException ex) {
            throw new ProjectException("Error trying to find person", ex);
        } finally {
            close(statement);
        }
        return pointsMap;
    }
        
    @Override
    public List<Testimony> findAll() throws ProjectException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Optional<Testimony> findEntityById(int id) throws ProjectException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public boolean delete(int id) throws ProjectException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public boolean delete(Testimony entity) throws ProjectException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Optional<Testimony> update(Testimony testimony) throws ProjectException {
        LOG.debug("going to database to update testimony");
        
        PreparedStatement statement = null;
        
        try {
            statement = connection.prepareStatement(UPDATE_TESTIMONY);
            
            statement.setInt(1, testimony.getPoints());
            statement.setInt(2, testimony.getId());
            
            int result = statement.executeUpdate();
            if (result != 1) {
                testimony = null;
            }
        } catch (SQLException ex) {
            throw new ProjectException("Error trying to add testimony", ex);
        } 
        finally {
            close(statement);
        }
        return Optional.ofNullable(testimony);
    }

    private Person setEncodedImage(Person person) throws ProjectException {
        BufferedImage image = person.getPhoto();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try { 
            ImageIO.write(image, "jpg", os);
        } catch (IOException ex) {
            LOG.error("exception has occured");
            throw new ProjectException();
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
            String base64Encoded = new String(encodeBase64, "UTF-8");
            person.setBase64image(base64Encoded);
        } catch (IOException ex) {
            throw new ProjectException("Couldn't find image", ex);
        }
        return person;
    }



}
