/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.interpol.exception;

/**
 *
 * @author Администратор
 */
public class TimeLimitExceededException extends Exception{

    public TimeLimitExceededException() {
    }

    public TimeLimitExceededException(String message) {
        super(message);
    }

    public TimeLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeLimitExceededException(Throwable cause) {
        super(cause);
    }
    
}
