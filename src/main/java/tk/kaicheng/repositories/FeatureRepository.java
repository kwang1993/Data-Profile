package tk.kaicheng.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tk.kaicheng.models.Feature;
import tk.kaicheng.models.ProfileFeature;
import tk.kaicheng.models.User;

import java.util.List;

/**
 * Created by wangkaicheng on 2017/8/1.
 */
@Repository
public interface FeatureRepository extends JpaRepository<Feature, Integer> {
    Feature findByFeatureName(String featureName);
    List <Feature> findByUser(User user);

    @Query(value = "update feature set feature_name = ?2 where feature_id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void updateFeatureName(int feature_id, String newFeatureName);

    @Query(value = "select profile_id, feature_value from profile_feature where feature_id = ?1", nativeQuery = true)
    List<Object[]> findProfileFeatureByFeatureId(int feature_id);
}
