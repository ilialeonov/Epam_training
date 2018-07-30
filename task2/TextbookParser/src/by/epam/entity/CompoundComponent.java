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
public interface CompoundComponent extends Component {
    public Component getChild(int i);
    public int getSize();
    public void parse();
}
