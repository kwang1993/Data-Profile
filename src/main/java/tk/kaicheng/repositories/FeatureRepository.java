package tk.kaicheng.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tk.kaicheng.models.Feature;
import tk.kaicheng.models.Profile;
import tk.kaicheng.models.User;

import java.util.List;

/**
 * Created by wangkaicheng on 2017/8/1.
 */
@Repository
public interface FeatureRepository extends JpaRepository<Feature, Integer> {

    Feature findByFeatureNameAndProfile(String featureName, Profile Profile);
    List <Feature> findByProfile(Profile profile);

    @Query(value = "update feature set feature_name = ?2 where feature_id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void updateFeatureName(Integer feature_id, String newFeatureName);

    @Query(value = "select entry_id, feature_value from entry_feature where feature_id = ?1", nativeQuery = true)
    List<Object[]> findEntryFeatureByFeatureId(Integer feature_id);
}
