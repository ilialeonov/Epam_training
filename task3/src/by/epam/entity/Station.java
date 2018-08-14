/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.entity;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Semaphore;

/**
 *
 * @author uks50
 */
public class Station {
    private String name;
    private ArrayList<Bus> buses;
    private Semaphore semaphore;
    
    public Station() {
        buses = new ArrayList();   
    }
    
    public Station(String name) {
        this.name = name;
        buses = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setMaxBusAmount(int count) {
        semaphore = new Semaphore(count);
    }
    
    public Semaphore getSemaphore() {
        return semaphore;
    }
    
    public void addBus(Bus bus) {
        buses.add(bus);
    }
    
    public void deleteBus(Bus bus) {
        buses.remove(bus);
    }
    
    public ArrayList<Bus> getBuses(){
        return buses;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.name);
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
        final Station other = (Station) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
