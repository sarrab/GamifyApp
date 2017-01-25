/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.services.dao;

import ch.heigvd.gamification.model.EndUser;
import ch.heigvd.gamification.model.PointAwards;
import ch.heigvd.gamification.model.PointScale;
import java.util.Date;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Thibaut-PC
 */
public interface PointAwardsRepository extends CrudRepository<PointAwards, Long> {
    PointAwards findByEnduser(EndUser endUser);
    PointAwards findByIdAndPointScale(Long id, PointScale pointScale);
}
