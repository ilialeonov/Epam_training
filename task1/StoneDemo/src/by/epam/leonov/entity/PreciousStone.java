/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.leonov.entity;

/**
 *
 * @author Администратор
 */
public class PreciousStone extends GemStone {
    
    public PreciousStone() {
    }
    
    protected PreciousStone(String name, double weight, 
            int transparency, int cost) {
        super(name, weight, transparency, cost);
    }


}
