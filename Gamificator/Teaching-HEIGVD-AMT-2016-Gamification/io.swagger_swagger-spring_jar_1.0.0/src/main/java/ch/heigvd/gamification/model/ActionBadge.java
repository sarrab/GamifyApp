/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.model;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 *
 * @author Thibaut-PC
 */
@Entity
@DiscriminatorValue("ActionBadge")
public class ActionBadge extends ActionType {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Badge badge;

    public ActionBadge() {

    }

    @Transient
    public String getDiscriminatorValue() {
        DiscriminatorValue val = this.getClass().getAnnotation(DiscriminatorValue.class);

        return val == null ? null : val.value();
    }

    public ActionBadge(Badge badge, String name) {

        super(name);
        this.badge = badge;
    }
    

    public Badge getBadge() {
        return badge;
    }

    
    public void setBadge(Badge badge) {
        this.badge = badge;
    }

}
