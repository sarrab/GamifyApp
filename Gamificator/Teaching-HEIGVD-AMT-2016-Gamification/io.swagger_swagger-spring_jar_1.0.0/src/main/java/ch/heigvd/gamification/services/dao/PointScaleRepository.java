/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.services.dao;

import ch.heigvd.gamification.model.Application;
import ch.heigvd.gamification.model.PointScale;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Thibaut-PC
 */
public interface PointScaleRepository extends CrudRepository<PointScale, Long>{
       PointScale findByName(String name);
       PointScale findByNameAndApp(String name, Application app);
       PointScale findByIdAndApp(Long id, Application app);
       List<PointScale> findAllByApp(Application app);
}
