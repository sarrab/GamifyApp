/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 *
 * @author Thibaut-PC
 *
 */
@Entity
@DiscriminatorValue("ActionPoints")
public class ActionPoints extends ActionType {

    private int nbrePoint;

    public ActionPoints() {
    }

    public ActionPoints(int nombrePoint, String name) {
        this.nbrePoint = nombrePoint;
    }

    public int getNombrePoint() {
        return nbrePoint;
    }

    public void setNombrePoint(int nombrePoint) {
        this.nbrePoint = nombrePoint;
    }

    public int getNbrePoint() {
        return nbrePoint;
    }

    
@Transient
public String getDiscriminatorValue(){
    DiscriminatorValue val = this.getClass().getAnnotation( DiscriminatorValue.class );

    return val == null ? null : val.value();
}

    public void setNbrePoint(int nbrePoint) {
        this.nbrePoint = nbrePoint;
    }

}
