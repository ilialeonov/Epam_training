/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.entity;

import java.util.Objects;

/**
 *
 * @author Ilia Leonov
 * User entity
 */
public class User {
    private int id;
    private String name;
    private String login;
    private String mail;
    private double money;
    private boolean status;
    
    /**
     *
     */
    public User() {
    }

    /**
     *
     * @return user's id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id user's id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return user's name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name user's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return user's login
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @param login user's login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     *
     * @return user's mail
     */
    public String getMail() {
        return mail;
    }

    /**
     *
     * @param mail user's mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     *
     * @return if user's admin
     */
    public boolean isAdmin() {
        return status;
    }

    /**
     *
     * @param status user's admin
     */
    public void setAdmin(boolean status) {
        this.status = status;
    }

    /**
     *
     * @return user's money
     */
    public double getMoney() {
        return money;
    }

    /**
     *
     * @param money user's money
     */
    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.login);
        hash = 67 * hash + Objects.hashCode(this.mail);
        hash = 67 * hash + (int) this.money;
        hash = 67 * hash + (this.status ? 1 : 0);
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
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.mail, other.mail)) {
            return false;
        }
        if (this.money != other.money) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User" + "id=" + id + ", name=" + name + ", login=" + login 
                + ", mail=" + mail + ", money=" + money + ", status=" + status;
    }

}
