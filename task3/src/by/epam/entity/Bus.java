/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.entity;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;

/**
 *
 * @author uks50
 */
public class Bus extends Thread {
    final int MAX_FIRST_ENTRENCE = 20;
    final int MAX_EXIT = 10;
    final int MAX_CHANGE = 5;
    final int MAX_NEXT_ENTRENCE = 10;
    final int TIME_MOVING = 3;
    final int TIME_STAYING = 1;
    
    private static final Logger LOG = Logger.getLogger(Bus.class);
    private static Exchanger<Integer> exchanger = new Exchanger();
    private Random random = new Random();
    private static Lock lock = new ReentrantLock();
    
    private int passengers;
    private int passengersIn;
    private int passengersOut;
    private int passengersChange;
    private Route route;
    private Station startStation;
    private Station nextStation;
   
    public Bus(){
    }
    
    public void setPassengers(int passengers){
        this.passengers = passengers;
    }
    
    public int getPassengers(){
        return passengers;
    }
    
    private int getPassengersChange(){
        passengersChange = random.nextInt(MAX_CHANGE);
        return passengersChange;
    }
    
    public void setRoute(Route route){
        this.route = route;
    }
    
    public Route getRoute() {
        return route;
    }
    
    private void move() {
        try {
            LOG.info("Bus route " 
                    + route + " is moving from the station " + startStation 
                    + " to the station " + nextStation);
            
            TimeUnit.SECONDS.sleep(TIME_MOVING);
        } catch (InterruptedException ex) {
            LOG.error("IException while moving", ex);
        }
    }
    
    private void firstStay() {
        LOG.info("Bus route " + route 
                    + " is staying at the station " + startStation);
        passengersIn = random.nextInt(MAX_FIRST_ENTRENCE);
        LOG.info(passengersIn + " passengers enter the bus");
        setPassengers(passengersIn);
    }
    
    private void lastStay() {
        LOG.info("Last station");
        LOG.info(passengers + " passengers leave the bus");
        passengers = 0;
    }
    
    private void stay() {
        
        try {
            lock.lock();
            startStation.getSemaphore().acquire();
            startStation.addBus(this);
        } catch (InterruptedException ex) {
            LOG.error("IException while staying", ex);
        } finally {
            lock.unlock();
        }
        
             
        LOG.info("Bus route " + route 
                + " is staying at the station " + startStation);
        
        passengersOut = random.nextInt(MAX_EXIT);
        while (passengers < passengersOut) {
            passengersOut = random.nextInt(MAX_EXIT);
        }
        
        LOG.info("Bus route " + route + " " + passengersOut 
                + " persons leave the bus");
        passengers -= passengersOut;
              
        changePeople();
            
        passengersIn = random.nextInt(MAX_NEXT_ENTRENCE);
        LOG.info("Bus route " + route + " " + passengersIn 
                + " persons enter bus");
        passengers += passengersIn; 
        
        try { 
            TimeUnit.SECONDS.sleep(TIME_STAYING);
            
            changePeople();
            
            LOG.info("there are " + passengers + 
                    " passengers in the bus route " + route);
            
            lock.lock();
            startStation.getSemaphore().release();
            startStation.deleteBus(this);     
        } catch (InterruptedException ex) {
            LOG.error("IException while staying", ex);
        } finally {
            lock.unlock();
        }
    }
    
    private void changePeople() { 
        ArrayList<Bus> stationBuses = startStation.getBuses();
        for (Bus bus : stationBuses) {
            if (bus != this) {
                lock.lock();
                passengersIn = bus.getPassengersChange();
                passengersOut = this.getPassengersChange();
                if (passengers < passengersOut 
                        || bus.getPassengers() < passengersIn) {
                    passengersIn = bus.getPassengersChange();
                    passengersOut = this.getPassengersChange();
                }
                this.setPassengers(passengers + passengersIn - passengersOut);
                bus.setPassengers(bus.getPassengers() 
                        - passengersIn + passengersOut);
                lock.unlock();
                
                LOG.info(passengersOut +" persons change bus route " 
                        + this.getRoute() + " for bus route " + bus.getRoute());
            }
        }
    }
    
    public void run(){
        startStation = route.getNextStation();
        firstStay();
        nextStation = route.getNextStation();
        while(nextStation != null) {
            move();
            startStation = nextStation;
            nextStation = route.getNextStation();
            stay();
        }
        lastStay();
    }
}