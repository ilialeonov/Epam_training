/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.entity;

import java.util.List;

/**
 *
 * @author Администратор
 */
public abstract class CompoundComponentSkeleton implements CompoundComponent{
    private String data;
    private List<Component> componentList;
            
    public CompoundComponentSkeleton() {
    }
    
    public CompoundComponentSkeleton(String data) {
        this.data = data;
    }
    
    public void setComponents(List<Component> componentList){
        this.componentList = componentList;
    }
    
    public List<Component> getComponents(){
        return componentList;
    }
    
    @Override
    public Component getChild(int i){
        return componentList.get(i);
    }
    
    @Override
    public int getSize(){
        return componentList.size();
    }
    
    @Override
    public abstract void parse();

    @Override
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String getData() {
        return data;
    }
    
    public void parseChildren(){
        for (Component x: componentList){
            if (x instanceof CompoundComponent) {
                CompoundComponent xc = (CompoundComponent) x;
                xc.parse();
            } else {
                System.out.println("***********");
                System.out.println(x.getData());
            }
        }
    }
}
