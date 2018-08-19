/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.entity;

import java.util.Date;
import java.util.Objects;

public class Candy {
    
    private String name;
    private int energy;
    private String energyUnit;
    private SweetType sweetType;
    private Ingridient ingridient;
    private Value value;
    private String producer;
    private Date deliveryTime;
    
    public Candy() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public String getEnergyUnit() {
        return energyUnit;
    }

    public void setEnergyUnit(String energyUnit) {
        this.energyUnit = energyUnit;
    }

    public SweetType getType() {
        return sweetType;
    }

    public void setType(SweetType sweetType) {
        this.sweetType = sweetType;
    }

    public Ingridient getIngridient() {
        return ingridient;
    }

    public void setIngridient(Ingridient ingridient) {
        this.ingridient = ingridient;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + this.energy;
        hash = 97 * hash + Objects.hashCode(this.energyUnit);
        hash = 97 * hash + Objects.hashCode(this.sweetType);
        hash = 97 * hash + Objects.hashCode(this.ingridient);
        hash = 97 * hash + Objects.hashCode(this.value);
        hash = 97 * hash + Objects.hashCode(this.producer);
        hash = 97 * hash + Objects.hashCode(this.deliveryTime);
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
        final Candy other = (Candy) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.energy != other.energy) {
            return false;
        }
        if (!Objects.equals(this.energyUnit, other.energyUnit)) {
            return false;
        }
        if (this.sweetType != other.sweetType) {
            return false;
        }
        if (!Objects.equals(this.ingridient, other.ingridient)) {
            return false;
        }
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        if (!Objects.equals(this.producer, other.producer)) {
            return false;
        }
        if (!Objects.equals(this.deliveryTime, other.deliveryTime)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Candy " + "name=" + name + ", energy=" + energy 
                + " " + energyUnit + ", sweetType=" + sweetType + " " 
                + ingridient + " " + value + ", producer=" + producer 
                + ", deliveryTime=" + deliveryTime;
    }
    
}
