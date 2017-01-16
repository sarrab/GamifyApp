/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.api;

import ch.heigvd.gamification.api.dto.PointScaleDTO;
import ch.heigvd.gamification.model.AuthenKey;
import ch.heigvd.gamification.services.dao.PointScaleRepository;
import ch.heigvd.gamification.model.PointScale;
import ch.heigvd.gamification.services.dao.AuthenKeyRepository;
import java.util.List;

import io.swagger.annotations.ApiParam;
import static java.util.stream.Collectors.toList;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author user
 */
@RestController
@RequestMapping(value = "/PointScale")
public class PointScalesEndpoint implements PointScalesApi {

    PointScaleRepository pointscaleRepository;
    AuthenKeyRepository authenRepository;

    @Autowired
    public PointScalesEndpoint(PointScaleRepository pointscaleRepository, AuthenKeyRepository authenRepository) {
        this.pointscaleRepository = pointscaleRepository;
        this.authenRepository = authenRepository;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PointScaleDTO>> pointScalesGet(@ApiParam(value = "token that identifies the app sending the request", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken) {

        AuthenKey apiKey = authenRepository.findByAppKey(xGamificationToken);
        if (apiKey == null) {
            return new ResponseEntity("apikey not exist", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<List<PointScaleDTO>>(StreamSupport.stream(pointscaleRepository.findAll().spliterator(), true)
                .map(p -> toDTO(p))
                .collect(toList()), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/{pointScaleId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> pointScalesPointScaleIdDelete(@ApiParam(value = "pointScaleIdt", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken, @ApiParam(value = "pointScaleId", required = true) @PathVariable("pointScaleId") Long pointScaleId) {

        AuthenKey apiKey = authenRepository.findByAppKey(xGamificationToken);
        if (apiKey == null) {
            return new ResponseEntity("apikey not exist", HttpStatus.BAD_REQUEST);
        }

        PointScale pointScale = pointscaleRepository.findOne(pointScaleId);

        if (pointScale != null) {
            pointscaleRepository.delete(pointScale);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @RequestMapping(value = "/{pointScaleId}", method = RequestMethod.GET)
    public ResponseEntity<PointScaleDTO> pointScalesPointScaleIdGet(@ApiParam(value = "pointScaleId", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken, @ApiParam(value = "pointScaleId", required = true) @PathVariable("pointScaleId") Long pointScaleId) {

        AuthenKey apiKey = authenRepository.findByAppKey(xGamificationToken);
        if (apiKey == null) {
            return new ResponseEntity("apikey not exist", HttpStatus.BAD_REQUEST);
        }

        PointScale p = pointscaleRepository.findOne(pointScaleId);

        PointScaleDTO dto = toDTO(p);
        dto.setId(p.getId());

        return new ResponseEntity(dto, HttpStatus.CREATED);
    }

    @Override
    @RequestMapping(value = "/{pointScaleId}", method = RequestMethod.PUT)
    public ResponseEntity<Void> pointScalesPointScaleIdPut(@ApiParam(value = "pointScaleId", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken, @ApiParam(value = "pointScaleId", required = true) @PathVariable("pointScaleId") Long pointScaleId, @ApiParam(value = "Modification of the pointScale") @RequestBody PointScaleDTO body) {

        AuthenKey apiKey = authenRepository.findByAppKey(xGamificationToken);
        if (apiKey == null) {
            return new ResponseEntity("apikey not exist", HttpStatus.BAD_REQUEST);
        }

        PointScale pointScale = pointscaleRepository.findOne(pointScaleId);

        if (!body.getDescription().equals(" ")) {
            pointScale.setDescription(body.getDescription());
        } else {
            body.setDescription(pointScale.getDescription());
        }

        if (!body.getName().equals(" ")) {
            pointScale.setName(body.getName());
        } else {
            body.setName(pointScale.getName());
        }
        pointScale.setMinpoint(body.getNbrDePoints());

        pointscaleRepository.save(pointScale);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @Override

    public ResponseEntity<Void> pointScalesPost(@ApiParam(value = "token that identifies the app sending the request", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken, @ApiParam(value = "PointScale to add", required = true) @RequestBody PointScaleDTO body) {

        AuthenKey apiKey = authenRepository.findByAppKey(xGamificationToken);
        if (apiKey == null) {
            return new ResponseEntity("apikey not exist", HttpStatus.BAD_REQUEST);
        }

        PointScale pointScale = new PointScale();

        if (pointscaleRepository.findByName(body.getName()) != null) {

            return new ResponseEntity("poinscat exist already", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (body != null) {
            pointScale.setDescription(body.getDescription());
            pointScale.setId(body.getId());
            pointScale.setName(body.getName());
            pointScale.setMinpoint(body.getNbrDePoints());
            pointscaleRepository.save(pointScale);
            return new ResponseEntity(HttpStatus.CREATED);

        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    public PointScaleDTO toDTO(PointScale pointScale) {
        PointScaleDTO pointscaledto = new PointScaleDTO();

        pointscaledto.setNbrDePoints(pointScale.getMinpoint());
        pointscaledto.setDescription(pointScale.getDescription());
        pointscaledto.setName(pointScale.getName());
        pointscaledto.setId(pointScale.getId());

        return pointscaledto;
    }

}
