/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.gamificator.Service;

import ch.heigvd.amt.gamificator.Dao.BadgeRepository;
import ch.heigvd.amt.gamificator.model.Badge;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.stereotype.Service;
import java.util.Collection;
import javax.annotation.Resource;



/**
 *
 * @author Thibaut-PC
 */
public class BadgesServiceimpl implements BadgeService{
   
   @Resource 
    private BadgeRepository badgeRepository;
    

    @Override
    public Collection<Badge> getAllBadges() {
      return IteratorUtils.toList(this.badgeRepository.findAll().iterator()); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Badge getPlaceById(Long id) {
      return this.badgeRepository.findOne(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Badge createPlace(Badge badge) {
       return this.badgeRepository.save(badge); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Badge updateBadge(Badge badge) {
       return this.badgeRepository.save(badge); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteBadge(Long id) {
     this.badgeRepository.delete(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Badge getPlaceByName(String name) {
        return this.badgeRepository.findByname(name); //To change body of generated methods, choose Tools | Templates.
    }   
        public BadgeRepository getBadgeRepository() {
 		return badgeRepository;
 	}

	public void setBadgeRepository(BadgeRepository placeRepository) {
 		this.badgeRepository = placeRepository;	
	}

        
    }
    

