/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.entity;

/**
 *
 * @author Администратор
 */
public class ComponentSkeleton implements Component{
    private String data;
    
    public ComponentSkeleton() {
        
    }
    
    public ComponentSkeleton(String data) {
        this.data = data;
    }
    
    @Override
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String getData() {
        return data;
    }
    
}
