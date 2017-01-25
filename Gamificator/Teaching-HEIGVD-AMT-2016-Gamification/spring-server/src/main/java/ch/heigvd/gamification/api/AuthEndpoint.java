/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.api;

import ch.heigvd.gamification.api.dto.Credentials;
import ch.heigvd.gamification.api.dto.Token;
import ch.heigvd.gamification.services.dao.ApplicationRepository;
import ch.heigvd.gamification.services.dao.AuthenKeyRepository;
import ch.heigvd.gamification.model.Application;
import ch.heigvd.gamification.model.AuthenKey;
import io.swagger.annotations.ApiParam;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Thibaut-PC
 */

@RestController
@RequestMapping(value = "/authentications")
public class AuthEndpoint implements AuthenticationsApi {

    private ApplicationRepository applicationsRepository;
    private AuthenKeyRepository authenrepository;


     @Autowired
    public AuthEndpoint(ApplicationRepository applicationsRepository, AuthenKeyRepository authenrepository) {
        this.applicationsRepository = applicationsRepository;
        this.authenrepository = authenrepository;
    }

    @Override
     @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity authenticateApplicationAndGetToken(@ApiParam(value = "The info required to authenticate an application", required = true) @RequestBody Credentials body) {

    
        
        Application app1 = applicationsRepository.findByName(body.getApplicationName());
        
        if(app1 != null){
        
        String password = body.getPassword();
        
        try {
            password = Application.doHash(password, app1.getSel());
            
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(AuthEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }

        Application app = applicationsRepository.findByPassword(password);
        
        
        
        if (app != null) {
            Token token = new Token();
           AuthenKey appKey = authenrepository.findByApp(app);
            token.setApplicationName(appKey.getAppKey());
            return ResponseEntity.ok(token);
        } else {
             System.err.println("nom autorisé" +  password + "" + app1.getSel() + " "  + body.getPassword());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

    }
    
    
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

}

}