/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Badge implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String image;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    Application app;

    @OneToMany(mappedBy = "badge", cascade = CascadeType.ALL)
    private List<BadgeAward> badgeAwards;

    public Badge() {

    }

    public List<BadgeAward> getBadgeAwards() {
        return badgeAwards;
    }

    public void setApp(Application app) {
        this.app = app;
    }

    public Badge(String image, String name, String description, Application app) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.app = app;
    }

    public Application getApp() {
        return app;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public void setBadgeAwards(List<BadgeAward> badgeAwards) {
        this.badgeAwards = badgeAwards;
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
