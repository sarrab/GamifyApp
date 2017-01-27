package ch.heigvd.gamification.api;

import ch.heigvd.gamification.api.dto.RuleDTO;
import ch.heigvd.gamification.services.dao.ActionBadgeRepository;
import ch.heigvd.gamification.services.dao.ActionPointRepository;
import ch.heigvd.gamification.services.dao.ActionTypeRepository;
import ch.heigvd.gamification.services.dao.ApplicationRepository;
import ch.heigvd.gamification.services.dao.BadgeRepository;
import ch.heigvd.gamification.services.dao.EventTypeRepository;
import ch.heigvd.gamification.services.dao.PointScaleRepository;
import ch.heigvd.gamification.services.dao.RuleRepository;
import ch.heigvd.gamification.model.ActionBadge;
import ch.heigvd.gamification.model.ActionPoints;
import ch.heigvd.gamification.model.ActionType;
import ch.heigvd.gamification.model.Application;
import ch.heigvd.gamification.model.AuthenKey;
import ch.heigvd.gamification.model.Badge;
import ch.heigvd.gamification.model.EventType;
import ch.heigvd.gamification.model.PointScale;
import ch.heigvd.gamification.model.Rule;
import ch.heigvd.gamification.services.dao.AuthenKeyRepository;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Thibaut-PC on 13.12.16.
 */
@RestController
@RequestMapping(value = "/rules")
public class RuleEndpoint implements RulesApi {

   private final RuleRepository rulerepository;

   private final PointScaleRepository pointScaleRepository;

   private final EventTypeRepository eventTypeRepository;

   private final ApplicationRepository applicationRepository;

   private final BadgeRepository badgerepository;

   private final ActionBadgeRepository actionBadgerepository;

   private final ActionPointRepository actionPointsrepository;

   private final ActionTypeRepository actionTypeRepository;

   private final AuthenKeyRepository authenkeyRepository;

   private PointScaleRepository pointscaleRepository;

   final String ACTION_TYPE_POINT_FINAL = "ActionPoints";

   final String ACTION_TYPE_BADGE_FINAL = "ActionBadge";

   @Autowired
   public RuleEndpoint(RuleRepository rulerepository, PointScaleRepository pointScaleRepository, EventTypeRepository eventTypeRepository, ApplicationRepository applicationRepository, BadgeRepository badgerepository, ActionBadgeRepository actionBadgerepository, ActionPointRepository actionPointsrepository, ActionTypeRepository actionTypeRepository, AuthenKeyRepository authenkeyRepository, PointScaleRepository p) {
      this.rulerepository = rulerepository;
      this.pointScaleRepository = pointScaleRepository;
      this.eventTypeRepository = eventTypeRepository;
      this.applicationRepository = applicationRepository;
      this.badgerepository = badgerepository;
      this.actionBadgerepository = actionBadgerepository;
      this.actionPointsrepository = actionPointsrepository;
      this.actionTypeRepository = actionTypeRepository;
      this.authenkeyRepository = authenkeyRepository;
   }

   @Override
   @RequestMapping(method = RequestMethod.GET)
   public ResponseEntity<List<RuleDTO>> rulesGet(@ApiParam(value = "token that identifies the app sending the request", required = true) @RequestHeader(value = "X-Gamification-Token", required = false) String xGamificationToken) {
      AuthenKey apiKey = authenkeyRepository.findByAppKey(xGamificationToken);

      if (apiKey == null) {
         return new ResponseEntity("apikey not exist", HttpStatus.UNAUTHORIZED);
      }
      Application app = apiKey.getApp();

      if (app != null) {
         return new ResponseEntity<>(StreamSupport.stream(rulerepository.findAll().spliterator(), true)
                 .map(p -> toDTO(p))
                 .collect(toList()), HttpStatus.OK);
      }

      return new ResponseEntity(HttpStatus.UNAUTHORIZED);

   }

   @Override
   @RequestMapping(method = RequestMethod.POST)
   public ResponseEntity<Void> rulesPost(@ApiParam(value = "token that identifies the app sending the request", required = true) @RequestHeader(value = "X-Gamification-Token", required = false) String xGamificationToken, @ApiParam(value = "paramètres de la règles créée", required = true) @RequestBody RuleDTO body) {

      AuthenKey apiKey = authenkeyRepository.findByAppKey(xGamificationToken);

      if (apiKey == null) {
         return new ResponseEntity("apikey not exist", HttpStatus.UNAUTHORIZED);
      };

      Application app = apiKey.getApp();

      EventType eventype = new EventType();
      Rule rule = new Rule();
      ActionType action;

      if (app != null) {

         eventype = eventTypeRepository.findByEventNameAndApp(body.getEvent(), app);
         if (eventype == null) {

            eventype = new EventType(body.getEvent(), app);
            eventTypeRepository.save(eventype);

         }

         rule.setEventyp(eventype);

         PointScale pointScale = pointScaleRepository.findByName(body.getPointScale());
         if (body.getAction().equals("ActionPoints")) {
            if (pointScale != null) {
               rule.setPointscale(pointScale);

            } else {

               return new ResponseEntity("pointScale is not exist", HttpStatus.NOT_FOUND);

            }
         }
         ActionType actiontype = new ActionType();
         List<ActionType> actiontypes = new ArrayList<>();

         actiontype = actionTypeRepository.findOne(body.getActionId());

         if (actiontype == null) {

            if (body.getAction().equalsIgnoreCase(ACTION_TYPE_POINT_FINAL)) {

               // ActionPoints actionpoint = new ActionPoints();
               ActionPoints actionpoint1 = new ActionPoints();
               PointScale p = pointScaleRepository.findByName(body.getPointScale());
               actionpoint1.setNombrePoint(p.getMinpoint());
               actionpoint1.setName("ActionPoints");
               actiontype = actionpoint1;

               actionTypeRepository.save(actiontype);

            }
            System.out.println(body.getAction());
            if (body.getAction().equals(ACTION_TYPE_BADGE_FINAL)) {
               ActionBadge actionbadge = new ActionBadge();
               Badge badge;
               badge = badgerepository.findOne(body.getBadgeId());

               if (badge != null) {

                  ActionBadge actionBadge = new ActionBadge();

                  actionbadge.setName("ActionBadge");
                  actionbadge.setBadge(badge);
                  actionBadge.setBadge(badge);
                  actionBadge.setName(body.getAction());
                  actiontype = actionBadge;

               }

            }
            /*else {
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);

                }*/
         }
         actionTypeRepository.save(actiontype);
         rule.setActionType(actiontype);

         rulerepository.save(rule);

         return new ResponseEntity(HttpStatus.CREATED);
      }

      return new ResponseEntity("content is available", HttpStatus.UNAUTHORIZED);

   }

   @Override
   @RequestMapping(value = "/{ruleId}", method = RequestMethod.DELETE)
   public ResponseEntity<Void> rulesRuleIdDelete(@ApiParam(value = "ruleId", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken, @ApiParam(value = "ruleId", required = true) @PathVariable("ruleId") Long ruleId) {

      AuthenKey apiKey = authenkeyRepository.findByAppKey(xGamificationToken);
      if (apiKey == null) {
         return new ResponseEntity("apikey not exist", HttpStatus.UNAUTHORIZED);
      }

      Rule rule = rulerepository.findOne(ruleId);

      if (rule != null) {
         rulerepository.delete(rule);
         return new ResponseEntity(HttpStatus.OK);
      } else {
         return new ResponseEntity(HttpStatus.NOT_FOUND);
      }
   }

   @Override
   public ResponseEntity<RuleDTO> rulesRuleIdGet(@ApiParam(value = "ruleId", required = true) @RequestHeader(value = "X-Gamification-Token", required = true) String xGamificationToken, @ApiParam(value = "ruleId", required = true) @PathVariable("ruleId") Long ruleId) {

      AuthenKey apiKey = authenkeyRepository.findByAppKey(xGamificationToken);
      if (apiKey == null) {
         return new ResponseEntity("apikey not exist", HttpStatus.BAD_REQUEST);
      }
      Rule rule = rulerepository.findOne(ruleId);

      if (rule != null) {
         RuleDTO dto = toDTO(rule);

         return new ResponseEntity(dto, HttpStatus.OK);
      } else {

         return new ResponseEntity(HttpStatus.NOT_FOUND);
      }

   }

   @Override
   @RequestMapping(value = "{ruleId}", method = RequestMethod.PUT)
   public ResponseEntity<Void> rulesRuleIdPut(@ApiParam(value = "token that identifies the app sending the request", required = true)
           @RequestHeader(value = "X-Gamification-Token", required = false) String xGamificationToken, @ApiParam(value = "ruleId", required = true)
           @PathVariable("ruleId") Long ruleId, @ApiParam(value = "Modification of the Rule")
           @RequestBody RuleDTO body) {

      AuthenKey apiKey = authenkeyRepository.findByAppKey(xGamificationToken);

      if (apiKey == null) {
         return new ResponseEntity("apikey not exist", HttpStatus.UNAUTHORIZED);
      }

      Application app = apiKey.getApp();

      if (app != null) {
         Rule rule = rulerepository.findOne(ruleId);

         if (!body.getAction().equals(" ")) {

            ActionType ap = actionTypeRepository.findOne(body.getBadgeId());

            if (ap != null) {
               rule.setActionType(ap);
            }
         }

         if (!body.getEvent().equals(" ")) {
            rule.getEventType().setEventName(body.getEvent());
            eventTypeRepository.save(rule.getEventType());
         } else {
            body.setEvent(rule.getEventType().getEventName());
         }

         if (!body.getPointScale().equals(" ")) {
            rule.getPointscale().setName(body.getPointScale());

         } else {
            body.setPointScale(rule.getEventType().getEventName());
         }

         rulerepository.save(rule);
         return new ResponseEntity(HttpStatus.OK);

      }

      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
   }

   public RuleDTO toDTO(Rule rule) {
      RuleDTO ruleDto = new RuleDTO();

      ruleDto.setAction(rule.getActionType().getName());
      switch (rule.getActionType().getClass().getSimpleName()) {
         case "ActionPoints":
            ActionPoints ap = (ActionPoints) rule.getActionType();
            ruleDto.setPoints(ap.getNbrePoint());
            break;
         case "ActionBadge":
            ActionBadge ab = (ActionBadge) rule.getActionType();
            ruleDto.setBadgeId(ab.getId());
      }
      ruleDto.setEvent(rule.getEventType().getEventName());
      ruleDto.setPointScale(rule.getPointscale().getName());
      ruleDto.setId(rule.getId());

      return ruleDto;
   }

}
