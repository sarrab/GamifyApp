package ch.heigvd.gamification.api;


import ch.heigvd.gamification.api.dto.PointsAwardDTO;
import ch.heigvd.gamification.model.Application;
import ch.heigvd.gamification.model.AuthenKey;
import ch.heigvd.gamification.services.dao.ApplicationRepository;
import ch.heigvd.gamification.services.dao.EndUserRepository;
import ch.heigvd.gamification.services.dao.PointAwardsRepository;
import ch.heigvd.gamification.model.EndUser;
import ch.heigvd.gamification.model.PointAwards;
import ch.heigvd.gamification.services.dao.AuthenKeyRepository;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Thibaut-PC on 15.12.16.
 */
@RestController
@RequestMapping(value = "/PointAward")
public class PointAwardEndpoint implements PointsAwardsApi {

    private PointAwardsRepository pointAwardsrepository;
    private final EndUserRepository endUserRepository;
    private  ApplicationRepository applicationRepository;
    private AuthenKeyRepository authenKeyRepository;
    
    
    
    
 @Autowired
    public PointAwardEndpoint(PointAwardsRepository pointAwardsrepository, EndUserRepository endUserRepository, ApplicationRepository applicationRepository, AuthenKeyRepository authenKeyRepository) {
        this.pointAwardsrepository = pointAwardsrepository;
        this.endUserRepository = endUserRepository;
        this.applicationRepository = applicationRepository;
        this.authenKeyRepository = authenKeyRepository;
    }
   
   

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<PointsAwardDTO> pointsAwardsGet(@ApiParam(value = "token that identifies the app sending the request", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken) {
          AuthenKey apiKey = authenKeyRepository.findByAppKey(xGamificationToken);
          
            if(apiKey == null){
        return new ResponseEntity("apikey not exist", HttpStatus.BAD_REQUEST);
        }
        Application app = apiKey.getApp();
           
           if(app != null){
        List<EndUser> endUsers = (List<EndUser>) endUserRepository.findAllByApp(applicationRepository.findByName(xGamificationToken));
        List<PointAwards> pointAwards = new ArrayList<>();
        List<PointsAwardDTO> pointAwardDTOs = new ArrayList<>();
        endUsers.stream().forEach((e) -> {
            pointAwards.add(pointAwardsrepository.findByEnduser(e));
        });

        pointAwards.stream().forEach((e) -> {
            pointAwardDTOs.add(toDTO(e));
        });

        return new ResponseEntity(pointAwardDTOs, HttpStatus.OK);
           }
           return  new ResponseEntity("nom autorisé", HttpStatus.UNAUTHORIZED);
    }
    
    @Override
    @RequestMapping(value = "/{point award's id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> pointsAwardsIdDelete(@ApiParam(value = "point award's id", required = true) @PathVariable("id") Long id) {

        PointAwards pointAward = pointAwardsrepository.findOne(id);

        if (pointAward != null) {
            pointAwardsrepository.delete(pointAward);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

   

    @Override
    @RequestMapping(value = "/{point award's id}", method = RequestMethod.GET)
    public ResponseEntity<PointsAwardDTO> pointsAwardsIdGet(@ApiParam(value = "token that identifies the app sending the request", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken, @ApiParam(value = "point award's id", required = true) @PathVariable("id") Long id) {
        PointAwards pointAward = pointAwardsrepository.findOne(id);
        if (pointAward != null) {

            return new ResponseEntity(toDTO(pointAward), HttpStatus.CREATED);

        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @RequestMapping(value = "/{point award's id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> pointsAwardsIdPut(@ApiParam(value = "token that identifies the app sending the request", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken, @ApiParam(value = "point award's id", required = true) @PathVariable("id") Long id, @ApiParam(value = "Modification of the point award") @RequestBody PointsAwardDTO body) {

        AuthenKey apiKey = authenKeyRepository.findByAppKey(xGamificationToken);
          
            if(apiKey == null){
        return new ResponseEntity("apikey not exist", HttpStatus.BAD_REQUEST);
        }
        Application app = apiKey.getApp();
           
           if(app != null){
        PointAwards pointAwards = pointAwardsrepository.findOne(id);
        if (pointAwards != null) {
            if (!body.getMotif().equals(" ")) {
                pointAwards.setReason(body.getMotif());

            } else {

                body.setMotif(pointAwards.getReason());
            }

            if (body.getPoints() > 0) {

                pointAwards.setPoint(body.getPoints());

            } else {

                body.setPoints(pointAwards.getPoint());

            }

            pointAwardsrepository.save(pointAwards);

            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
           }
           
           return new ResponseEntity("nom autorisé",   HttpStatus.UNAUTHORIZED);
    }

    @Override
    @RequestMapping(value = "/{point award's id}", method = RequestMethod.POST)
    public ResponseEntity<PointsAwardDTO> pointsAwardsPost(@ApiParam(value = "token that identifies the app sending the request", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken, @ApiParam(value = "paramètres de la récompence créée", required = true) @RequestBody PointsAwardDTO body) {
        
        AuthenKey apiKey = authenKeyRepository.findByAppKey(xGamificationToken);
          
            if(apiKey == null){
        return new ResponseEntity("apikey not exist", HttpStatus.BAD_REQUEST);
        }
        Application app = apiKey.getApp();
        
        
        if(app != null){
        
        PointAwards pointAwards = new PointAwards();

        pointAwards.setReason(body.getMotif());
        pointAwards.setPoint(body.getPoints());

        return new ResponseEntity(HttpStatus.CREATED);
        
        }
        
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    public PointsAwardDTO toDTO(PointAwards pointAward) {
        PointsAwardDTO pointAwardDto = new PointsAwardDTO();

        pointAwardDto.setPoints(pointAward.getPoint());
        pointAwardDto.setMotif(pointAward.getReason());

        return pointAwardDto;
    }
    
    

}
