/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.services;

import ch.heigvd.gamification.api.dto.EventDTO;
import ch.heigvd.gamification.services.dao.ActionPointRepository;
import ch.heigvd.gamification.services.dao.ApplicationRepository;
import ch.heigvd.gamification.services.dao.BadgeAwardRepository;
import ch.heigvd.gamification.services.dao.BadgeRepository;
import ch.heigvd.gamification.services.dao.EndUserRepository;
import ch.heigvd.gamification.services.dao.EventRepository;
import ch.heigvd.gamification.services.dao.EventTypeRepository;
import ch.heigvd.gamification.services.dao.PointAwardsRepository;
import ch.heigvd.gamification.services.dao.RulePropertiesRepository;
import ch.heigvd.gamification.services.dao.RuleRepository;
import ch.heigvd.gamification.model.ActionBadge;
import ch.heigvd.gamification.model.ActionPoints;
import ch.heigvd.gamification.model.Application;
import ch.heigvd.gamification.model.AuthenKey;
import ch.heigvd.gamification.model.BadgeAward;
import ch.heigvd.gamification.model.EndUser;
import ch.heigvd.gamification.model.Event;
import ch.heigvd.gamification.model.EventType;
import ch.heigvd.gamification.model.PointAwards;
import ch.heigvd.gamification.model.Rule;
import ch.heigvd.gamification.services.dao.AuthenKeyRepository;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author Thibaut-PC
 */

@Service
public class EventProcessor {
 private final  EventTypeRepository eventTypeRepository;

private  final  EndUserRepository endUserRepository;

private   final ApplicationRepository ApplicationRepository;

 private  final  RuleRepository ruleRepository;

  private  final RulePropertiesRepository rulesPropertiesRepository;

 private   final PointAwardsRepository pointAwardsrepository;

 private   final BadgeAwardRepository badgeAwardRepository;

 private   final BadgeRepository badgeRepository;
 
 private  final EventRepository eventRepository;
 private final  AuthenKeyRepository authenKeyRepository;

    
    
 
 final String ACTION_TYPE_POINT_FINAL = "ActionPoints";
    final String ACTION_TYPE_BADGE_FINAL = "AwardBadge";
    
    ActionPointRepository actionPointRepository;

    public EventProcessor(EventTypeRepository eventTypeRepository, EndUserRepository endUserRepository, ApplicationRepository ApplicationRepository, RuleRepository ruleRepository, RulePropertiesRepository rulesPropertiesRepository, PointAwardsRepository pointAwardsrepository, BadgeAwardRepository badgeAwardRepository, BadgeRepository badgeRepository, EventRepository eventRepository, AuthenKeyRepository authenKeyRepository, ActionPointRepository actionPointRepository) {
        this.eventTypeRepository = eventTypeRepository;
        this.endUserRepository = endUserRepository;
        this.ApplicationRepository = ApplicationRepository;
        this.ruleRepository = ruleRepository;
        this.rulesPropertiesRepository = rulesPropertiesRepository;
        this.pointAwardsrepository = pointAwardsrepository;
        this.badgeAwardRepository = badgeAwardRepository;
        this.badgeRepository = badgeRepository;
        this.eventRepository = eventRepository;
        this.authenKeyRepository = authenKeyRepository;
        this.actionPointRepository = actionPointRepository;
    }
 
    
    

    @Async
    @Transactional
    public void processEvent(String xGamificationToken, EventDTO eventDTO) {
        
     Long idendUser = eventDTO.getUserId();
        DateTime timestamp = eventDTO.getTimeStamp();
        String type = eventDTO.getType();
        EndUser enduser;
       
        
       
        AuthenKey apiKey = authenKeyRepository.findByAppKey(xGamificationToken);
          
            if(apiKey == null){
                System.err.println("apikey not exist");
        }
        Application app = apiKey.getApp();
            
       
        enduser = endUserRepository.findByIdappAndApp(eventDTO.getUserId(), app);

        if (enduser == null) {
            System.err.println("user doesn't exist");
            enduser = endUserRepository.save(new EndUser(eventDTO.getUserId(), eventDTO.getType(), timestamp.toDate(), app));
            app.add(enduser);

        }
          
      
       Event event = new Event();
        EventType eventype = null;

        String eventypeName = eventDTO.getType();

        eventype = eventTypeRepository.findByEventNameAndApp(eventypeName, app);
        
        
        
         if(eventype == null){
           
             System.err.println("le type d'événement n'existe pas");
         }
         
        List<Rule> rules = eventype.getRules();
        if (rules.size() > 0) {
             
            for (Rule r : rules) {

                 if(r.getActionType().getName().equalsIgnoreCase(ACTION_TYPE_POINT_FINAL)) {
                   
                         //ActionPoints actionPoint = actionPointRepository.findByName(r.getActionType().getName());
                      
                       ActionPoints ap = (ActionPoints) r.getActionType();
                       pointAwardsrepository.save(new PointAwards(enduser, ap.getNombrePoint(), r.getPointscale()));
                 }
                 
                  if(r.getActionType().getName().equalsIgnoreCase(ACTION_TYPE_BADGE_FINAL)){
                  
                        ActionBadge ab = (ActionBadge) r.getActionType();
                        badgeAwardRepository.save(new BadgeAward(timestamp.toDate(), ab.getBadge(), enduser));
                }
                  
          
            }
           
        }
        

        event.setEndUser(enduser);
        event.setEventType(eventype);
        event.setDate(new Date());
        event.setApp(app);
        eventRepository.save(event);
       // enduser.addEvent(event);
       
      
        
    }

}
