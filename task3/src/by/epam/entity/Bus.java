/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.entity;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author uks50
 */
public class Bus extends Thread {
    private static final Logger LOG = Logger.getLogger(Bus.class);
    private int passengers;
    private int passengersChange;
    private Route route;
    private Station startStation;
    private Station nextStation;
    private Random random = new Random();
    private static Exchanger<Integer> exchanger = new Exchanger();
        
    public Bus(){
    }
    
    public void setPassengers(int passengers){
        this.passengers = passengers;
    }
    
    public int getPassengers(){
        return passengers;
    }
    
    private int getPassengersChange(){
        passengersChange = random.nextInt(4);
        return passengersChange;
    }
    
    public void setRoute(Route route){
        this.route = route;
    }
    
    public Route getRoute() {
        return route;
    }
    
    public void move() {
        try {
            LOG.info("Bus by the route " 
                    + route + " is moving from the station " + startStation 
                    + " to the station " + nextStation);
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ex) {
            LOG.error("IException moving", ex);
        }
    }
    
    public void stay() {
        
        try {
            startStation = nextStation; 
            startStation.getSemaphore().acquire();
            startStation.addBus(this);
            LOG.info("Bus by the route "
                    + route + " is staying at the station " + startStation);
            LOG.info("People leave bus");
            int passengersOut = random.nextInt(10);
            passengers -= passengersOut;
            changePeople();
            LOG.info("People enter bus");
            int passengersIn = random.nextInt(10);
            passengers += passengersIn;  
            TimeUnit.SECONDS.sleep(1);
            changePeople();
            startStation.getSemaphore().release();
            startStation.deleteBus(this);
        } catch (InterruptedException ex) {
            LOG.error("IException staying", ex);
        }
    }
    
    public void changePeople() { 
        ArrayList<Bus> stationBuses = startStation.getBuses();
        for (Bus bus : stationBuses) {
            if (bus != this) {
                int passengersIn = bus.getPassengersChange();
                int passengersOut = this.getPassengersChange();
                this.setPassengers(passengers + passengersIn - passengersOut);
                bus.setPassengers(bus.getPassengers() - passengersIn + passengersOut);
                LOG.info("People change bus on route " + this.getRoute() +
                        "for bus in route" + bus.getRoute());
            }
        }
    }
    
    public void run(){
        startStation = route.getNextStation();
        
        while((nextStation = route.getNextStation()) != null) {
            move();
            stay();
        }
        LOG.debug("Last station. People leave the bus.");  
    }
}
