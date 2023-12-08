
package com.StockMarket.APIStockMarket.Service;

import com.StockMarket.APIStockMarket.Access.IUserRepository;
import com.stockmarket.commons.domain.User;
import com.stockmarket.commons.protocol.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 */
@Service
public class UserService {
    
    @Autowired
    private IUserRepository userDataBase;
    
    /**
     * Registe a new user with id on the "database" of users
     * @param id
     * @return True in successfull case, False if the id already exist
     */
    public StatusCode registerUser(String id){
        boolean returnValue;
        User newUser = new User(id);
        returnValue = userDataBase.addUser(newUser);
        if(returnValue)return StatusCode.RESPONSE_SUCCESS; 
         
        return StatusCode.ALREADY_EXISTS;
    }
}
