package com.hellokoding.jpa;

import com.hellokoding.jpa.model.User;
import com.hellokoding.jpa.repository.ProfileRepository;
import com.hellokoding.jpa.model.Profile;
import com.hellokoding.jpa.model.ProfileFeature;
import com.hellokoding.jpa.model.Feature;
import com.hellokoding.jpa.repository.FeatureRepository;
import com.hellokoding.jpa.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;

@SpringBootApplication
public class HelloJpaApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(HelloJpaApplication.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private FeatureRepository featureRepository;

    public static void main(String[] args) {
        SpringApplication.run(HelloJpaApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... strings) throws Exception {
        User user  = new User("sb","");

        Profile profileA = new Profile("Profile A", user);

        Feature featureA = new Feature("Feature A");

        ProfileFeature profileFeature = new ProfileFeature();
        profileFeature.setProfile(profileA);
        profileFeature.setFeature(featureA);
        profileFeature.setFeatureValue("feature_value");

        profileA.getProfileFeatures().add(profileFeature);
        user.getProfiles().add(profileA);
        userRepository.save(user);
        featureRepository.save(featureA);
        profileRepository.save(profileA);

        // test
        System.out.println(profileA.getProfileFeatures().size());

        /*
        // update
        profileA.getProfileFeatures().remove(profileFeature);
        profileRepository.save(profileA);

        // test
        System.out.println(profileA.getProfileFeatures().size());
        */
    }
}
