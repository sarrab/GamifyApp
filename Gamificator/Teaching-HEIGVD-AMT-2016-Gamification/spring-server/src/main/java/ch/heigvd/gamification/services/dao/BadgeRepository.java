/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.services.dao;


import ch.heigvd.gamification.model.Badge;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Thibaut-PC
 */
public interface BadgeRepository  extends CrudRepository<Badge, Long>{
    
    Badge findByName(String name);
}
