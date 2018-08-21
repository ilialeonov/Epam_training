/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.epam.util;

/**
 *
 * @author uks50
 */
public class CandyBuilderFactory {
    private enum TypeParser {
        SAX, STAX, DOM
    }
    
    public AbstractCandiesBuilder createStudentBuilder (String typeParser) {
        TypeParser parser = TypeParser.valueOf(typeParser.toUpperCase());
        switch (parser) {
            case SAX:
                return new CandiesSaxBuilder();
            case STAX:
                return new CandiesStaxBuilder();
            case DOM:
                return new CandiesDOMBuilder();
            default:
                throw new EnumConstantNotPresentException(
                        parser.getDeclaringClass(), parser.name());
        }
    }
}
