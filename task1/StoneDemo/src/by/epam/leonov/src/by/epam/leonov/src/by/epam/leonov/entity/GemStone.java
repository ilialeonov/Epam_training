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
    private Value value;
    
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
    
    public void setValue(Value value) {
       this.value = value;
    }
    
    public Value getValue(){
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.weight) ^ (Double.doubleToLongBits(this.weight) >>> 32));
        hash = 47 * hash + this.transparency;
        hash = 47 * hash + this.cost;
        hash = 47 * hash + Objects.hashCode(this.value);
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
        if (this.value != other.value) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + name + ", value " + value 
                +", weight = " + weight + ", transparency = " + transparency 
                + ", cost = " + cost;
    }
    
    
}
