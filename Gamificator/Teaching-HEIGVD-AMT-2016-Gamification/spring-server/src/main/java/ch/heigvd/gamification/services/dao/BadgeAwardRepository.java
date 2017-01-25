/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.services.dao;

import ch.heigvd.gamification.model.Badge;
import ch.heigvd.gamification.model.BadgeAward;
import ch.heigvd.gamification.model.EndUser;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Thibaut-PC
 */
public interface BadgeAwardRepository extends CrudRepository<BadgeAward, Long>{
    
    BadgeAward findByIdAndBadge(Long id, Badge badge);
    BadgeAward findByIdAndEndUser(Long id, EndUser endUser);
}
