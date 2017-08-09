package com.hellokoding.jpa.bootstrap;

import com.hellokoding.jpa.models.Feature;
import com.hellokoding.jpa.models.Profile;
import com.hellokoding.jpa.models.ProfileFeature;
import com.hellokoding.jpa.models.User;
import com.hellokoding.jpa.repositories.FeatureRepository;
import com.hellokoding.jpa.repositories.ProfileRepository;
import com.hellokoding.jpa.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


/**
 * Created by wangkaicheng on 2017/8/3.
 */



@Component

public class Loader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private FeatureRepository featureRepository;

    private Logger log = Logger.getLogger(Loader.class);


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {


        userRepository.deleteAll();
        profileRepository.deleteAll();
        featureRepository.deleteAll();


        User user = new User("testUser", "testPassword");
        userRepository.save(user);

        Feature featureA = new Feature("testFeature");
        featureRepository.save(featureA);

        Profile profileA = new Profile("testProfile", user);
        ProfileFeature profileFeature = new ProfileFeature();
        profileFeature.setProfile(profileA);
        profileFeature.setFeature(featureA);
        profileFeature.setFeatureValue("testValue");
        profileA.getProfileFeatures().add(profileFeature);
        profileRepository.save(profileA);


    }
}
