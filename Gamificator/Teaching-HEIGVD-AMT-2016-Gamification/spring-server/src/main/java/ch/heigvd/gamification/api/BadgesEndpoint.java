/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.api;

import ch.heigvd.gamification.api.dto.BadgeDTO;
import ch.heigvd.gamification.dao.ApplicationRepository;
import ch.heigvd.gamification.dao.BadgeRepository;
import ch.heigvd.gamification.model.Application;
import ch.heigvd.gamification.model.Badge;
import java.util.List;

import io.swagger.annotations.ApiParam;
import java.net.URI;
import static java.util.stream.Collectors.toList;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author user
 */
@RestController
@RequestMapping(value = "/badges")
public class BadgesEndpoint implements BadgesApi {

    BadgeRepository badgeRepository;
    ApplicationRepository apprepository;
     UriComponentsBuilder ucBuilder;
    
   

    
    @Autowired
    public BadgesEndpoint(BadgeRepository badgeRepository, ApplicationRepository apprepository) {
        this.badgeRepository = badgeRepository;
        this.apprepository = apprepository;
 
    }

   
    @Override
    @RequestMapping(value = "/{badgeName}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> badgesBadgeIdDelete(@ApiParam(value = "badgeName", required = true) @PathVariable("badgeName") Long badgeId) {
        Badge badge = badgeRepository.findOne(badgeId);

        if (badge != null) {
            badgeRepository.delete(badge);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @RequestMapping(value = "/{badgeName}", method = RequestMethod.GET)
    public ResponseEntity<BadgeDTO> badgesBadgeIdGet(@ApiParam(value = "badgeName", required = true) @PathVariable("badgeName") Long badgeId) {
        Badge badge = badgeRepository.findOne(badgeId);
                
        BadgeDTO dto = toDTO(badge);
        dto.setId(badge.getId());
        if (dto == null) {
            return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }

    @Override
    @RequestMapping(value = "/{badgeId}", method = RequestMethod.PUT)
     public ResponseEntity<Void> badgesBadgeIdPut(@ApiParam(value = "badgeId", required = true) @RequestHeader(value = "token", required = true) String token, @ApiParam(value = "badgeId", required = true) @PathVariable("badgeId") Long badgeId, @ApiParam(value = "Modification of the badge") @RequestBody BadgeDTO body) {
        
        Badge badge = badgeRepository.findOne(badgeId);
      

        if (!body.getDescription().equals(" ")) {
            badge.setDescription(body.getDescription());
        } else {
            body.setDescription(badge.getDescription());
        }
        if (!body.getImageURI().equals(" ")) {
            badge.setImage(body.getImageURI());
        } else {
            body.setImageURI(badge.getImage());
        }

        if (!body.getName().equals(" ")) {
            badge.setName(body.getName());
        } else {
            body.setName(badge.getName());
        }
           
        badgeRepository.save(badge);
        return new ResponseEntity(HttpStatus.OK);
    }

     
    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<BadgeDTO>>badgesGet() {
        return new ResponseEntity<>(StreamSupport.stream(badgeRepository.findAll().spliterator(), true)
                .map(p -> toDTO(p))
                .collect(toList()), HttpStatus.OK);
    }

    
    @Override
    @RequestMapping(method = RequestMethod.POST)
     public ResponseEntity<Void> badgesPost(@ApiParam(value = "Badge to add", required = true) @RequestBody BadgeDTO body, @ApiParam(value = "Identifient de l'application à laquelle appartient le badge", required = true) @RequestHeader(value = "token", required = true) String token) {
    
         Application app = apprepository.findByName(token);
         
         
        if (body != null && app !=null) {
            Badge badge = new Badge();
            badge.setDescription(body.getDescription());
            badge.setName(body.getName());
            badge.setImage(body.getImageURI());
            badge.setApp(app);
            badgeRepository.save(badge);
            
        HttpHeaders responseHeaders = new HttpHeaders();
         
              
         UriComponents uriComponents = MvcUriComponentsBuilder.fromMethodName(BadgesEndpoint.class, "badgesBadgeIdGet", badge.getId()).build();
            
         URI locationUri = uriComponents.toUri();
          responseHeaders.add("Location", locationUri.toString());           
            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);

        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

   
    public BadgeDTO toDTO(Badge badge) {
      BadgeDTO badgedto = new BadgeDTO();
      
      badgedto.setDescription(badge.getDescription());
      badgedto.setId(badge.getId());
      badgedto.setImageURI(badge.getImage());
      badgedto.setName(badge.getName());
      
     badgedto.setApplication(badge.getApp().getId());
      
      return badgedto;
    }

    

    
    
    
}
