/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.leonov.view;

import by.epam.leonov.entity.GemStone;
import by.epam.leonov.entity.Jewelry;
import java.util.List;

public class View {
    public void showNecklace(Jewelry necklace){
        System.out.println(necklace);
    }
    
    public void showGemsWeight(double weight){
        System.out.printf("Common weight of all gems is %.2f  carats.\n\n", weight);
    }
    
    public void showGemsCost(int cost){
        System.out.println("Common cost of all gems is " + cost + " $.\n");
    }    

    public void showInTransparencyInterval(List<GemStone> inInterval) {
        System.out.println("GemStones in interval of the chosen tranparency: ");
        for (GemStone stone : inInterval) {
            System.out.println(stone);
        }
    }
    
    
}
