/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.gamificator.Service;

import ch.heigvd.amt.gamificator.Dao.UserRepository;
import ch.heigvd.amt.gamificator.model.User;
import java.util.Collection;
import javax.annotation.Resource;
import org.apache.commons.collections.IteratorUtils;

/**
 *
 * @author Thibaut-PC
 */
public class UserServiceimpl implements UserServices{
    
    @Resource
	private UserRepository userRepository;
    

    @Override
    public Collection<User> getAllUsers() {
       return IteratorUtils.toList(this.userRepository.findAll().iterator()); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getUserById(Long id) {
        return this.getUserById(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User createUser(User user) {
       return this.userRepository.save(user); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User updateUser(User user) {
        return this.userRepository.save(user); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteUser(Long id) {
      this.userRepository.delete(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getUserByName(String shortName) {
     return this.userRepository.findByusername(shortName); //To change body of generated methods, choose Tools | Templates.
    }
    
    public UserRepository getUserRepository() {
 		return userRepository;
 	}

	public void setUserRepository(UserRepository userRepository) {
 		this.userRepository = userRepository;	
	}
        
        
        
    
    
}
