/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.gamificator.Service;

import ch.heigvd.amt.gamificator.model.User;
import java.util.Collection;

/**
 *
 * @author Thibaut-PC
 */
public interface UserServices {
    
    Collection<User> getAllUsers();
	
	User getUserById(Long id);
	
	User createUser(User place);
	
	User updateUser(User place); void deleteUser(Long id);
	
	User getUserByName(String shortName);
    
    
}
