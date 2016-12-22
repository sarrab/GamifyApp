/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.dao;

import ch.heigvd.gamification.model.Application;
import ch.heigvd.gamification.model.Badge;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Thibaut-PC
 */
public interface ApplicationRepository extends CrudRepository<Application, Long> {
   Application findByName (String name);
}
