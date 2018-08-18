/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.runner;

import by.epam.entity.Bus;
import by.epam.entity.Station;
import by.epam.entity.RouteSingletone;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;



public class BusRunner {
    private static final Logger LOG = Logger.getLogger(BusRunner.class);
    public static void main(String[] args){
        LOG.info("Bus moving imitation");
        
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
    
        RouteSingletone RouteAD = RouteSingletone.getRouteSingletone();
        RouteAD.setName("A - D");
        RouteAD.setNextStation(a);
        RouteAD.setNextStation(b);
        RouteAD.setNextStation(c);
        RouteAD.setNextStation(d);
        
        RouteSingletone RouteGC = RouteSingletone.getRouteSingletone();
        RouteGC.setName("G - C");
        RouteGC.setNextStation(g);
        RouteGC.setNextStation(f);
        RouteGC.setNextStation(e);
        RouteGC.setNextStation(d);
        RouteGC.setNextStation(c);
        
        RouteSingletone RouteAE = RouteSingletone.getRouteSingletone();
        RouteAE.setName("A - E");
        RouteAE.setNextStation(a);
        RouteAE.setNextStation(g);
        RouteAE.setNextStation(d);
        RouteAE.setNextStation(f);
        RouteAE.setNextStation(e);
        
        RouteSingletone RouteAB = RouteSingletone.getRouteSingletone();
        RouteAB.setName("A - B");
        RouteAB.setNextStation(a);
        RouteAB.setNextStation(c);
        RouteAB.setNextStation(f);
        RouteAB.setNextStation(d);
        RouteAB.setNextStation(b);
        
        Bus busAD = new Bus();
        busAD.setRoute(RouteAD);
        
        Bus busGC = new Bus();
        busGC.setRoute(RouteGC);
        
        Bus busAE = new Bus();
        busAE.setRoute(RouteAE);
        
        Bus busAB = new Bus();
        busAB.setRoute(RouteAB);
        
        
        try {
            busAD.start();
            TimeUnit.MILLISECONDS.sleep(400);
            busGC.start();
            TimeUnit.MILLISECONDS.sleep(400);
            busAE.start();
            TimeUnit.MILLISECONDS.sleep(400);
            busAB.start();
            TimeUnit.MILLISECONDS.sleep(400);
        } catch (InterruptedException ex) {
            LOG.info("An IException has occured while imitating", ex);
        }
        
    }
    
     
    
}