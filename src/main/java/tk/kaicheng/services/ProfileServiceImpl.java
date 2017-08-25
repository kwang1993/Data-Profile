package tk.kaicheng.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.kaicheng.POJO.FeatureAndValue;
import tk.kaicheng.models.Profile;
import tk.kaicheng.models.User;
import tk.kaicheng.repositories.ProfileRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangkaicheng on 2017/8/2.
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Profile save(Profile profile){
        return profileRepository.save(profile);
    }

    @Override
    public void deleteAll(){
        profileRepository.deleteAll();
    }

    @Override
    public void delete(Integer id){
        profileRepository.delete(id);
    }

    @Override
    public void delete(Profile profile){
        profileRepository.delete(profile);
    }

    @Override
    public List <Profile> findAll(){
        return profileRepository.findAll();
    }

    @Override
    public Profile findOne(Integer id){
        return profileRepository.findOne(id);
    }

    @Override
    public Profile findByProfileNameAndUser(String profileName, User user){
        return profileRepository.findByProfileNameAndUser(profileName, user);
    }

    @Override
    public List<Profile> findByUser(User user) {
        return profileRepository.findByUser(user);
    }

    @Override
    public void updateProfileName(int profile_id, String newProfileName) {
        profileRepository.updateProfileName(profile_id, newProfileName);
    }

    @Override
    public void saveProfileFeature(int profile_id, int feature_id, String featureValue) {
        profileRepository.saveProfileFeature(profile_id, feature_id, featureValue);
    }

    @Override
    public void deleteProfileFeatureById(int profile_id, int feature_id) {
        profileRepository.deleteProfileFeatureById(profile_id, feature_id);
    }

    @Override
    public void updateProfileFeatureByFeatureValue(int profile_id, int feature_id, String newFeatureValue) {
        profileRepository.updateProfileFeatureByFeatureValue(profile_id, feature_id, newFeatureValue);
    }

    @Override
    public String findProfileFeatureById(int profile_id, int feature_id) {
        return profileRepository.findProfileFeatureById(profile_id, feature_id);
    }

    @Override
    public List<FeatureAndValue> findFeatureAndValuesByProfileId(int profile_id) {
        List<Object[]> ls = profileRepository.findFeatureAndValuesByProfileId(profile_id);
        List <FeatureAndValue> res = new ArrayList<>();
        if(ls == null || ls.size() == 0 ) return res;
        for(Object [] objects : ls ){
            FeatureAndValue featureAndValue = new FeatureAndValue((int)objects[0], (int)objects[1], (String)objects[2], (String)objects[3]);
            res.add(featureAndValue);
        }
        return res;
    }

    @Override
    public FeatureAndValue findFeatureAndValueById(int profile_id, int feature_id) {
        Object[] objects = profileRepository.findFeatureAndValueById(profile_id, feature_id);
        FeatureAndValue featureAndValue = new FeatureAndValue((int)objects[0], (int)objects[1], (String)objects[2], (String)objects[3]);
        return featureAndValue;
    }

    @Override
    public List<Object[]> findProfileFeatureByProfileId(int profile_id) {
        return profileRepository.findProfileFeatureByProfileId(profile_id);
    }

    @Override
    public List<Object[]> findAllProfileFeatures() {
        return profileRepository.findAllProfileFeatures();
    }

}
