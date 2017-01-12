/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Thibaut-PC
 */
@Entity
public class Application implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "app")
    private List<Badge> badges;
    
     @OneToOne(mappedBy = "app", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
      
    private AuthenKey appKey;
    
     @OneToMany(mappedBy = "app")
    private List<Event> event;

    @OneToMany(mappedBy = "app")
    private List<EndUser> endusers;

    @OneToMany(mappedBy = "app")
    private List<EventType> eventypes;

    @Column(unique = true)
    private String name;
    private String password;
     @Column(unique = true)
     private String username;

    public Application(String name, String password, String username) {
       
        this.name = name;
        this.password = password;
        this.username = username;
    }


    

    public Application() {

    }

    public AuthenKey getAppKey() {
        return appKey;
    }

    public String getUsername() {
        return username;
    }

    public void setAppKey(AuthenKey appKey) {
        this.appKey = appKey;
    }

    public void setUsername(String username) {
        this.username = username;
    }

  
   public List<Event> getEvent() {
        return event;
    }

    public void setEvent(List<Event> event) {
        this.event = event;
    }
    
    public void setPassword(String password) {
      /*try {
            this.password = doHash(password,getUsername());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }*/
      
      this.password = password;
    }

    
    public String getPassword() {
        return password;
    }
    

    public List<EventType> getEventypes() {
        return eventypes;
    }

    public void setEventypes(List<EventType> eventypes) {
        this.eventypes = eventypes;
    }

    public List<Badge> getBadges() {
        return badges;
    }

    public void setEndusers(List<EndUser> endusers) {
        this.endusers = endusers;
    }

    public List<EndUser> getEndusers() {
        return endusers;
    }

    public void setBadges(List<Badge> badges) {
        this.badges = badges;
    }
 

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add(EndUser endUser){
     this.endusers.add(endUser);
    
    }
    public static void setw(){
    }
    
  public static String doHash(String password, String salt) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        digest.update(salt.getBytes());

        byte[] byteData = digest.digest(password.getBytes("UTF-8"));

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }  
    
  
  

}
