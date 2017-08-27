package tk.kaicheng.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.kaicheng.models.Feature;
import tk.kaicheng.models.Profile;
import tk.kaicheng.models.User;
import tk.kaicheng.repositories.FeatureRepository;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wangkaicheng on 2017/8/2.
 */
@Service
public class FeatureServiceImpl implements FeatureService {

    @Autowired
    private FeatureRepository featureRepository;

    @Override
    public Feature save(Feature feature){
        return featureRepository.save(feature);
    }

    @Override
    public void deleteAll(){
        featureRepository.deleteAll();
    }

    @Override
    public void delete(Integer id){
        featureRepository.delete(id);
    }

    @Override
    public void delete(Feature feature){
        featureRepository.delete(feature);
    }

    @Override
    public List <Feature> findAll(){
        return featureRepository.findAll();
    }

    @Override
    public Feature findOne(Integer id){
        return featureRepository.findOne(id);
    }

    @Override
    public List<Feature> findByProfile(Profile profile) {
        return featureRepository.findByProfile(profile);
    }

    @Override
    public Feature findByFeatureNameAndProfile(String featureName, Profile profile) {
        return featureRepository.findByFeatureNameAndProfile(featureName, profile);
    }

    @Override
    public void updateFeatureName(Integer feature_id, String newFeatureName) {
        featureRepository.updateFeatureName(feature_id, newFeatureName);
    }

    @Override
    public List<Object[]> findEntryFeatureByFeatureId(Integer feature_id) {
        return featureRepository.findEntryFeatureByFeatureId(feature_id);
    }

}
