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
public class Rule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public void setEventyp(EventType eventyp) {
        this.eventyp = eventyp;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public Long getId() {
        return id;
    }
    
     @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private EventType eventyp;
     
      @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private ActionType actionType;

    public Rule(EventType eventyp, ActionType actionType) {
        this.eventyp = eventyp;
        this.actionType = actionType;
    }
 
    public EventType getEventyp() {
        return eventyp;
    }

    public ActionType getActionType() {
        return actionType;
    }
       
    
    
}
