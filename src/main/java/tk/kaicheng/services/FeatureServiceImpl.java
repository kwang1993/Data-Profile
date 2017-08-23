package tk.kaicheng.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.kaicheng.models.Feature;
import tk.kaicheng.repositories.FeatureRepository;


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
    public Iterable <Feature> findAll(){
        return featureRepository.findAll();
    }

    @Override
    public Feature findOne(Integer id){
        return featureRepository.findOne(id);
    }

}
