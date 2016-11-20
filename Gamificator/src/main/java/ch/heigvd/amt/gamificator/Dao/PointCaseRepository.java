/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.gamificator.Dao;

import ch.heigvd.amt.gamificator.model.PointCase;
import java.io.Serializable;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Thibaut-PC
 */
public interface PointCaseRepository extends CrudRepository<PointCase, Long>{
      PointCase findByname(String name);
}
