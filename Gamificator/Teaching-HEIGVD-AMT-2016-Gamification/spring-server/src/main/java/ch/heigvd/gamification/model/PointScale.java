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
public class PointScale implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) private
     Long id;
    private String name;
    private String description;
    private int minpoint;
   
    public PointScale (){
        
    }

    public PointScale(String name, String description, int point) {
        this.name = name;
        this.description = description;
        this.minpoint = point;
    }

    public Long getId() {
        return id;
    }

    public void setMinpoint(int minpoint) {
        this.minpoint = minpoint;
    }

    public int getMinpoint() {
        return minpoint;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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

 
    
    
}
