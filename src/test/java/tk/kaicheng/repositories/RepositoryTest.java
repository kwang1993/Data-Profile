package tk.kaicheng.repositories;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import tk.kaicheng.configuration.RepositoryConfiguration;
import tk.kaicheng.models.*;
import tk.kaicheng.repositories.FeatureRepository;
import tk.kaicheng.repositories.ProfileRepository;
import tk.kaicheng.repositories.RoleRepository;
import tk.kaicheng.repositories.UserRepository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {RepositoryConfiguration.class})

public class RepositoryTest {
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private FeatureRepository featureRepository;

    @Test
    public void testSave(){

        // variables
        String roleName = "USER";
        String userName = "testUser";
        String password = "testPassword";
        String profileName = "testProfile";
        String featureName = "testFeature";
        String featureValue = "testFeatureValue";

        // create role
        Role role = roleRepository.findByRoleName(roleName);
        if( role == null) {
            role = new Role();
            role.setRoleName(roleName);
            roleRepository.save(role); // role must be persistent for users
            assertNotNull(roleRepository.findByRoleName(roleName));
        }


        // create user
        User user = userRepository.findByUserName(userName);
        if(user != null){
            userRepository.delete(user);
            assertNull(userRepository.findByUserName(userName));
        }
        user = new User();
        user.setUserName(userName);
//        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setPassword(password);
        user.setRoles(new HashSet<Role>(Arrays.asList(role)));
        user.setProfiles(new HashSet<Profile>());
        userRepository.save(user); // user must be persistent for profiles
        assertNotNull(userRepository.findByUserName(userName));


        // create profile
        Profile profile = profileRepository.findByProfileName(profileName);
        if(profile != null) {
            profileRepository.delete(profile);
            assertNull(profileRepository.findByProfileName(profileName));
        }
        profile = new Profile();
        profile.setProfileName(profileName);
        profile.setUser(user);
        profile.setProfileFeatures(new HashSet<ProfileFeature>());
        profileRepository.save(profile); // profile must be persistent for profile feature
        assertNotNull(profileRepository.findByProfileName(profileName).getUser());

        // create feature
        Feature feature = featureRepository.findByFeatureName(featureName);
        if(feature == null){
            feature = new Feature();
            feature.setFeatureName(featureName);
            featureRepository.save(feature); // feature must be persistent for profileFeatures
            assertNotNull(featureRepository.findByFeatureName(featureName));
        }

        // create profile feature
        ProfileFeature profileFeature = new ProfileFeature();
        profileFeature.setProfile(profile);
        profileFeature.setFeature(feature);
        profileFeature.setFeatureValue(featureValue);
        profileRepository.saveProfileFeature(profile.getId(),feature.getId(),featureValue);

        String fetchedFeatureValue = profileRepository.findProfileFeatureById(profile.getId(), feature.getId());
        assertEquals(fetchedFeatureValue, featureValue);
    }

    @Test
    public void testUpdateRole(){
        // update role USER and change it back
        String roleName = "USER";
        String newRoleName = "updateUSER";
        Role role = roleRepository.findByRoleName(roleName );
        if(role == null) return;
        role.setRoleName(newRoleName);
        roleRepository.save(role);
        assertNotNull(roleRepository.findByRoleName(newRoleName));
        role = roleRepository.findByRoleName(newRoleName);
        role.setRoleName(roleName);
        roleRepository.save(role);
        assertNotNull(roleRepository.findByRoleName(roleName));
    }

    @Test
    public void testDeleteRole(){
        String roleName = "USER";
        Role role = roleRepository.findByRoleName(roleName);
        if(role == null) return;
        roleRepository.delete(role.getId());
        assertNull(roleRepository.findByRoleName(roleName));
        assertEquals(roleRepository.findUserRoleByRoleId(role.getId()).size(), 0);
    }

    @Test
    public void testUpdateUser(){
        // update testUser and change it back
        String userName = "testUser";
        String newUserName = "newUser";
        User fetchedUser = userRepository.findByUserName(userName);
        if(fetchedUser == null) return;
        fetchedUser.setUserName(newUserName);
        userRepository.save(fetchedUser);
        assertNotNull(userRepository.findByUserName(newUserName));
        fetchedUser.setUserName(userName);
        userRepository.save(fetchedUser);
        assertNotNull(userRepository.findByUserName(userName));
    }

    @Test
    public void testDeleteUser(){
        // delete testUser
        String userName = "testUser";
        User user = userRepository.findByUserName(userName);
        if(user == null) return;
        userRepository.delete(user);
        assertNull(userRepository.findByUserName(userName));
        assertEquals(userRepository.findUserRoleByUserId(user.getId()).size(), 0);
        assertEquals(profileRepository.findByUser(user).size(), 0);
    }

    @Test
    public void testUpdateProfile(){
        // update testUser and change it back
        String profileName = "testProfile";
        String newProfileName = "newProfile";
        Profile fetchedProfile = profileRepository.findByProfileName(profileName);
        if(fetchedProfile == null) return;

        profileRepository.updateProfileName(fetchedProfile.getId(), newProfileName);
        assertNotNull(profileRepository.findByProfileName(newProfileName));
        profileRepository.updateProfileName(fetchedProfile.getId(), profileName);
        assertNotNull(profileRepository.findByProfileName(profileName));
    }

    @Test
    public void testDeleteProfile(){
        String profileName = "testProfile";
        Profile profile = profileRepository.findByProfileName(profileName);
        if(profile == null) return;
        profileRepository.delete(profile.getId());
        assertNull(profileRepository.findByProfileName(profileName));
        assertEquals(profileRepository.findProfileFeatureByProfileId(profile.getId()).size(), 0);
    }

    @Test
    public void testUpdateFeature(){
        // update testUser and change it back
        String featureName = "testFeature";
        String newFeatureName = "newFeature";
        Feature fetchedFeature = featureRepository.findByFeatureName(featureName);
        if(fetchedFeature == null) return;
        fetchedFeature.setFeatureName(newFeatureName);
        featureRepository.save(fetchedFeature);
        assertNotNull(featureRepository.findByFeatureName(newFeatureName));
        fetchedFeature.setFeatureName(featureName);
        featureRepository.save(fetchedFeature);
        assertNotNull(featureRepository.findByFeatureName(featureName));
    }

    @Test
    public void testDeleteFeature(){
        String featureName = "testFeature1";
        Feature feature = featureRepository.findByFeatureName(featureName);
        if(feature == null) return;
        featureRepository.delete(feature.getId());
        assertNull(featureRepository.findByFeatureName(featureName));
        assertEquals(featureRepository.findProfileFeatureByFeatureId(feature.getId()).size(), 0);

    }

    @Test
    public void testSaveUserRole(){
        String userName = "testUser";
        String roleName = "USER";
        User user = userRepository.findByUserName(userName);
        Role role = roleRepository.findByRoleName(roleName);
        if(user == null || role == null) return;
        if(userRepository.findUserRoleByUserId(user.getId()).contains(role.getId())) return;
        userRepository.saveUserRole(user.getId(), role.getId());
        assertTrue(userRepository.findUserRoleByUserId(user.getId()).contains(role.getId()));
    }

    @Test
    public void testUpdateUserRole(){
        //Nothing to update
    }

    @Test
    public void testDeleteUserRole(){
        String userName = "testUser";
        String roleName = "USER";
        User user = userRepository.findByUserName(userName);
        Role role = roleRepository.findByRoleName(roleName);
        if(user == null || role == null) return;
        if(!userRepository.findUserRoleByUserId(user.getId()).contains(role.getId())) return;
        userRepository.deleteUserRoleById(user.getId(), role.getId());
        assertFalse(userRepository.findUserRoleByUserId(user.getId()).contains(role.getId()));
    }

    @Test
    public void testSaveProfileFeature() {
        //Already done in testSave
    }

    @Test
    public void testUpdateProfileFeature(){

        String featureValue = "testFeatureValue";
        String newFeatureValue = "newFeatureValue";
        String profileName = "testProfile";
        String featureName = "testFeature";

        Profile profile = profileRepository.findByProfileName(profileName);
        Feature feature = featureRepository.findByFeatureName(featureName);
        if(profile == null || feature == null) return;

        if(profileRepository.findProfileFeatureById(profile.getId(), feature.getId()) == null) return;
        profileRepository.updateProfileFeatureByFeatureValue(profile.getId(), feature.getId(), newFeatureValue);
        assertEquals(profileRepository.findProfileFeatureById(profile.getId(), feature.getId()), newFeatureValue);
        profileRepository.updateProfileFeatureByFeatureValue(profile.getId(), feature.getId(), featureValue);
        assertEquals(profileRepository.findProfileFeatureById(profile.getId(), feature.getId()), featureValue);
    }

    @Test
    public void testDeleteProfileFeature(){
        String profileName = "testProfile";
        String featureName = "testFeature";
        Profile profile = profileRepository.findByProfileName(profileName);
        Feature feature = featureRepository.findByFeatureName(featureName);
        if(profile == null || feature == null) return;
        if(profileRepository.findProfileFeatureById(profile.getId(), feature.getId()) == null) return;
        profileRepository.deleteProfileFeatureById(profile.getId(), feature.getId());
        assertNull(profileRepository.findProfileFeatureById(profile.getId(), feature.getId()));
    }

}
