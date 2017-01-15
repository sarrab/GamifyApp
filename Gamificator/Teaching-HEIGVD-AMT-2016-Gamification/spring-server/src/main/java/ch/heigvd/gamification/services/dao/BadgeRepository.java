/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.services.dao;


import ch.heigvd.gamification.model.Application;
import ch.heigvd.gamification.model.Badge;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Thibaut-PC
 */
public interface BadgeRepository  extends CrudRepository<Badge, Long>{
    
    Badge findByName(String name);
    Badge findByIdAndApp(Long id, Application app);
    List<Badge> findAllByApp(Application app);
}
