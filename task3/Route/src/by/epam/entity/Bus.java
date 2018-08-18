/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

public class Bus extends Thread {
    
    private static final Logger LOG = Logger.getLogger(Bus.class);
    private static final String PATH = "data.txt";
    private static int MAX_FIRST_ENTRENCE = 20;
    private static int MAX_EXIT = 10;
    private static int MAX_CHANGE = 5;
    private static int MAX_NEXT_ENTRENCE = 10;
    private static int TIME_MOVING = 3;
    private static int TIME_STAYING = 1;
    
    static {
        try (FileReader fileReader = new FileReader(PATH);
                BufferedReader reader = new BufferedReader(fileReader)) {
            MAX_FIRST_ENTRENCE = Integer.parseInt(reader.readLine());
            MAX_EXIT = Integer.parseInt(reader.readLine());
            MAX_CHANGE = Integer.parseInt(reader.readLine());
            MAX_FIRST_ENTRENCE = Integer.parseInt(reader.readLine());
            TIME_MOVING = Integer.parseInt(reader.readLine());
            TIME_STAYING = Integer.parseInt(reader.readLine());
        } catch (IOException ex) {
            LOG.info("default data is taken");
            LOG.error("Access file error", ex);
        }
    }
    
    private static Random random = new Random();
    
    private int passengers;
    private int passengersIn;
    private int passengersOut;
    private int passengersChange;
    private RouteSingletone route;
    private Station currentStation;
    private Station nextStation;
   
    public Bus(){
    }
    
    public void setPassengers(int passengers){
        this.passengers = passengers;
    }
    
    public int getPassengers(){
        return passengers;
    }
    
    public void setRoute(RouteSingletone route){
        this.route = route;
    }
    
    public RouteSingletone getRoute() {
        return route;
    }
    
    public void run(){
        currentStation = route.getNextStation();
        firstStay();
        nextStation = route.getNextStation();
         do {
            move();
            currentStation = nextStation;
            System.out.println("++++++++++++++++");
            stay();
        } while((nextStation = route.getNextStation()) != null);
         move();
        lastStay();
    }    
    
    private int getPassengersChange(){
        passengersChange = random.nextInt(MAX_CHANGE);
        return passengersChange;
    }
        
    private void move() {
        try {
            LOG.info("Bus route " 
                    + route + " is moving from the station " + currentStation 
                    + " to the station " + nextStation);
            
            TimeUnit.SECONDS.sleep(TIME_MOVING);
        } catch (InterruptedException ex) {
            LOG.error("IException while moving", ex);
            Thread.currentThread().interrupt();
        }
    }
    
    private void firstStay() {
        LOG.info("Bus route " + route 
                    + " is staying at the station " + currentStation);
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
            currentStation.getLock().lock();
            currentStation.getSemaphore().acquire();
            currentStation.addBus(this);
        } catch (InterruptedException ex) {
            LOG.error("IException while staying", ex);
            Thread.currentThread().interrupt();
        } finally {
            currentStation.getLock().unlock();
        }
            
        LOG.info("Bus route " + route 
                + " is staying at the station " + currentStation);
        
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
            
            currentStation.getLock().lock();
            currentStation.getSemaphore().release();
            currentStation.deleteBus(this);     
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            LOG.error("IException while staying", ex);
        } finally {
            currentStation.getLock().unlock();
        }
    }
    
    private void changePeople() { 
        ArrayList<Bus> stationBuses = currentStation.getBuses();
        for (Bus bus : stationBuses) {
            if (bus != this) {
                try {
                    currentStation.getLock().lock();
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
                } finally {
                    currentStation.getLock().unlock();
                }              
                LOG.info(passengersOut +" persons change bus route " 
                        + this.getRoute() + " for bus route " + bus.getRoute());
            }
        }
    } 
}