/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.api;

import ch.heigvd.gamification.api.dto.LeaderboardDTO;
import ch.heigvd.gamification.model.Application;
import ch.heigvd.gamification.model.AuthenKey;
import ch.heigvd.gamification.model.BadgeAward;
import ch.heigvd.gamification.model.EndUser;
import ch.heigvd.gamification.model.PointScale;
import ch.heigvd.gamification.services.dao.AuthenKeyRepository;
import ch.heigvd.gamification.services.dao.BadgeAwardRepository;
import ch.heigvd.gamification.services.dao.EndUserRepository;
import ch.heigvd.gamification.services.dao.PointAwardsRepository;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Thibaut-PC
 */
@RestController
public class LeaderboardEndpoint implements LeaderboardApi {

    private final EndUserRepository endUserRepository;

    private final PointAwardsRepository badgeAwardsRepository;

    private final BadgeAwardRepository badgeAwardRepository;

    private final AuthenKeyRepository authenKeyRepository;

    @Autowired
    public LeaderboardEndpoint(EndUserRepository endUserRepository, PointAwardsRepository badgeAwardsRepository, BadgeAwardRepository badgeAwardRepository, AuthenKeyRepository authenKeyRepository) {
        this.endUserRepository = endUserRepository;
        this.badgeAwardsRepository = badgeAwardsRepository;
        this.badgeAwardRepository = badgeAwardRepository;
        this.authenKeyRepository = authenKeyRepository;
    }

    @Override
    public ResponseEntity<LeaderboardDTO> leaderboardGet(@ApiParam(value = "token that identifies the app sending the request", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken) {
        AuthenKey apiKey = authenKeyRepository.findByAppKey(xGamificationToken);

        if (apiKey == null) {
            return new ResponseEntity("apikey not exist", HttpStatus.BAD_REQUEST);
        }
        Application app = apiKey.getApp();

        if (app != null) {

            List<LeaderboardDTO> results = new ArrayList<>();
            List<Object[]> endUsers = new ArrayList<>();

            endUsers = endUserRepository.getBestUsers(app);

            if (endUsers.isEmpty()) {
                endUsers = endUserRepository.getBestBadgeUsers(app);
                for (int i = 0; i < endUsers.size(); ++i) {
                    LeaderboardDTO tmp = new LeaderboardDTO();
                    EndUser enduser = (EndUser) endUsers.get(i)[0];

                    tmp.setName(enduser.getName());
                    tmp.setEndUserId(enduser.getId());
                    List<String> names = new ArrayList<>();
                    for (BadgeAward badge : enduser.getBadgeAwards()) {
                        names.add(badge.getBadge().getName());
                    }
                    tmp.setBadges(names);

                    if (!results.contains(tmp)) {
                        results.add(tmp);
                    }
                }

                return new ResponseEntity(results, HttpStatus.OK);
            }

            System.out.println("la taille de l'objet" + endUsers.size());
            PointScale pointscale = new PointScale();

            for (int i = 0; i < endUsers.size(); ++i) {

                Long somme = 0l;
                LeaderboardDTO tmp = new LeaderboardDTO();
                EndUser enduser = (EndUser) endUsers.get(i)[0];
                tmp.setName(enduser.getName());
                tmp.setEndUserId(enduser.getId());
                somme = (Long) endUsers.get(i)[2];
                tmp.setPoints(somme.intValue());
                pointscale = (PointScale) endUsers.get(i)[1];
                tmp.setNamePointscale(pointscale.getName());
                List<String> names = new ArrayList<>();
                System.err.println("version badge");
                for (BadgeAward badge : enduser.getBadgeAwards()) {
                    names.add(badge.getBadge().getName());
                }

                tmp.setBadges(names);

                if (!results.contains(tmp)) {
                    results.add(tmp);
                }
            }

            return new ResponseEntity(results, HttpStatus.OK);
        }

        return new ResponseEntity("content no available", HttpStatus.BAD_REQUEST);
    }
}
