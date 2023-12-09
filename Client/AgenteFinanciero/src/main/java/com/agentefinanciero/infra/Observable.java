
package com.agentefinanciero.infra;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 */
public abstract class Observable {
    //Lista de observadores
    private List<IObservador> observadores;
    
    //Agregar un observador
    public void addObservador(IObservador Obs){
        if(observadores==null)
            observadores = new ArrayList<>();
        observadores.add(Obs);
    }
    
    //Eliminar un observador
    public void removeObservador(IObservador Obs){
        if(observadores!=null) observadores.remove(Obs);
    }
    
    //Se actualiza cada uno de los observadores con el estado del observado
    public void notificarTodos(String mensaje){
        for(IObservador each:observadores){
            each.actualizar(mensaje);
        }
    }  

}
