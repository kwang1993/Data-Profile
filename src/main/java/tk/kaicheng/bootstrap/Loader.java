package tk.kaicheng.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.kaicheng.models.*;
import tk.kaicheng.repositories.FeatureRepository;
import tk.kaicheng.repositories.ProfileRepository;
import tk.kaicheng.repositories.RoleRepository;
import tk.kaicheng.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by wangkaicheng on 2017/8/3.
 */



@Component
@Transactional
public class Loader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private FeatureRepository featureRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {


        userRepository.deleteAll();
        profileRepository.deleteAll();
        featureRepository.deleteAll();
        roleRepository.deleteAll();

        Role roleAdmin = new Role();
        roleAdmin.setRoleName("ADMIN");
        roleRepository.save(roleAdmin);
        Role roleUser = new Role();
        roleUser.setRoleName("USER");
        roleRepository.save(roleUser);

        User user = new User();
        user.setUserName("testUser");
        user.setPassword("testPassword");
        Set<Role> set = new HashSet<>();
        set.add(roleUser);
        user.setRoles(set);
        userRepository.save(user);

        Feature featureA = new Feature();
        featureA.setFeatureName("testFeature");
        featureRepository.save(featureA);

        Profile profileA = new Profile();
        profileA.setProfileName("testProfile");
        profileA.setUser(user);
        ProfileFeature profileFeature = new ProfileFeature();
        profileFeature.setProfile(profileA);
        profileFeature.setFeature(featureA);
        profileFeature.setFeatureValue("testValue");
        profileA.getProfileFeatures().add(profileFeature);
        profileRepository.save(profileA);


    }
}
