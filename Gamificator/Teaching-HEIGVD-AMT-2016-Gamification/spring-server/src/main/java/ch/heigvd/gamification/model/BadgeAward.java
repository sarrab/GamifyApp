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
import javax.persistence.Temporal;

/**
 *
 * @author Thibaut-PC
 */
@Entity
public class BadgeAward implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;

    @ManyToOne
    private Badge badge;

    @ManyToOne
    private EndUser endUser;

    public BadgeAward(Date date, Badge badge, EndUser enduser) {
        this.date = date;
        this.badge = badge;
        this.endUser = enduser;
    }

    public Date getDate() {
        return date;
    }

    public Badge getBadge() {
        return badge;
    }

    public EndUser getEnduser() {
        return endUser;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public void setEnduser(EndUser enduser) {
        this.endUser = enduser;
    }

    public BadgeAward() {
    }

    public Long getID() {
        return id;
    }

    public void setID(Long ID) {
        this.id = ID;
    }

}
