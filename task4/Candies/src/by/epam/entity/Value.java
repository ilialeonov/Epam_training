/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.entity;

import java.util.Objects;

/**
 *
 * @author Администратор
 */
public class Value {
    private int protein;
    private String proteinUnit;
    private int fat;
    private String fatUnit;
    private int carbohydrate;
    private String carbohydrateUnit;

    public Value() {
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public String getProteinUnit() {
        return proteinUnit;
    }

    public void setProteinUnit(String proteinUnit) {
        this.proteinUnit = proteinUnit;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public String getFatUnit() {
        return fatUnit;
    }

    public void setFatUnit(String fatUnit) {
        this.fatUnit = fatUnit;
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public String getCarbohydrateUnit() {
        return carbohydrateUnit;
    }

    public void setCarbohydrateUnit(String carbohydrateUnit) {
        this.carbohydrateUnit = carbohydrateUnit;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.protein;
        hash = 19 * hash + Objects.hashCode(this.proteinUnit);
        hash = 19 * hash + this.fat;
        hash = 19 * hash + Objects.hashCode(this.fatUnit);
        hash = 19 * hash + this.carbohydrate;
        hash = 19 * hash + Objects.hashCode(this.carbohydrateUnit);
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
        final Value other = (Value) obj;
        if (this.protein != other.protein) {
            return false;
        }
        if (!Objects.equals(this.proteinUnit, other.proteinUnit)) {
            return false;
        }
        if (this.fat != other.fat) {
            return false;
        }
        if (!Objects.equals(this.fatUnit, other.fatUnit)) {
            return false;
        }
        if (this.carbohydrate != other.carbohydrate) {
            return false;
        }
        if (!Objects.equals(this.carbohydrateUnit, other.carbohydrateUnit)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Value: (" + "protein=" + protein + " " + proteinUnit 
                + ", fat=" + fat + " " + fatUnit + ", carbohydrate=" 
                + carbohydrate + " " + carbohydrateUnit + ")";
    }
}
