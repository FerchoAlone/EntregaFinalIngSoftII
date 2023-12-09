
package com.StockMarket.Ports.Output.Persistence;

import com.stockmarket.commons.domain.User;
import java.util.ArrayList;

/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 */
public interface IUserRepository {
    
    /**
     * Get all users of "database"
     * @return Array of users
     */
    ArrayList<User> getAllUsers();
    
    /**
     * Get a user by Id
     * @param userId
     * @return User with userId or in case error null
     */
    User getUserById(String userId);
    
    /**
     * Add newUser to array of users if the id of the user
     * isn't duplicate
     * @param newUser
     * @return True if the user is added, False if the id already exist
     */
    boolean addUser(User newUser);
      
}
