
package com.stockmarket.commons.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatchRequest{
    
    /**
     * Stock Id
     */
    private String id;
    /**
     * Parameter to update
     */
    private String param;
    /**
     * New value
     */
    private String value;
   
}
