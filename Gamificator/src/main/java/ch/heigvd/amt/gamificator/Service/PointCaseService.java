/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.gamificator.Service;

import ch.heigvd.amt.gamificator.model.PointCase;
import java.util.Collection;

/**
 *
 * @author Thibaut-PC
 */
public interface PointCaseService {
    
    
    Collection<PointCase> getAllPointCase();
	
	PointCase getPlaceById(Long id);
	
	PointCase createPlace(PointCase pointCase);
	
	PointCase updatePointCase(PointCase pointCase); void deletePointcase(Long id);
	
	PointCase getPlaceByName(String Name);
}
