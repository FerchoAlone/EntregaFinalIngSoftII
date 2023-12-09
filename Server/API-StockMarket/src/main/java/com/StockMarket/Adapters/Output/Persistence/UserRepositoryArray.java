
package com.StockMarket.Adapters.Output.Persistence;

import com.StockMarket.Ports.Output.Persistence.IUserRepository;
import com.stockmarket.commons.domain.User;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 */
@Repository
public final class UserRepositoryArray implements  IUserRepository{
    
    private static ArrayList<User> users;

    public UserRepositoryArray() {
        init();
    }
    
    
    /**
     * Initializes the simulated database information
     */
    private void init(){
        users = new ArrayList<>();
        users.add(new User("1"));
        users.add(new User("2"));
        users.add(new User("3"));
        users.add(new User("4"));
        users.add(new User("5"));
    }
      
    @Override
    public ArrayList<User> getAllUsers() {
        return users;
    }
    
    @Override
    public User getUserById(String userId) {
        for(User user:users){
            if(user.getId().equals(userId))return user;
        }
        return null;
    }
    
    @Override
    public boolean addUser(User newUser) {
        if(getUserById(newUser.getId())!=null){
            return false;
        }
        users.add(newUser);
        return true;
    }
    
}
