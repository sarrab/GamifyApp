/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.gamificator.Service;

import ch.heigvd.amt.gamificator.model.Badge;
import java.util.Collection;

/**
 *
 * @author Thibaut-PC
 */
public interface BadgeService {
    
    
         Collection<Badge> getAllBadges();
	
	Badge getPlaceById(Long id);
	
	Badge createPlace(Badge badge);
	
	Badge updateBadge(Badge place); void deleteBadge(Long id);
	
	Badge getPlaceByName(String name);
    
    
}
