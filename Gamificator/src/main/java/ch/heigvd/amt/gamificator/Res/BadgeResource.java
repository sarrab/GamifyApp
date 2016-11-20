/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.gamificator.Res;

import ch.heigvd.amt.gamificator.Service.BadgeService;
import ch.heigvd.amt.gamificator.model.Badge;
import java.util.Collection;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Thibaut-PC
 */

@RestController @RequestMapping(value ="/badges") 
public class BadgeResource {
    
    @Resource
	private BadgeService  badgeService;
    
    @RequestMapping(method = RequestMethod.POST)
	public Badge createBadge(@RequestBody  Badge badge) {
 		return this.badgeService.createPlace(badge);	
	}
    
    @RequestMapping(method = RequestMethod.GET)
	public Collection<Badge> getAllBadge() {
 	  	return this.badgeService.getAllBadges();	
	}
   @RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public Badge getBadgeForname(@PathVariable(value = "name") String name) {
 		//find place by name
   		return this.badgeService.getPlaceByName(name);
	}     
        
        
        @RequestMapping(value = "/{id}", method = RequestMethod.DELETE) 
	public void deleteBadge(@PathVariable(value = "id") Long id) {
		this.badgeService.deleteBadge(id);
	}
        
     @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Badge updatePlace(@PathVariable(value = "id") Long id, @RequestBody Badge badge) {
		badge.setId(id);
 
 		return this.badgeService.updateBadge(badge);
 	}
    
        
    public BadgeService getPlaceService() { 
		return badgeService;
	}
 
	public void setPlaceService(BadgeService badgeService) {
   		this.badgeService = badgeService;
   	}     
        
}
