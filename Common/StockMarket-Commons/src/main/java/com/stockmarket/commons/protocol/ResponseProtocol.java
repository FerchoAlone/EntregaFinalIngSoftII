
package com.stockmarket.commons.protocol;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProtocol<T> {
    
    /**
     * Status code for response
     */
    private StatusCode status;
    /**
     * Message description 
     */
    private String message;
    /**
     * Possible output values
     */
    private List<T> values;

    public boolean addValue(T value){
        if(values==null){
            values= new ArrayList<>();
        }
        values.add(value);
        return true;
    }  
}


