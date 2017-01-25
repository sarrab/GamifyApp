package ch.heigvd.gamification.api;

import ch.heigvd.gamification.api.dto.BadgeDTO;
import ch.heigvd.gamification.api.dto.EndUserDTO;
import ch.heigvd.gamification.api.dto.EndUserReputationDTO;
import ch.heigvd.gamification.services.dao.ApplicationRepository;
import ch.heigvd.gamification.services.dao.EndUserRepository;
import ch.heigvd.gamification.model.Application;
import ch.heigvd.gamification.model.AuthenKey;
import ch.heigvd.gamification.model.Badge;
import ch.heigvd.gamification.model.EndUser;
import ch.heigvd.gamification.model.PointAwards;
import ch.heigvd.gamification.services.dao.AuthenKeyRepository;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static java.util.stream.Collectors.toList;
import java.util.stream.StreamSupport;
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
@RequestMapping(value = "/endUser")
public class EndUserEndpoint implements EndUserApi {

    private final EndUserRepository endUserRepository;

    private final ApplicationRepository applicationRepository;

    private final AuthenKeyRepository auhtenKeyRepository;

    @Autowired
    public EndUserEndpoint(EndUserRepository endUserRepository, ApplicationRepository applicationRepository, AuthenKeyRepository auhtenKeyRepository) {
        this.endUserRepository = endUserRepository;
        this.applicationRepository = applicationRepository;
        this.auhtenKeyRepository = auhtenKeyRepository;
    }

    @Override
    @RequestMapping(value = "{endUserID}/badges", method = RequestMethod.GET)
    public ResponseEntity<EndUserReputationDTO> endUserEndUserIDReputationGet(@ApiParam(value = "endUserID", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken, @ApiParam(value = "endUserID", required = true) @PathVariable("endUserID") Long endUserID) {

        AuthenKey apiKey = auhtenKeyRepository.findByAppKey(xGamificationToken);

        if (apiKey == null) {
            return new ResponseEntity("apikey not exist", HttpStatus.UNAUTHORIZED);
        }
        Application app = apiKey.getApp();

        if (app != null) {

            EndUser endUser = endUserRepository.findByIdappAndApp(endUserID, app);

            if (endUser != null) {

                List<BadgeDTO> badgesDTO = new ArrayList<>();

                endUser.getBadgeAwards().stream().map((b) -> {
                    BadgeDTO badgeDto = new BadgeDTO();
                    Badge badge = b.getBadge();
                    badgeDto.setDescription(badge.getDescription());
                    badgeDto.setImageURI(badge.getImage());
                    badgeDto.setName(badge.getName());
                    return badgeDto;
                }).forEach((badgeDto) -> {
                    badgesDTO.add(badgeDto);
                });
                return new ResponseEntity(badgesDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

        }

        return new ResponseEntity("No content available", HttpStatus.BAD_REQUEST);
    }

    @Override
    @RequestMapping(value = "{endUserID}/reputation", method = RequestMethod.GET)
    public ResponseEntity<Void> endUserEndUserIDBadgesGet(@ApiParam(value = "endUserID", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken, @ApiParam(value = "endUserID", required = true) @PathVariable("endUserID") Long endUserID) {

        AuthenKey apiKey = auhtenKeyRepository.findByAppKey(xGamificationToken);

        if (apiKey == null) {
            return new ResponseEntity("apikey not exist", HttpStatus.UNAUTHORIZED);
        }
        Application app = apiKey.getApp();

        if (app != null) {

            EndUser endUser = endUserRepository.findByIdappAndApp(endUserID, app);

            if (endUser != null) {

                System.out.println("endUserName" + endUserID);
                System.out.println("endUser" + endUser.getID());

                EndUserReputationDTO dto = new EndUserReputationDTO();
                List<PointAwards> poindAwards = endUserRepository.findOne(endUserID).getPointAwards();
                Long point;
                point = 0L;

                point = poindAwards.stream().map((p) -> p.getPoint()).reduce(point, (accumulator, _item) -> accumulator + _item);

                System.out.println("points" + point);

                dto.setPoints(point);

                List<BadgeDTO> badgeDtos = new ArrayList();

                endUser.getBadgeAwards().stream().map((b) -> {
                    BadgeDTO badgedto = new BadgeDTO();
                    Badge badge = b.getBadge();
                    badgedto.setDescription(badge.getDescription());
                    badgedto.setImageURI(badge.getImage());
                    badgedto.setName(badge.getName());
                    return badgedto;
                }).forEach((badgedto) -> {
                    badgeDtos.add(badgedto);
                });
                dto.setBadge(badgeDtos);

                return new ResponseEntity(dto, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

        }

        return new ResponseEntity("No content is available", HttpStatus.BAD_REQUEST);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EndUserDTO>> endUserGet(@ApiParam(value = "token that identifies the app sending the request", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken) {

        AuthenKey apiKey = auhtenKeyRepository.findByAppKey(xGamificationToken);

        if (apiKey == null) {
            return new ResponseEntity("apikey not exist", HttpStatus.UNAUTHORIZED);
        }
        Application app = apiKey.getApp();
        if (app != null) {
            return new ResponseEntity<>(StreamSupport.stream(endUserRepository.findAll().spliterator(), true)
                    .map(p -> toDTO(p))
                    .collect(toList()), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> endUserIdDelete(@ApiParam(value = "id", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        AuthenKey apiKey = auhtenKeyRepository.findByAppKey(xGamificationToken);

        if (apiKey == null) {
            return new ResponseEntity("apikey not exist", HttpStatus.UNAUTHORIZED);
        }
        Application app = apiKey.getApp();
        if (app != null) {

            EndUser endUser = endUserRepository.findByIdAndApp(id, app);
            endUserRepository.delete(endUser);
            return new ResponseEntity(HttpStatus.OK);
        } else {

            return new ResponseEntity(HttpStatus.NOT_FOUND);

        }

    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<EndUserDTO> endUserPost(@ApiParam(value = "token that identifies the app sending the request", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken, @ApiParam(value = "données de l'utilisateur", required = true) @RequestBody EndUserDTO body) {

        AuthenKey apiKey = auhtenKeyRepository.findByAppKey(xGamificationToken);

        if (apiKey == null) {
            return new ResponseEntity("apikey not exist", HttpStatus.UNAUTHORIZED);
        }
        Application app = apiKey.getApp();
        EndUser endUser = new EndUser();

        endUser.setName(body.getName());
        if (app != null) {

            endUser.setApp(app);
            if (endUserRepository.findByIdAndApp(endUser.getID(), app) != null) {

                return new ResponseEntity("this user already exixts", HttpStatus.BAD_REQUEST);
            } else {

                endUser.setName(body.getName());
                endUser.setApp(app);
                endUser.setDate(new Date());
                endUser.setIdapp(endUser.getId());
                endUserRepository.save(endUser);

                return new ResponseEntity(HttpStatus.CREATED);

            }

        }

        return new ResponseEntity("No content available", HttpStatus.BAD_REQUEST);

    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<EndUserDTO> endUserIdGet(@ApiParam(value = "id", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken, @ApiParam(value = "id", required = true) @PathVariable("id") Long id) {
        AuthenKey apiKey = auhtenKeyRepository.findByAppKey(xGamificationToken);

        if (apiKey == null) {
            return new ResponseEntity("apikey not exist", HttpStatus.UNAUTHORIZED);
        }
        Application app = apiKey.getApp();
        if (app != null) {

            EndUser endUser = endUserRepository.findByIdAndApp(id, app);
            if (endUser != null) {
                EndUserDTO endUserDto = new EndUserDTO();

                endUserDto.setName(endUser.getName());

                return new ResponseEntity(endUserDto, HttpStatus.OK);
            } else {

                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity("no content available", HttpStatus.BAD_REQUEST);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> endUserIdPut(@ApiParam(value = "id", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken, @ApiParam(value = "id", required = true) @PathVariable("id") Long id, @ApiParam(value = "Update of a user") @RequestBody EndUserDTO body) {

        AuthenKey apiKey = auhtenKeyRepository.findByAppKey(xGamificationToken);

        if (apiKey == null) {
            return new ResponseEntity("apikey not exist", HttpStatus.UNAUTHORIZED);
        }
        Application app = apiKey.getApp();

        if (app != null) {

            EndUser endUser = endUserRepository.findByIdAndApp(id, app);

            if (endUser != null) {

                if (!body.getName().equals(" ")) {

                    endUser.setName(body.getName());

                } else {

                    body.setName(endUser.getName());

                }

            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);

            }

            endUserRepository.save(endUser);
            return new ResponseEntity(HttpStatus.OK);

        }

        return new ResponseEntity("no content available", HttpStatus.BAD_REQUEST);
    }

    /**
     * convertir un endUserDto en enduser
     *
     * @param endUser
     * @return
     */
    public EndUserDTO toDTO(EndUser endUser) {
        EndUserDTO endUserDto = new EndUserDTO();

        endUserDto.setName(endUser.getName());
        return endUserDto;
    }

}
