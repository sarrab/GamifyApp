/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.services.dao;

import ch.heigvd.gamification.model.ActionPoints;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Thibaut-PC
 */
public interface ActionPointRepository extends CrudRepository<ActionPoints, Long> {
  ActionPoints findByName (String name);   
 ActionPoints findByNbrePoint(int nmbrePoint);
}
