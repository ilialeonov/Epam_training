/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.entity;

import java.util.Objects;

public class Ingridient {
    private int water;
    private String waterUnit;
    private int sugar;
    private String sugarUnit;
    private int fructose;
    private String fructoseUnit;
    private int vanilin;
    private String vanilinUnit;
    private ChocoType chocoType;

    public Ingridient() {
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public String getWaterUnit() {
        return waterUnit;
    }

    public void setWaterUnit(String waterUnit) {
        this.waterUnit = waterUnit;
    }

    public int getSugar() {
        return sugar;
    }

    public void setSugar(int sugar) {
        this.sugar = sugar;
    }

    public String getSugarUnit() {
        return sugarUnit;
    }

    public void setSugarUnit(String sugarUnit) {
        this.sugarUnit = sugarUnit;
    }

    public int getFructose() {
        return fructose;
    }

    public void setFructose(int fructose) {
        this.fructose = fructose;
    }

    public String getFructoseUnit() {
        return fructoseUnit;
    }

    public void setFructoseUnit(String fructoseUnit) {
        this.fructoseUnit = fructoseUnit;
    }

    public int getVanilin() {
        return vanilin;
    }

    public void setVanilin(int vanilin) {
        this.vanilin = vanilin;
    }

    public String getVanilinUnit() {
        return vanilinUnit;
    }

    public void setVanilinUnit(String vanilinUnit) {
        this.vanilinUnit = vanilinUnit;
    }

    public ChocoType getType() {
        return chocoType;
    }

    public void setType(ChocoType chocoType) {
        this.chocoType = chocoType;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.water;
        hash = 37 * hash + Objects.hashCode(this.waterUnit);
        hash = 37 * hash + this.sugar;
        hash = 37 * hash + Objects.hashCode(this.sugarUnit);
        hash = 37 * hash + this.fructose;
        hash = 37 * hash + Objects.hashCode(this.fructoseUnit);
        hash = 37 * hash + this.vanilin;
        hash = 37 * hash + Objects.hashCode(this.vanilinUnit);
        hash = 37 * hash + Objects.hashCode(this.chocoType);
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
        final Ingridient other = (Ingridient) obj;
        if (this.water != other.water) {
            return false;
        }
        if (!Objects.equals(this.waterUnit, other.waterUnit)) {
            return false;
        }
        if (this.sugar != other.sugar) {
            return false;
        }
        if (!Objects.equals(this.sugarUnit, other.sugarUnit)) {
            return false;
        }
        if (this.fructose != other.fructose) {
            return false;
        }
        if (!Objects.equals(this.fructoseUnit, other.fructoseUnit)) {
            return false;
        }
        if (this.vanilin != other.vanilin) {
            return false;
        }
        if (!Objects.equals(this.vanilinUnit, other.vanilinUnit)) {
            return false;
        }
        if (this.chocoType != other.chocoType) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (chocoType == null) {
            return "Ingridients: (" + "water=" + water + " " + waterUnit 
                + ", sugar=" + sugar + " " + sugarUnit + ", fructose=" 
                + fructose + " " + fructoseUnit + ", vanilin=" + vanilin + " " 
                + vanilinUnit + ")";
        } else {
            return "Ingridients: (" + "water=" + water + " " + waterUnit 
                + ", sugar=" + sugar + " " + sugarUnit + ", fructose=" 
                + fructose + " " + fructoseUnit + ", vanilin=" + vanilin + " " 
                + vanilinUnit + ", chocoType=" + chocoType + ")";
        }
        
    }
    
}
