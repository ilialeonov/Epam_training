/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command;

import by.epam.interpol.controller.SessionRequestContent;
import by.epam.interpol.exception.ProjectException;
import by.epam.interpol.logic.PersonLogic;

/**
 *
 * @author Администратор
 */
class WantedCommand implements ActionCommand {

    public WantedCommand(PersonLogic personLogic) {
    }

    @Override
    public String execute(SessionRequestContent requestContent) throws ProjectException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
