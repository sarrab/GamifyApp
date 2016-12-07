/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.api;

import ch.heigvd.gamification.api.dto.EventDTO;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Thibaut-PC
 */
public class EventsEndpoint  implements EventsApi{

    @Override
    public ResponseEntity<Void> eventsPost(@ApiParam(value = "Event produced in the application", required = true) @RequestBody EventDTO body) {
        return null;
    }
}
