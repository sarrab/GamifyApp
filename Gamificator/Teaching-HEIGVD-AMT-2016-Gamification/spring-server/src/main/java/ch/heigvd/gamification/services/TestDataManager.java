/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.services;


import ch.heigvd.gamification.model.Application;
import ch.heigvd.gamification.model.AuthenKey;
import ch.heigvd.gamification.model.Badge;
import ch.heigvd.gamification.model.BadgeAward;
import ch.heigvd.gamification.model.EndUser;
import ch.heigvd.gamification.model.PointAwards;
import ch.heigvd.gamification.model.PointScale;
import ch.heigvd.gamification.services.dao.ActionBadgeRepository;
import ch.heigvd.gamification.services.dao.ActionPointRepository;
import ch.heigvd.gamification.services.dao.ActionTypeRepository;
import ch.heigvd.gamification.services.dao.ApplicationRepository;
import ch.heigvd.gamification.services.dao.AuthenKeyRepository;
import ch.heigvd.gamification.services.dao.BadgeAwardRepository;
import ch.heigvd.gamification.services.dao.BadgeRepository;
import ch.heigvd.gamification.services.dao.EndUserRepository;
import ch.heigvd.gamification.services.dao.EventTypeRepository;
import ch.heigvd.gamification.services.dao.PointAwardsRepository;
import ch.heigvd.gamification.services.dao.PointScaleRepository;
import ch.heigvd.gamification.services.dao.RuleRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Thibaut-PC
 */
public class TestDataManager implements TestDataManagerLocal{

 ActionBadgeRepository actionBadgeRepository;
 ActionPointRepository actionPointRepository;
 ActionTypeRepository actionTypeRepository;
 ApplicationRepository applicationRepository;
 AuthenKeyRepository authenKeyRepository;
 EndUserRepository endUserRepository;
BadgeRepository badgeRepository;
 PointAwardsRepository pointAwardRepository;
 RuleRepository ruleRepository;
 EventTypeRepository eventTypeRepository;
 BadgeAwardRepository badgeAwardsRepository;
 PointScaleRepository pointScaleRepository;

    @Autowired 
    public TestDataManager(ActionBadgeRepository actionBadgeRepository, ActionPointRepository actionPointRepository, ActionTypeRepository actionTypeRepository, ApplicationRepository applicationRepository, AuthenKeyRepository authenKeyRepository, EndUserRepository endUserRepository, BadgeRepository badgeRepository, PointAwardsRepository pointAwardRepository, RuleRepository ruleRepository, EventTypeRepository eventTypeRepository, BadgeAwardRepository badgeAwardsRepository, PointScaleRepository pointScaleRepository) {
        this.actionBadgeRepository = actionBadgeRepository;
        this.actionPointRepository = actionPointRepository;
        this.actionTypeRepository = actionTypeRepository;
        this.applicationRepository = applicationRepository;
        this.authenKeyRepository = authenKeyRepository;
        this.endUserRepository = endUserRepository;
        this.badgeRepository = badgeRepository;
        this.pointAwardRepository = pointAwardRepository;
        this.ruleRepository = ruleRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.badgeAwardsRepository = badgeAwardsRepository;
        this.pointScaleRepository = pointScaleRepository;
    }
  
 

   
 
  
   
    
    
    @Override
    public void generateTestData() {
        
        
        /**
         * Generate Application
         */
        
        System.out.println("Gnerate application");  
        
        Application app1 = applicationRepository.save(new Application("app1", "app1_t"));
        AuthenKey apiKey1 = new AuthenKey();
        app1.setAppKey(apiKey1);
        Application app2 = applicationRepository.save(new Application("app2", "app2_t"));
        
        AuthenKey apiKey2 = new AuthenKey();
        app2.setAppKey(apiKey2);
         
      Application app3 = applicationRepository.save(new Application("app3", "app3_t"));
         
      
      AuthenKey apiKey3 = new AuthenKey();
        app3.setAppKey(apiKey2);
      
      Application app4 = applicationRepository.save(new Application("app4", "app4_t"));
          
     AuthenKey apiKey4 = new AuthenKey();
        app1.setAppKey(apiKey4);
        
        
        /**
         * Generate user
         */
          System.out.println("Generate endUsers");
     /*   for (int i = 0; i < 25; ++i) {
            endUserRepository.save(new EndUser( "endUser1" + i, new Date(System.currentTimeMillis()), app1));
        }
    
        
        
         for (int i = 0; i < 30; ++i) {
            endUserRepository.save(new EndUser( "endUser2" + i, new Date(System.currentTimeMillis()), app2));
        }
    
          for (int i = 0; i < 20; ++i) {
            endUserRepository.save(new EndUser( "endUser3" + i, new Date(System.currentTimeMillis()), app3));
            
        }
     for (int i = 0; i < 15; ++i) {
            endUserRepository.save(new EndUser( "endUser4" + i, new Date(System.currentTimeMillis()), app4));
        }*/
    
    /**
     * generation des badges pour app1
     */
    
      System.out.println("Generate badges for app1");
        Badge badge1App1 = badgeRepository.save(new Badge("badge1.png", "Cup", "meilleur", app1));
        Badge badge2App1 = badgeRepository.save(new Badge("badge2.png", "Dar plane", "meilleur", app2));
    
        
        
    /**
     * generation des badges pour app2
     */
    
      System.out.println("Generate badges for app1");
        Badge badge3App2 = badgeRepository.save(new Badge("badge3.png", "United States Coast Guard", "meilleur", app2));
        Badge badge4App2 = badgeRepository.save(new Badge("badge4.png", "Good idea", "meilleur", app2));    
         Badge badge5App2 = badgeRepository.save(new Badge("badge5.png", "Marine", "meilleur", app2));    
        
        
     /**
     *  Set badge for app2
     */
    
     EndUser enduser1 =endUserRepository.findOne((long) 1);
     BadgeAward badgeAward = new BadgeAward( new Date(System.currentTimeMillis()),badge1App1, enduser1);
      badgeAwardsRepository.save(badgeAward);
        
      /**
     *  Set badge for User 15
     */
    
      
     EndUser enduser2 =endUserRepository.findOne((long) 15);
     BadgeAward badgeAward1 = new BadgeAward( new Date(System.currentTimeMillis()),badge3App2, enduser2);
      badgeAwardsRepository.save(badgeAward1);
               
        
      BadgeAward badgeAward2 = new BadgeAward( new Date(System.currentTimeMillis()),badge4App2, enduser2);
      badgeAwardsRepository.save(badgeAward2);
      
       BadgeAward badgeAward4 = new BadgeAward( new Date(System.currentTimeMillis()),badge5App2, enduser2);
      badgeAwardsRepository.save(badgeAward4);
                
        
    /**
     * Generate Poinscale
     */
    
     /*for (int i = 0; i < 25; ++i) {
            endUserRepository.save(new EndUser( "endUser1" + i, new Date(System.currentTimeMillis()), app1));
        }*/
     
     
     /**
      * poinscale
      */
     
     List<PointScale> pointScalel = new ArrayList<>();
       PointScale pointScale = new PointScale("activity", "meilleur", 12);
       pointScaleRepository.save(pointScale);
       pointScalel.add(pointScale);
       PointScale pointScale1 = new PointScale("activity1", "meilleur", 14);
        pointScaleRepository.save(pointScale1);
           pointScalel.add(pointScale1);
       PointScale pointScale2 = new PointScale("activity2", "meilleur", 13);
        pointScalel.add(pointScale2);
        pointScaleRepository.save(pointScale2);
       PointScale pointScale3 = new PointScale("activity3", "meilleur", 14);
        
      pointScalel.add(pointScale3);
       pointScaleRepository.save(pointScale3);
       PointScale pointScale4 = new PointScale("activity4", "meilleur", 15);
          pointScalel.add(pointScale4);
        pointScaleRepository.save(pointScale4);
       PointScale pointScale5 = new PointScale("activity5", "meilleur", 16);
          pointScalel.add(pointScale5);
        pointScaleRepository.save(pointScale5);
       PointScale pointScale6 = new PointScale("activity6", "meilleur", 17);
          pointScalel.add(pointScale6);
        pointScaleRepository.save(pointScale6);
       PointScale pointScale7 = new PointScale("activity7", "meilleur", 18);
          pointScalel.add(pointScale7);
        pointScaleRepository.save(pointScale7);
       
               
       
       
     
     
      for (int i = 0; i < 7; i++) {
            pointAwardRepository.save(new PointAwards(enduser1, i+10, pointScalel.get(i)));
        }
        
    }
    
    
    
    
    
}
