package tk.kaicheng.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {


//        userRepository.deleteAll();
//        profileRepository.deleteAll();
//        featureRepository.deleteAll();
//        roleRepository.deleteAll();

        String adminRoleName = "ADMIN";
        String userRoleName = "USER";
        if(roleRepository.findByRoleName(adminRoleName) == null){
            Role roleAdmin = new Role();
            roleAdmin.setRoleName(adminRoleName);
            roleRepository.save(roleAdmin);
        }
        if(roleRepository.findByRoleName(userRoleName) == null) {
            Role roleUser = new Role();
            roleUser.setRoleName(userRoleName);
            roleRepository.save(roleUser);
        }
        // create an admin called root and password is 123456
        String root = "root";
        if(userRepository.findByUsername(root) == null){
            User user = new User();
            user.setUsername(root);
            user.setPassword(bCryptPasswordEncoder.encode("Guess my password, hehe."));
            Set<Role> set = new HashSet<>();
            set.add(roleRepository.findByRoleName(adminRoleName));
            user.setRoles(set);
            userRepository.save(user);
        }
        // create a user called kaicheng and password is 123456
        String name = "kaicheng";
        if(userRepository.findByUsername(name) == null){
            User user = new User();
            user.setUsername(name);
            user.setPassword(bCryptPasswordEncoder.encode("kaicheng"));
            Set<Role> set = new HashSet<>();
            set.add(roleRepository.findByRoleName(userRoleName));
            user.setRoles(set);
            userRepository.save(user);
        }
        name = "harrison";
        if(userRepository.findByUsername(name) == null){
            User user = new User();
            user.setUsername(name);
            user.setPassword(bCryptPasswordEncoder.encode("harrison"));
            Set<Role> set = new HashSet<>();
            set.add(roleRepository.findByRoleName(userRoleName));
            user.setRoles(set);
            userRepository.save(user);
        }
//
//        Feature featureA = new Feature();
//        featureA.setFeatureName("age");
//        featureRepository.save(featureA);
//
//        Profile profileA = new Profile();
//        profileA.setProfileName("patientProfile");
//        profileA.setUser(user);
//        profileRepository.save(profileA);
//
//        profileRepository.saveProfileFeature(profileA.getId(), featureA.getId(), "22");


    }
}
