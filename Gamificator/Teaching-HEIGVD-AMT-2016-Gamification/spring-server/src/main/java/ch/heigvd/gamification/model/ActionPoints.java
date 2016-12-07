/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.model;

import javax.persistence.Entity;

/**
 *
 * @author Thibaut-PC
 * 
 */

@Entity
public class ActionPoints  extends ActionType{
    private int nombrePoint;

    
    public ActionPoints(){}
    public ActionPoints(int nombrePoint, String name) {
        this.nombrePoint = nombrePoint;
    }

    public int getNombrePoint() {
        return nombrePoint;
    }

    public void setNombrePoint(int nombrePoint) {
        this.nombrePoint = nombrePoint;
    }
    
    
    
}
