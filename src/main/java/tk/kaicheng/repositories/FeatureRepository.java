package tk.kaicheng.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tk.kaicheng.models.Feature;
import tk.kaicheng.models.ProfileFeature;

import java.util.List;

/**
 * Created by wangkaicheng on 2017/8/1.
 */
@Repository
public interface FeatureRepository extends JpaRepository<Feature, Integer> {
    Feature findByFeatureName(String featureName);

    @Query(value = "select profile_id, feature_value from profile_feature where feature_id = ?1", nativeQuery = true)
    public List<Object[]> findProfileFeatureByFeatureId(int feature_id);
}
