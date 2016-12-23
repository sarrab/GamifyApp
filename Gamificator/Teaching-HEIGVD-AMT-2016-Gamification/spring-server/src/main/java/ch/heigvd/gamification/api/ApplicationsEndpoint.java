/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.api;

import ch.heigvd.gamification.model.Application;
import ch.heigvd.gamification.api.dto.ApplicationDTO;
import ch.heigvd.gamification.api.dto.Registration;
import ch.heigvd.gamification.services.dao.ApplicationRepository;
import ch.heigvd.gamification.services.dao.AuthenKeyRepository;
import ch.heigvd.gamification.model.AuthenKey;
import io.swagger.annotations.ApiParam;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

/**
 *
 * @author Thibaut-PC
 */
@RestController
@RequestMapping(value = "/applications")
public class ApplicationsEndpoint implements ApplicationsApi {

    ApplicationRepository apprepository;
    AuthenKeyRepository authenRepository;

   
 @Autowired
    public ApplicationsEndpoint(ApplicationRepository apprepository, AuthenKeyRepository authenRepository) {
        this.apprepository = apprepository;
        this.authenRepository = authenRepository;
    }

    @Override
    @RequestMapping(value = "/{applicationId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> applicationsApplicationIdDelete(@ApiParam(value = "applicationId", required = true) @PathVariable("applicationId") Long applicationId) {
        ch.heigvd.gamification.model.Application app = apprepository.findOne(applicationId);

        if (app != null) {
            apprepository.delete(app);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @RequestMapping(value = "/{applicationId}", method = RequestMethod.GET)
    public ResponseEntity<ApplicationDTO> applicationsApplicationIdGet(@ApiParam(value = "applicationId", required = true) @PathVariable("applicationId") Long applicationId) {
        ch.heigvd.gamification.model.Application app = apprepository.findOne(applicationId);
        UriComponents uriComponents = MvcUriComponentsBuilder.fromMethodName(BadgesEndpoint.class,"badgesGet", 1).build();
        ApplicationDTO dto = toDTO(app, uriComponents);

        if (dto == null) {
            return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }

   
    @Override
    @RequestMapping(value = "/{applicationId}", method = RequestMethod.PUT)
    public ResponseEntity<Void> applicationsApplicationIdPut(@ApiParam(value = "Id de l'application", required = true) @PathVariable("applicationId") Long applicationId, @ApiParam(value = "Modification of the application") @RequestBody Registration body) {
     
        
        
      Application app = apprepository.findOne(applicationId);

        if (!body.getName().equals(" ")) {
            app.setName(body.getName());
        } else {
            body.setName(app.getName());
        }

        if (!body.getPassword().equals(" ")) {
            app.setPassword(body.getPassword());
        } else {
            body.setPassword(app.getPassword());
        }

        apprepository.save(app);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ApplicationDTO>> applicationsGet() {
        UriComponents uriComponents = MvcUriComponentsBuilder.fromMethodName(BadgesEndpoint.class, "badgesGet", 1).build();
        return new ResponseEntity<>(StreamSupport.stream(apprepository.findAll().spliterator(), true)
                .map(p -> toDTO(p, uriComponents))
                .collect(toList()), HttpStatus.OK);
    }

   

    @Override
    @RequestMapping(method = RequestMethod.POST)
   public ResponseEntity<Void> applicationsPost(@ApiParam(value = "Application to add", required = true) @RequestBody Registration body) {
       
        if (body != null) {
        
            AuthenKey apiKey = new AuthenKey();
            String password ="";
            try {
                password = ch.heigvd.gamification.model.Application.doHash(body.getPassword(), body.getUsername());
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
                Logger.getLogger(ApplicationsEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        Application app = new Application(body.getName(), password, body.getUsername());    
         
        apiKey.setApp(app);
        
             apprepository.save(app);
             authenRepository.save(apiKey);
            return new ResponseEntity(HttpStatus.CREATED);

        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    
    public ApplicationDTO toDTO(ch.heigvd.gamification.model.Application app, UriComponents uriComponents) {

        ApplicationDTO dto = new ApplicationDTO();
       List<String> urls = new ArrayList<>();
       app.getBadges().stream().forEach((badge) -> {
           urls.add(uriComponents.toString() + badge.getId());
        });
        dto.setBadges(urls);
        dto.setId(app.getId());
        dto.setName(app.getName());

        return dto;

    }
}
