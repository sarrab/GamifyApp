/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.model;

import ch.heigvd.gamification.model.ActionType;
import ch.heigvd.gamification.model.Badge;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 *
 * @author Thibaut-PC
 */

@Entity
public class ActionBadge extends ActionType {
    
   
     @ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    private Badge badge;
    
    public ActionBadge(){
        
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
