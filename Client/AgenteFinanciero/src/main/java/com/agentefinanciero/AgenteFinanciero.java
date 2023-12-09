
package com.agentefinanciero;

import com.agentefinanciero.Subscriber.RabbitMQListener;
import com.agentefinanciero.Presentation.AgenteFinancieroView;
import javax.swing.JOptionPane;


/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 */
public class AgenteFinanciero {


    public static void main(String[] argv) throws Exception {
        
        String id = JOptionPane.showInputDialog("Ingresa tu numero de id por favor: ");
        
        RabbitMQListener listener = new RabbitMQListener( id);
        AgenteFinancieroView view = new AgenteFinancieroView(); 
        listener.addObservador(view);
        
        listener.listenNotifications();     
        view.setVisible(true);
          
    }
}
