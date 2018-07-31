/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.entity;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author uks50
 */
public class Composite implements Component{
    private String data;
    private List<Component> componentList;
            
    public Composite() {
    }
    
    public Composite(String data) {
        this.data = data;
    }
    
    public void setComponents(List<Component> componentList){
        this.componentList = componentList;
    }
    
    public List<Component> getComponents(){
        return componentList;
    }
    
    public Component getChild(int i){
        return componentList.get(i);
    }
    
    public int getSize(){
        return componentList.size();
    }

    @Override
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.data);
        hash = 97 * hash + Objects.hashCode(this.componentList);
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
        final Composite other = (Composite) obj;
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (!Objects.equals(this.componentList, other.componentList)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder str = null;
        for (Component x: componentList) {
            str.append(x).append(" ");
        }
        return str.toString();
    }

}    