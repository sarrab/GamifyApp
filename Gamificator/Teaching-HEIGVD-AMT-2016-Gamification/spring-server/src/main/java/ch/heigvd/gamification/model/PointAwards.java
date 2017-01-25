/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.joda.time.LocalDate;

/**
 *
 * @author Thibaut-PC
 */
@Entity
public class PointAwards implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private EndUser enduser;

    @ManyToOne
    private PointScale pointScale;
    
    
    
    
    private long point;
    
    
    
  
   Date dat;

    private String reason;

    public PointAwards() {
    }

    public PointAwards(EndUser enduser, long point, PointScale points) {
        this.enduser = enduser;
        this.point = point;
        this.dat = new Date();
        this.pointScale = points;
    }

    public void setPointScale(PointScale pointScale) {
        this.pointScale = pointScale;
    }

    public PointScale getPointScale() {
        return pointScale;
    }

    public EndUser getEnduser() {
        return enduser;
    }

    public long getPoint() {
        return point;
    }

   public Date getDate() {
        return dat;
    }

    public String getReason() {
        return reason;
    }

    public void setEnduser(EndUser enduser) {
        this.enduser = enduser;
    }

    public void setPoint(long point) {
        this.point = point;
    }

    public void setDate(Date dat) {
        this.dat = dat;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getID() {
        return id;
    }

    public void setID(Long ID) {
        this.id = ID;
    }

}
