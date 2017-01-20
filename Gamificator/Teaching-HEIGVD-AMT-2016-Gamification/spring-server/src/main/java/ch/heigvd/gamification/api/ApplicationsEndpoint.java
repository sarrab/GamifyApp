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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @RequestMapping(value = "/{applicationName}", method = RequestMethod.DELETE)
    public ResponseEntity<Void>  applicationsApplicationNameDelete(@ApiParam(value = "applicationName", required = true) @PathVariable("applicationName") String applicationUsername) {

        Application app = apprepository.findByName(applicationUsername);

        if (app != null) {
            apprepository.delete(app);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @RequestMapping(value = "/{applicationName}", method = RequestMethod.GET)
    public ResponseEntity<ApplicationDTO> applicationsApplicationNameGet(@ApiParam(value = "applicationName", required = true) @PathVariable("applicationName") String applicationUsername) {

        Application app = apprepository.findByName(applicationUsername);
        UriComponents uriComponents = MvcUriComponentsBuilder.fromMethodName(BadgesEndpoint.class, "badgesGet", 1).build();
        ApplicationDTO dto = toDTO(app, uriComponents);

        if (dto == null) {
            return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }

    @Override
    @RequestMapping(value = "/{applicationName}", method = RequestMethod.PUT)
    public ResponseEntity<Void> applicationsApplicationNamePut(@ApiParam(value = "applicationName", required = true) @PathVariable("applicationName") String applicationUsername, @ApiParam(value = "Modification of the application") @RequestBody Registration body) {

        Application app = apprepository.findByName(applicationUsername);
        
        if(app == null){
        return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        if (body.getApplicationName() != null) {
            if (!body.getApplicationName().equals(" ")) {
                app.setName(body.getApplicationName());
            } else {
                body.setApplicationName(app.getName());
            }
        }
        
          if(body.getPassword()!= null){
            
            if(!body.getPassword().equals(" ")){
                try {
                    String password = Application.doHash(body.getPassword(), app.getSel());
                    System.out.println("mot de passe update" + password);
                    app.setPassword(password);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(ApplicationsEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(ApplicationsEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            
            }
            
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
            String password = "";
            
            if(apprepository.findByName(body.getApplicationName())!= null){
               
                System.out.println(body.getApplicationName());
             return new ResponseEntity("name already use", HttpStatus.UNPROCESSABLE_ENTITY);
          }
           
            Application app = new Application(body.getApplicationName(), body.getPassword());

            apiKey.setApp(app);
            try {
                apprepository.save(app);
            } catch (javax.persistence.PersistenceException e) {
               return new ResponseEntity("name already use", HttpStatus.UNPROCESSABLE_ENTITY);
            }
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
        dto.setApplicationName(app.getName());
      

        return dto;

    }

}
