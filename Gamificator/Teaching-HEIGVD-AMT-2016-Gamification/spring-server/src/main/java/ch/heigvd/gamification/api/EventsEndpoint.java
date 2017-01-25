/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.api;

import ch.heigvd.gamification.api.dto.EventDTO;
import ch.heigvd.gamification.services.EventProcessor;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Thibaut-PC
 */
@RestController
@RequestMapping(value = "/events")
public class EventsEndpoint implements EventsApi {
    
    
    
    EventProcessor eventProcessor;
    
    @Autowired

    public EventsEndpoint(EventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
    }

    
    /**
     *
     * @param xGamificationToken
     * @param event
     * @return
     */
    
    
    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity reportEvent(@ApiParam(value = "token that identifies the app sending the request", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken, @ApiParam(value = "The event that occured in the realm of the gamified application", required = true) @RequestBody EventDTO event) {
 
        try {
            eventProcessor.processEvent(xGamificationToken, event);
        }
        catch (DataIntegrityViolationException e) {
            // We relaunch the request when it fails
            eventProcessor.processEvent(xGamificationToken, event);
        }

        return ResponseEntity.ok().build();
    }    
        
    }
        
        
        
        
        
        
        
        

