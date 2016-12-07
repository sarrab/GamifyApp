/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.api;

import ch.heigvd.gamification.api.dto.Application;
import ch.heigvd.gamification.api.dto.ApplicationDTO;
import ch.heigvd.gamification.api.dto.BadgeDTO;
import ch.heigvd.gamification.dao.ApplicationRepository;
import ch.heigvd.gamification.model.Badge;
import io.swagger.annotations.ApiParam;
import java.net.URI;
import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import static java.util.stream.Collectors.toList;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Thibaut-PC
 */
@RestController
@RequestMapping(value = "/applications")
public class ApplicationsEndpoint implements ApplicationsApi {

    ApplicationRepository apprepository;

    @Autowired
    public ApplicationsEndpoint(ApplicationRepository app) {
        this.apprepository = app;
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
        UriComponents uriComponents = MvcUriComponentsBuilder.fromMethodName(BadgesEndpoint.class, "badgesGet").build();
        ApplicationDTO dto = toDTO(app, uriComponents);

        if (dto == null) {
            return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }

    @Override
    @RequestMapping(value = "/{applicationId}", method = RequestMethod.PUT)
    public ResponseEntity<Void> applicationsApplicationIdPut(@ApiParam(value = "applicationId", required = true) @PathVariable("applicationId") Long applicationId, @ApiParam(value = "Modification of the application") @RequestBody Application body) {
        ch.heigvd.gamification.model.Application app = apprepository.findOne(applicationId);

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
        UriComponents uriComponents = MvcUriComponentsBuilder.fromMethodName(BadgesEndpoint.class, "badgesGet").build();
        return new ResponseEntity<>(StreamSupport.stream(apprepository.findAll().spliterator(), true)
                .map(p -> toDTO(p, uriComponents))
                .collect(toList()), HttpStatus.OK);
    }

    
    @RequestMapping(method = RequestMethod.POST)
    @Override
    public ResponseEntity<Void> applicationsPost(@ApiParam(value = "applicationId", required = true) @RequestBody Application body) {
        if (body != null) {
            ch.heigvd.gamification.model.Application app = new ch.heigvd.gamification.model.Application();
            app.setId(body.getId());
            app.setName(body.getName());
            app.setPassword(body.getPassword());

            apprepository.save(app);
            return new ResponseEntity(body, HttpStatus.CREATED);

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
