/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Thibaut-PC
 */

@Entity
public class Event implements Serializable{
  @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
     @ManyToOne
    private EndUser endUser;
     
      @ManyToOne
    private EventType eventType;
      
       @ManyToOne
    private Application app;

    public void setApp(Application app) {
        this.app = app;
    }

    public Application getApp() {
        return app;
    }
      
    private Date date;

    public Event(Long id, EndUser endUser, EventType eventType) {
        this.id = id;
        this.endUser = endUser;
        this.eventType = eventType;
        this.date = new Date();
    }

    public Event() {
        
    }

    public Long getId() {
        return id;
    }

    public EndUser getEndUser() {
        return endUser;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Date getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEndUser(EndUser endUser) {
        this.endUser = endUser;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
    
    
    
    
    
    
   
}
