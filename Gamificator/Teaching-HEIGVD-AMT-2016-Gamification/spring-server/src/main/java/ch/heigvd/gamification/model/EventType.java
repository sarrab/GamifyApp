/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Thibaut-PC
 */

@Entity
public class EventType implements Serializable {
    @Id
   @GeneratedValue(strategy = GenerationType.AUTO) 
  private Long id;
    private String eventName;
    
    
     @ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    private Application app;
    
    public EventType(){
        
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public Application getApp() {
        return app;
    }

    public EventType(String eventName, Application app) {
        this.eventName = eventName;
        this.app = app;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setApp(Application app) {
        this.app = app;
    }
       
    
}
