/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.gamificator.Dao;

import ch.heigvd.amt.gamificator.model.User;
import java.io.Serializable;
import static org.apache.tomcat.jni.User.username;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Thibaut-PC
 */
public interface UserRepository extends CrudRepository<User, Long> {
    
    User findByusername(String username);
    
}
