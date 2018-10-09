/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.dao.daoimpl;

import by.epam.interpol.dao.AbstractTestimonyDao;
import by.epam.interpol.entity.Person;
import by.epam.interpol.entity.Testimony;
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
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 *
 * @author Администратор
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
    private final String AWARD = "award";
    private final String POINTS = "points";
    private final String TESTIMONY = "testimony";

    
    private static final String CREATE_TESTIMONY = "INSERT INTO " 
            +"interpol.person_testimony (`user_id`, `person_id`, `testimony`)\n" 
            +"VALUE (?, ?, ?)";

    private static final String SELECT_WITH_OFFSET = "SELECT person_id, name, "
            + "panname, age, t2.region AS birthP, t3.region AS lastP,\n" 
            +" photo, award, testimony, found, points \n"
            + "FROM interpol.person_testimony t LEFT JOIN interpol.person t1 ON \n" 
            +"t.person_id = t1.id_person JOIN interpol.region t2 ON\n" 
            +"t2.id_region = t1.birth_region JOIN interpol.region t3 ON\n" 
            +"t3.id_region = t1.last_seen_region\n" 
            +"WHERE user_id = ? AND isCriminal = ? AND found = b'0' "
            + "ORDER BY id DESC LIMIT ? OFFSET ?";

    private static final String SELECT_ARCHIVE_WITH_OFFSET = "SELECT person_id, name, "
            + "panname, age, t2.region AS birthP, t3.region AS lastP,\n" 
            +" photo, award, testimony, found, points \n"
            + "FROM interpol.person_testimony t LEFT JOIN interpol.person t1 ON \n" 
            +"t.person_id = t1.id_person JOIN interpol.region t2 ON\n" 
            +"t2.id_region = t1.birth_region JOIN interpol.region t3 ON\n" 
            +"t3.id_region = t1.last_seen_region\n" 
            +"WHERE user_id = ? AND found = b'1' "
            + "ORDER BY id DESC LIMIT ? OFFSET ?";
    
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
                LOG.debug("autoID " + autoId);
                testimony.setId(autoId);
            }
        } catch (SQLException ex) {
            LOG.error("exception:", ex);
            throw new ProjectException("Error trying to add testimony", ex);
        } 
        finally {
            close(statement);
        }
        return Optional.ofNullable(autoId);
    }
 
    public List<Testimony> findAmountOfEntities(int pageSize, int offset, int userId, 
            boolean isCriminal) throws ProjectException {
        
        LOG.debug("trying to find personList for page");
        
        List<Testimony> testimonyList = null;
        PreparedStatement statement = null;
        try {
            testimonyList = new ArrayList<>();
            statement = connection.prepareStatement(SELECT_WITH_OFFSET);
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
    
    public List<Testimony> findAmountOfEntities(int pageSize, int offset, Integer idUser) throws ProjectException {
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
    
    @Override
    public List<Testimony> findAll() throws ProjectException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<Testimony> findEntityById(int id) throws ProjectException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(int id) throws ProjectException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Testimony entity) throws ProjectException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Optional<Testimony> update(Testimony entity) throws ProjectException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
