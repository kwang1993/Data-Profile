package tk.kaicheng.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tk.kaicheng.POJO.FeatureAndValue;
import tk.kaicheng.models.Feature;
import tk.kaicheng.models.Profile;
import tk.kaicheng.models.ProfileFeature;
import tk.kaicheng.models.User;

import java.util.List;

/**
 * Created by wangkaicheng on 2017/8/1.
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Profile findByProfileNameAndUser(String profileName, User user);
    List <Profile> findByUser(User user);

    @Query(value = "update profile set profile_name = ?2 where profile_id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void updateProfileName(int profile_id, String newProfileName);

    @Query(value = "insert into profile_feature(profile_id, feature_id, feature_value) values(?1, ?2, ?3)", nativeQuery = true)
    @Modifying
    @Transactional
    void saveProfileFeature(int profile_id, int feature_id, String featureValue);

    @Query(value = "delete from profile_feature where profile_id = ?1 and feature_id = ?2", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteProfileFeatureById(int profile_id, int feature_id);

    @Query(value = "update profile_feature set feature_value = ?3 where profile_id = ?1 and feature_id = ?2", nativeQuery = true)
    @Modifying
    @Transactional
    void updateProfileFeatureByFeatureValue(int profile_id, int feature_id, String newFeatureValue);

    @Query(value = "select feature_value from profile_feature where profile_id = ?1 and feature_id = ?2", nativeQuery = true)
    public String findProfileFeatureById(int profile_id, int feature_id);

    @Query(value = "select feature_id, feature_value from profile_feature where profile_id = ?1", nativeQuery = true)
    List<Object[]> findProfileFeatureByProfileId(int profile_id);

    @Query(value = "select pf.profile_id, f.feature_id, f.feature_name, pf.feature_value from feature f, profile_feature pf where pf.profile_id = ?1 and pf.feature_id = f.feature_id", nativeQuery = true)
    List<Object[]> findFeatureAndValuesByProfileId(int profile_id);

    @Query(value = "select pf.profile_id, f.feature_id, f.feature_name, pf.feature_value from feature f, profile_feature pf where pf.profile_id = ?1 and f.feature_id = ?2 and pf.feature_id = f.feature_id", nativeQuery = true)
    Object[] findFeatureAndValueById(int profile_id, int feature_id);

    @Query(value = "select profile_id, feature_id, feature_value from profile_feature", nativeQuery = true)
    List<Object[]> findAllProfileFeatures();

//    @Query(value = "select f.feature_name, pf.feature_value from profile_feature pf, feature f, profile p where pf.feature_id=f.feature_id and pf.profile_id=p.profile_id and p.profile_name=?1", nativeQuery = true)
//    @Modifying
//    List<Object[]> findFeatureNameAndFeatureValueByProfileName(String profileName);
}
