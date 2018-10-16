/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.entity;

import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 *
 * @author Ilia Leonov
 * entity person
 */
public class Person {
    
    private int id;
    private String name;
    private String panname;
    private int age;
    private String birthPlace;
    private String lastPlace;
    private boolean status;
    private BufferedImage photo;
    private int award;
    private String information;
    private boolean isCriminal;
    private String Base64image;
    
    /**
     *
     */
    public Person() {
    }

    /**
     *
     * @return person's id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id person's id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return person's name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name person's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return person's panname
     */
    public String getPanname() {
        return panname;
    }

    /**
     *
     * @param panname person's panname
     */
    public void setPanname(String panname) {
        this.panname = panname;
    }

    /**
     *
     * @return person's age
     */
    public int getAge() {
        return age;
    }

    /**
     *
     * @param age person's age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     *
     * @return person's birthplace
     */
    public String getBirthPlace() {
        return birthPlace;
    }

    /**
     *
     * @param birthPlace person's birthplace
     */
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    /**
     *
     * @return person's last seen place
     */
    public String getLastPlace() {
        return lastPlace;
    }

    /**
     *
     * @param lastPlace person's last seen place
     */
    public void setLastPlace(String lastPlace) {
        this.lastPlace = lastPlace;
    }

    /**
     *
     * @return person's status
     */
    public boolean getStatus() {
        return status;
    }

    /**
     *
     * @param status person's status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     *
     * @return person's photo
     */
    public BufferedImage getPhoto() {
        return photo;
    }

    /**
     *
     * @param photo person's photo
     */
    public void setPhoto(BufferedImage photo) {
        this.photo = photo;
    }

    /**
     *
     * @return person's award
     */
    public int getAward() {
        return award;
    }

    /**
     *
     * @param award person's award
     */
    public void setAward(int award) {
        this.award = award;
    }

    /**
     *
     * @return person's information
     */
    public String getInformation() {
        return information;
    }

    /**
     *
     * @param information person's information
     */
    public void setInformation(String information) {
        this.information = information;
    }

    /**
     *
     * @return if person's Criminal
     */
    public boolean isIsCriminal() {
        return isCriminal;
    }

    /**
     *
     * @param isCriminal sets person's isCrimimnal
     */
    public void setIsCriminal(boolean isCriminal) {
        this.isCriminal = isCriminal;
    }

    /**
     *
     * @return person's image
     */
    public String getBase64image() {
        return Base64image;
    }

    /**
     *
     * @param Base64image person's image
     */
    public void setBase64image(String Base64image) {
        this.Base64image = Base64image;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.id;
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.panname);
        hash = 41 * hash + this.age;
        hash = 41 * hash + Objects.hashCode(this.birthPlace);
        hash = 41 * hash + Objects.hashCode(this.lastPlace);
        hash = 41 * hash + (this.status ? 1 : 0);
        hash = 41 * hash + Objects.hashCode(this.photo);
        hash = 41 * hash + this.award;
        hash = 41 * hash + Objects.hashCode(this.information);
        hash = 41 * hash + (this.isCriminal ? 1 : 0);
        hash = 41 * hash + Objects.hashCode(this.Base64image);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.panname, other.panname)) {
            return false;
        }
        if (this.age != other.age) {
            return false;
        }
        if (!Objects.equals(this.birthPlace, other.birthPlace)) {
            return false;
        }
        if (!Objects.equals(this.lastPlace, other.lastPlace)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.photo, other.photo)) {
            return false;
        }
        if (this.award != other.award) {
            return false;
        }
        if (!Objects.equals(this.information, other.information)) {
            return false;
        }
        if (this.isCriminal != other.isCriminal) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", name=" + name + ", panname=" + panname 
                + ", age=" + age + ", birthPlace=" + birthPlace + ", lastPlace=" 
                + lastPlace + ", status=" + status + ", photo=" + photo + ", award=" 
                + award + ", information=" + information + ", isCriminal=" + isCriminal 
                + ", Base64image=" + Base64image + '}';
    }
    

}