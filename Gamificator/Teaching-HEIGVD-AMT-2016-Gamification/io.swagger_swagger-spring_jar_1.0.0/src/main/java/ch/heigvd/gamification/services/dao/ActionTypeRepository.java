/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.services.dao;

import ch.heigvd.gamification.model.ActionBadge;
import ch.heigvd.gamification.model.ActionType;
import ch.heigvd.gamification.model.Badge;
import ch.heigvd.gamification.model.Rule;
import ch.qos.logback.core.joran.action.Action;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Thibaut-PC
 */
public interface ActionTypeRepository extends CrudRepository<ActionType, Long> {
   //List<ActionType> findByName (String name);
 ActionType   findByIdAndRules(Long id, Rule rules);
 
       
}
