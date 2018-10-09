/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command;

import by.epam.interpol.controller.SessionRequestContent;
import by.epam.interpol.exception.ProjectException;

/**
 *
 * @author Администратор
 */
public interface ActionCommand {
    String execute(SessionRequestContent requestContent) throws ProjectException;
}
