package by.epam.interpol.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Objects;

/**
 *
 * @author Ilia Leonov
 * testimony entity
 */
public class Testimony {
    
    private int id;
    private int userId;
    private int personId;
    private int points;
    private boolean watched;
    private String testimony;
    private Person person;

    /**
     *
     */
    public Testimony() {
    }

    /**
     *
     * @return testimonies id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id testimonies id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return testimonies userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     *
     * @param userId testimonies userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     *
     * @return testimonies person id
     */
    public int getPersonId() {
        return personId;
    }

    /**
     *
     * @param personId testimonies person id
     */
    public void setPersonId(int personId) {
        this.personId = personId;
    }

    /**
     *
     * @return testimonies points
     */
    public int getPoints() {
        return points;
    }

    /**
     *
     * @param points testimonies points
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     *
     * @return if testimonies watched
     */
    public boolean getIsWatched() {
        return watched;
    }

    /**
     *
     * @param watched testimonies watched
     */
    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    /**
     *
     * @return testimonies testimony
     */
    public String getTestimony() {
        return testimony;
    }

    /**
     *
     * @param testimony testimonies testimony
     */
    public void setTestimony(String testimony) {
        this.testimony = testimony;
    }

    /**
     *
     * @return testimonies person
     */
    public Person getPerson() {
        return person;
    }

    /**
     *
     * @param person testimonies person
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id;
        hash = 47 * hash + this.userId;
        hash = 47 * hash + this.personId;
        hash = 47 * hash + this.points;
        hash = 47 * hash + (this.watched ? 1 : 0);
        hash = 47 * hash + Objects.hashCode(this.testimony);
        hash = 47 * hash + Objects.hashCode(this.person);
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
        final Testimony other = (Testimony) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.userId != other.userId) {
            return false;
        }
        if (this.personId != other.personId) {
            return false;
        }
        if (this.points != other.points) {
            return false;
        }
        if (this.watched != other.watched) {
            return false;
        }
        if (!Objects.equals(this.testimony, other.testimony)) {
            return false;
        }
        if (!Objects.equals(this.person, other.person)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Testimony{" + "id=" + id + ", userId=" + userId + ", personId=" 
                + personId + ", points=" + points + ", watched=" + watched 
                + ", testimony=" + testimony + ", person=" + person + '}';
    }

    
}