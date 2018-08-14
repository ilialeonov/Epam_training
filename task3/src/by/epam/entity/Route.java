/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.entity;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author uks50
 */
public class Route {
    private String name;
    private ArrayDeque<Station> route = new ArrayDeque();

    public Route() {
    }
    
    public Route(String name) {
        this.name = name;
    }
    
    public Route(ArrayDeque<Station> route){
        this.route = route;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayDeque<Station> getRoute() {
        return route;
    }

    public void setRoute(ArrayDeque<Station> route) {
        this.route = route;
    }
    
    public void setNextStation(Station station){
        route.add(station);
    }
    
    // returns null if there are no more stations
    public Station getNextStation(){
        return route.poll();
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.route);
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
        final Route other = (Route) obj;
        if (!Objects.equals(this.route, other.route)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
}
