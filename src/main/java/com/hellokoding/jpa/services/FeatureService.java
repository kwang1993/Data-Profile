package com.hellokoding.jpa.services;

import com.hellokoding.jpa.models.Feature;
import com.hellokoding.jpa.repositories.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by wangkaicheng on 2017/8/2.
 */
@Service
public class FeatureService {

    @Autowired
    private FeatureRepository featureRepository;

    public Feature save(Feature feature){
        return featureRepository.save(feature);
    }

    public void deleteAll(){
        featureRepository.deleteAll();
    }

    public void delete(Integer id){
        featureRepository.delete(id);
    }

    public void delete(Feature feature){
        featureRepository.delete(feature);
    }

    public Iterable <Feature> findAll(){
        return featureRepository.findAll();
    }

    public Feature findOne(Integer id){
        return featureRepository.findOne(id);
    }

}
