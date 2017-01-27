/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.services.dao;

import ch.heigvd.gamification.model.Application;
import ch.heigvd.gamification.model.EventType;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Thibaut-PC
 */
public interface EventTypeRepository extends CrudRepository<EventType, Long> {
    EventType findByEventName (String name);
    EventType findByEventNameAndApp(String eventypeName, Application app);
    
}
