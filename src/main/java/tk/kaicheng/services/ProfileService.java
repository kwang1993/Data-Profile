package tk.kaicheng.services;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import tk.kaicheng.models.Profile;
import tk.kaicheng.models.User;

import java.util.List;

/**
 * Created by wangkaicheng on 2017/8/20.
 */
public interface ProfileService {
    Profile save(Profile profile);

    void deleteAll();

    void delete(Integer id);

    void delete(Profile profile);

    List <Profile> findAll();

    Profile findOne(Integer id);

    Profile findByProfileName(String profileName);

    List<Profile> findByUser(User user);

    void updateProfileName(int profile_id, String newProfileName);

    void saveProfileFeature(int profile_id, int feature_id, String featureValue);

    void deleteProfileFeatureById(int profile_id, int feature_id);

    void updateProfileFeatureByFeatureValue(int profile_id, int feature_id, String newFeatureValue);

    String findProfileFeatureById(int profile_id, int feature_id);

    List<Object[]> findProfileFeatureByProfileId(int profile_id);

    List<Object[]> findAllProfileFeatures();
}
