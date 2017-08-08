package com.hellokoding.jpa.repositories;

import com.hellokoding.jpa.models.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by wangkaicheng on 2017/8/1.
 */
@Repository
public interface FeatureRepository extends JpaRepository<Feature, Integer> {
}
