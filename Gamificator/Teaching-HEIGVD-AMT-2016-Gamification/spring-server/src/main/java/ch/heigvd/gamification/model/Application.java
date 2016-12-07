/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Thibaut-PC
 */

@Entity
public class Application implements Serializable {
     @Id
    @GeneratedValue(strategy = GenerationType.AUTO) private
     Long id;
     
   @Column(unique=true)
    private String name;
    private String description;
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    //@ElementCollection(targetClass=String.class)
    //private List<String> badges = new ArrayList<>();
    
    @OneToMany(mappedBy = "app")
    private List<Badge> badges;

    public List<Badge> getBadges() {
        return badges;
    }

    public void setBadges(List<Badge> badges) {
        this.badges = badges;
    }
    
     @ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    private Account creator;
     
     
    private Boolean enable;
    
      public Application(){
          
      }

    public Application(String name, String description, Account creator, Boolean enable) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.enable = enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getEnable() {
        return enable;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Account getCreator() {
        return creator;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }
              
    
}
