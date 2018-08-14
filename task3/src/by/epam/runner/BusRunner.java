/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.runner;

import by.epam.entity.Bus;
import by.epam.entity.Route;
import by.epam.entity.Station;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BusRunner {
    public static void main(String[] args){
        Station a = new Station("A");
        a.setMaxBusAmount(4);
        Station b = new Station("B");
        b.setMaxBusAmount(2);
        Station c = new Station("C");
        c.setMaxBusAmount(3);
        Station d = new Station("D");
        d.setMaxBusAmount(2);
        Station e = new Station("E");
        e.setMaxBusAmount(4);
        Station f = new Station("F");
        f.setMaxBusAmount(2);
        Station g = new Station("G");
        g.setMaxBusAmount(2);
    
        Route RouteAD = new Route("A - D");
        RouteAD.setNextStation(a);
        RouteAD.setNextStation(b);
        RouteAD.setNextStation(c);
        RouteAD.setNextStation(d);
        
        Route RouteGC = new Route("G - C");
        RouteGC.setNextStation(g);
        RouteGC.setNextStation(f);
        RouteGC.setNextStation(e);
        RouteGC.setNextStation(d);
        RouteGC.setNextStation(c);
        
        Route RouteAE = new Route("A - E");
        RouteAE.setNextStation(a);
        RouteAE.setNextStation(g);
        RouteAE.setNextStation(d);
        RouteAE.setNextStation(f);
        RouteAE.setNextStation(e);
        
        Route RouteAB = new Route("A - B");
        RouteAB.setNextStation(a);
        RouteAB.setNextStation(c);
        RouteAB.setNextStation(f);
        RouteAB.setNextStation(d);
        RouteAB.setNextStation(b);
        
        Bus busAD = new Bus();
        busAD.setPassengers(80);
        busAD.setRoute(RouteAD);
        
        Bus busGC = new Bus();
        busGC.setPassengers(64);
        busGC.setRoute(RouteGC);
        
        Bus busAE = new Bus();
        busAE.setPassengers(24);
        busAE.setRoute(RouteAE);
        
        Bus busAB = new Bus();
        busAB.setPassengers(42);
        busAB.setRoute(RouteAB);
        
        
        try {
            TimeUnit.SECONDS.sleep(1);
            busAD.start();
            TimeUnit.MILLISECONDS.sleep(400);
            busGC.start();
            TimeUnit.MILLISECONDS.sleep(400);
            busAE.start();
            TimeUnit.MILLISECONDS.sleep(400);
            busAB.start();
            TimeUnit.MILLISECONDS.sleep(400);
        } catch (InterruptedException ex) {
            
        }
        
    }
    
     
    
}
