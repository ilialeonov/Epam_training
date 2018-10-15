/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.command.util;

import by.epam.interpol.controller.SessionRequestContent;
import by.epam.interpol.exception.ProjectException;

/**
 *
 * Interface for any command wich takes control at defining where should be
 * redirected user
 */
public interface ActionCommand {

    /**
     *
     * @param requestContent consists requestParameters, requestAttributes,
     * sessionAttributes and session information
     * @return the page redirect to
     * @throws ProjectException any exception in project wrapped in it
     */
    String execute(SessionRequestContent requestContent) throws ProjectException;
}
