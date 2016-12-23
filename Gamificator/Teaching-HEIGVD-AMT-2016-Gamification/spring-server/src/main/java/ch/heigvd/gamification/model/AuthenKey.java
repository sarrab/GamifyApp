/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.model;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Thibaut-PC
 */

@Entity
public class AuthenKey implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
     
     @OneToOne
    private Application app;
     
      private String appKey;

    public void setId(Long id) {
        this.id = id;
    }

    public void setApp(Application app) {
        this.app = app;
    }

    public Long getId() {
        return id;
    }

    public Application getApp() {
        return app;
    }
    
   

    public AuthenKey() {
        
        
        UUID key = UUID.randomUUID();
        appKey = String.valueOf(key);
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
    
    
    
}
