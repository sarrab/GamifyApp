/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.services.dao;

import ch.heigvd.gamification.model.Application;
import ch.heigvd.gamification.model.EndUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Thibaut-PC
 */
public interface EndUserRepository extends JpaRepository<EndUser, Long>  {
    
    
    
    
    List <EndUser> getBestUsers();
    EndUser findByName (String name);
    
    
    public EndUser findByIdAndApp(Long idendUser, Application app);
    public  Iterable<EndUser> findAllByApp(Application app);
    public EndUser findByNameAndApp(String name, Application app);
    public EndUser findByIdappAndApp(Long id, Application app);
    
}
