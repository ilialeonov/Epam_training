/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.sevice;

import by.epam.entity.Candy;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Администратор
 */
public abstract class AbstractCandiesBuilder {
    
    protected Set<Candy> candies;

    public AbstractCandiesBuilder() {
        candies = new HashSet<>();
    }

    public AbstractCandiesBuilder(Set<Candy> candies) {
        this.candies = candies;
    }

    public Set<Candy> getCandies() {
        return candies;
    }
    
    abstract public void buildSetCandies(String fileName);
    
}
