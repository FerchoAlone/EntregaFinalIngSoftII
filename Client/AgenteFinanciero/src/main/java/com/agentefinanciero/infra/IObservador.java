
package com.agentefinanciero.infra;

/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 */
public interface IObservador {
    //Accion a tomar si surge un cambio en el observado. Se informa ademas que cambio se realizo (estado).
    public void actualizar(String mensaje);
}
