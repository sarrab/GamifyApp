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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private EventType eventType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private ActionType actionType;
    
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private PointScale pointscale;

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    

    public Rule() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEventyp(EventType eventyp) {
        this.eventType = eventyp;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public Long getId() {
        return id;
    }

    public Rule(EventType eventyp, ActionType actionType, PointScale p) {
        this.eventType = eventyp;
        this.actionType = actionType;
        this.pointscale = p;
    }

    public void setPointscale(PointScale pointscale) {
        this.pointscale = pointscale;
    }

    public PointScale getPointscale() {
        return pointscale;
    }

    public EventType getEventype() {
        return eventType;
    }

    public ActionType getActionType() {
        return actionType;
    }

}
