/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.gamificator.Res;

import ch.heigvd.amt.gamificator.Service.PointCaseService;
import ch.heigvd.amt.gamificator.model.PointCase;
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
@RestController @RequestMapping(value ="/PointCase") 
public class PointCaseResource {
    
    @Resource
	private PointCaseService pointCaseService;
  
    
    @RequestMapping(method = RequestMethod.POST)
	public PointCase createPlace(@RequestBody PointCase pointCase) {
 		return this.pointCaseService.createPlace(pointCase);	
	}
 
      @RequestMapping(method = RequestMethod.GET)
	public Collection<PointCase> getAllPointCase() {
 	  	return this.pointCaseService.getAllPointCase();	
	}
 
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public PointCase getPlacename(@PathVariable(value = "name") String name) {
 		//find place by shortname
   		return this.pointCaseService.getPlaceByName(name);
	}
 	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE) 
	public void deletePointCase(@PathVariable(value = "id") Long id) {
		this.pointCaseService.deletePointcase(id);
	}
 
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public PointCase updatePointCase(@PathVariable(value = "id") Long id, @RequestBody PointCase pointCase) {
		pointCase.setId(id);
 
 		return this.pointCaseService.updatePointCase(pointCase);
 	}
 
	public PointCaseService getPointCaseService() { 
		return pointCaseService;
	}
 
	public void setPointCaseService(PointCaseService pointCase) {
   		this.pointCaseService = pointCase;
   	}   
        
    
}
