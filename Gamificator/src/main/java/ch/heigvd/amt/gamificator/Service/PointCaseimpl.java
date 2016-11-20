/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.gamificator.Service;

import ch.heigvd.amt.gamificator.Dao.PointCaseRepository;
import ch.heigvd.amt.gamificator.model.PointCase;
import java.util.Collection;
import javax.annotation.Resource;
import org.apache.commons.collections.IteratorUtils;

/**
 *
 * @author Thibaut-PC
 */
public class PointCaseimpl implements PointCaseService{
 
    
    @Resource
	private PointCaseRepository pointCaseRepository;
    
    @Override
    public Collection<PointCase> getAllPointCase() {
      return IteratorUtils.toList(this.pointCaseRepository.findAll().iterator());//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PointCase getPlaceById(Long id) {
        return this.pointCaseRepository.findOne(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PointCase createPlace(PointCase pointCase) {
      return this.pointCaseRepository.save(pointCase) ; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PointCase updatePointCase(PointCase pointCase) {
       return this.pointCaseRepository.save(pointCase); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletePointcase(Long id) {
       this.pointCaseRepository.delete(id); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PointCase getPlaceByName(String Name) {
        return this.pointCaseRepository.findByname(Name); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    public PointCaseRepository getPlaceRepository() {
 		return pointCaseRepository;
 	}

	public void setPlaceRepository(PointCaseRepository pointCaseRepository) {
 		this.pointCaseRepository = pointCaseRepository;	
	}
        
        
         
}
