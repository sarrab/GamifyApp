/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.gamificator.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Thibaut-PC
 */
@Entity
public class Badge {
     @Id
   	@GeneratedValue(strategy = GenerationType.AUTO) private
   	Long id;
 
      public String image;
     public String name;
     public String description;

    public Badge(String image, String name, String description) {
        this.image = image;
        this.name = name;
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
 



}
