/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.entity;

import by.epam.entity.Station;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

/**
 *
 * @author uks50
 */
public class RouteSingletone {
    final static int MAX_COUNTER = 4;
    private static int counter = 0;
    
    private String name;
    private Queue<Station> route = new ArrayDeque();      
    
    private RouteSingletone(){   
    }
    
    public static RouteSingletone getRouteSingletone(){
        while (counter < MAX_COUNTER) {
            counter++;
            return new RouteSingletone();
        }
        return null;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Queue<Station> getRoute() {
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
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.name);
        hash = 13 * hash + Objects.hashCode(this.route);
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
        final RouteSingletone other = (RouteSingletone) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
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
