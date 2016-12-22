/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Thibaut-PC
 */

@Entity
public class ActionType implements Serializable {

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) private
   	Long id;
   private String name;
    
   public ActionType(){
       
   }

    public ActionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
