package com.hellokoding.jpa.repository;

import com.hellokoding.jpa.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wangkaicheng on 2017/8/1.
 */

public interface FeatureRepository extends JpaRepository<Feature, Integer> {
}
