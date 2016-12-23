/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.api;

import ch.heigvd.gamification.api.dto.BadgeDTO;
import ch.heigvd.gamification.services.dao.ApplicationRepository;
import ch.heigvd.gamification.services.dao.AuthenKeyRepository;
import ch.heigvd.gamification.services.dao.BadgeRepository;
import ch.heigvd.gamification.model.Application;
import ch.heigvd.gamification.model.AuthenKey;
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
    AuthenKeyRepository authenKeyRepository;

     @Autowired
    public BadgesEndpoint(BadgeRepository badgeRepository, ApplicationRepository apprepository, AuthenKeyRepository authenKeyRepository) {
        this.badgeRepository = badgeRepository;
        this.apprepository = apprepository;
        this.authenKeyRepository = authenKeyRepository;
    }

    @Override
    @RequestMapping(value = "/{badgeId}", method = RequestMethod.DELETE)
     public ResponseEntity<Void> badgesBadgeIdDelete(@ApiParam(value = "badgeId", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken, @ApiParam(value = "badgeId", required = true) @PathVariable("badgeId") Long badgeId) {
       
         AuthenKey apiKey = authenKeyRepository.findByAppKey(xGamificationToken);
        if(apiKey == null){
        return new ResponseEntity("apikey not exist", HttpStatus.BAD_REQUEST);
        }
        Badge badge = badgeRepository.findOne(badgeId);
        Application app = apiKey.getApp();
        
        
        
        if (badge != null && app != null  ) {
            badgeRepository.delete(badge);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity("no content is valid", HttpStatus.BAD_REQUEST);
        }
    }

    
    @Override
    @RequestMapping(value = "/{badgeId}", method = RequestMethod.GET)
    public ResponseEntity<BadgeDTO> badgesBadgeIdGet(@ApiParam(value = "token that identifies the app sending the request", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken, @ApiParam(value = "Badge's id", required = true) @PathVariable("badgeId") Long badgeId) {
       
        AuthenKey apiKey = authenKeyRepository.findByAppKey(xGamificationToken);
           if(apiKey == null){
        return new ResponseEntity("apikey not exist", HttpStatus.BAD_REQUEST);
        }
         
        Badge badge = badgeRepository.findOne(badgeId);
        Application app = apiKey.getApp();
        if(app != null && badge != null){
        BadgeDTO dto = toDTO(badge);
        dto.setId(badge.getId());
        if (dto == null) {
            return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        }
        return new ResponseEntity("no content is available", HttpStatus.BAD_REQUEST);
    }


    
   

    @Override
    @RequestMapping(value = "/{badgeId}", method = RequestMethod.PUT)
    public ResponseEntity<Void> badgesBadgeIdPut(@ApiParam(value = "token that identifies the app sending the request", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken, @ApiParam(value = "Badge's id", required = true) @PathVariable("badgeId") Long badgeId, @ApiParam(value = "Modification of the badge") @RequestBody BadgeDTO body) {
   
       AuthenKey apiKey = authenKeyRepository.findByAppKey(xGamificationToken); 
       if(apiKey == null){
        return new ResponseEntity("apikey not exist", HttpStatus.BAD_REQUEST);
        }
        Application app = apiKey.getApp();
        
        
        if(app != null){
        
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
        
        else{
        
        return new ResponseEntity("no content is available", HttpStatus.BAD_REQUEST);
        
        }
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<BadgeDTO>> badgesGet(@ApiParam(value = "token that identifies the app sending the request", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken) {
 
          AuthenKey apiKey = authenKeyRepository.findByAppKey(xGamificationToken);
        
             
       //Application app = apprepository.findByAppKey(apiKey);
       if(apiKey == null){
       return new ResponseEntity(HttpStatus.UNAUTHORIZED);
       }
         
         Application app = apiKey.getApp();
         
         if(app != null){
        return new ResponseEntity<>(StreamSupport.stream(badgeRepository.findAll().spliterator(), true)
                .map(p -> toDTO(p))
                .collect(toList()), HttpStatus.OK);
         }
       return new ResponseEntity("no content available", HttpStatus.BAD_REQUEST);
        
    }

   


    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> badgesPost(@ApiParam(value = "Badge to add", required = true) @RequestBody BadgeDTO body, @ApiParam(value = "token that identifies the app sending the request", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken) {
        AuthenKey apiKey = authenKeyRepository.findByAppKey(xGamificationToken);
        
             
       //Application app = apprepository.findByAppKey(apiKey);
       if(apiKey == null){
       return new ResponseEntity(HttpStatus.UNAUTHORIZED);
       }
       Application app = apiKey.getApp();

        if (body != null && app != null) {
            Badge badge = new Badge();
            badge.setDescription(body.getDescription());
            badge.setName(body.getName());
            badge.setImage(body.getImageURI());
            badge.setApp(app);
            badgeRepository.save(badge);

            HttpHeaders responseHeaders = new HttpHeaders();

            UriComponents uriComponents = MvcUriComponentsBuilder.fromMethodName(BadgesEndpoint.class, "badgesBadgeIdGet", 1, badge.getId()).build();

            URI locationUri = uriComponents.toUri();
            responseHeaders.add("Location", uriComponents.toString());
            return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);

        } else {
            return new ResponseEntity("no content is available" , HttpStatus.BAD_REQUEST);
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
