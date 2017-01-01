/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Thibaut-PC
 */
@Entity

@NamedQueries({
   
@NamedQuery(name = "EndUser.getBestUsers", query = "SELECT e.id, e.name, SUM(p.point) FROM EndUser e, PointAwards p WHERE e.app = :app AND p.enduser = e GROUP BY p.enduser.id"),
})
public class EndUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Long idapp;
    
@OneToMany(mappedBy = "enduser", targetEntity=PointAwards.class, cascade = CascadeType.PERSIST)
    private List<PointAwards> pointAwards;

    
      @OneToMany(mappedBy = "endUser")
    private List<Event> events  = new ArrayList<>();
      
      
      
    @OneToMany(mappedBy = "endUser", targetEntity=BadgeAward.class, cascade = CascadeType.PERSIST)
    private List<BadgeAward> badgeAwards;
    
    
    public void setId(Long id) {
        this.id = id;
    }

    public EndUser(Long idapp, String name, Date date, Application app) {
        this.idapp = idapp;
        this.name = name;
        this.date = date;
        this.app = app;
    }

    public void setIdapp(Long idapp) {
        this.idapp = idapp;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Long getId() {
        return id;
    }

    public Long getIdapp() {
        return idapp;
    }

    public List<Event> getEvents() {
        return events;
    }

   

    public List<BadgeAward> getBadgeAwards() {
        return badgeAwards;
    }

    public void setBadgeAwards(List<BadgeAward> badgeAwards) {
        this.badgeAwards = badgeAwards;
    }

    private String name;
    private Date date;

    @ManyToOne
    private Application app;

    public void setPointAwards(List<PointAwards> pointAwards) {
        this.pointAwards = pointAwards;
    }

    public List<PointAwards> getPointAwards() {
        return pointAwards;
    }

    public EndUser() {
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Application getApp() {
        return app;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setApp(Application app) {
        this.app = app;
    }

    public Long getID() {
        return id;
    }

    public void setID(Long ID) {
        this.id = ID;
    }
    
    public void addEvent(Event event){
    this.events.add(event);
    }

}
