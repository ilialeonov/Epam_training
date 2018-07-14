/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.leonov.entity;

import java.util.Objects;

/**
 *
 * @author Администратор
 */
public class GemStone {
    private String name;
    private double weight;
    private int transparency;
    private int cost;
    
    public GemStone() {
    }

    protected GemStone(String name, double weight, int transparency, int cost) {
        this.name = name;
        this.weight = weight;
        this.transparency = transparency;
        this.cost = cost;
    }   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getTransparency() {
        return transparency;
    }

    public void setTransparency(int transparency) {
        this.transparency = transparency;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.weight) 
                ^ (Double.doubleToLongBits(this.weight) >>> 32));
        hash = 59 * hash + this.transparency;
        hash = 59 * hash + this.cost;
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
        final GemStone other = (GemStone) obj;
        if (this.name != other.name) {
            return false;
        }
        if (this.weight != other.weight) {
            return false;
        }
        if (this.transparency != other.transparency) {
            return false;
        }
        if (this.cost != other.cost) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + name + ", weight = " + weight 
                + ", transparency = " + transparency + ", cost = " + cost;
    }
    
    
}
