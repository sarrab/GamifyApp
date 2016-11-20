/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.gamificator.Dao;

import ch.heigvd.amt.gamificator.model.Badge;
import java.io.Serializable;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Thibaut-PC
 */
public interface BadgeRepository  extends CrudRepository<Badge, Long>{
    
    Badge findByname(String name);
 
}
