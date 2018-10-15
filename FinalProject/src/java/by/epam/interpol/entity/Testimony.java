package by.epam.interpol.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Objects;

/**
 *
 * @author Администратор
 */
public class Testimony {
    
    private int id;
    private int userId;
    private int personId;
    private int points;
    private boolean watched;
    private String testimony;
    private Person person;

    public Testimony() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean getIsWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public String getTestimony() {
        return testimony;
    }

    public void setTestimony(String testimony) {
        this.testimony = testimony;
    }

    public Person getPerson() {
        return person;
    }

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