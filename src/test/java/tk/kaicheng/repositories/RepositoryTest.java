package tk.kaicheng.repositories;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tk.kaicheng.configuration.RepositoryConfiguration;
import tk.kaicheng.models.*;

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
    private EntryRepository entryRepository;
    @Autowired
    private FeatureRepository featureRepository;

    @Test
    public void testSave(){

        // variables
        String roleName = "USER";
        String username = "testUser";
        String password = "testPassword";
        String profileName = "testProfile";
        String entryName = "testEntry";
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
        User user = userRepository.findByUsername(username);
        if(user != null){
            userRepository.delete(user);
            assertNull(userRepository.findByUsername(username));
        }
        user = new User();
        user.setUsername(username);
//        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setPassword(password);
        user.setRoles(new HashSet<Role>(Arrays.asList(role)));
        user.setProfiles(new HashSet<Profile>());
        userRepository.save(user); // user must be persistent for entrys
        assertNotNull(userRepository.findByUsername(username));

        // create profile
        Profile profile = profileRepository.findByProfileNameAndUser(profileName, user);
        if(profile != null) {
            profileRepository.delete(profile);
            assertNull(profileRepository.findByProfileNameAndUser(profileName, user));
        }
        profile = new Profile();
        profile.setProfileName(profileName);
        profile.setUser(user);
        profileRepository.save(profile);

        // create entry
        Entry entry = entryRepository.findByEntryNameAndProfile(entryName, profile);
        if(entry != null) {
            entryRepository.delete(entry);
            assertNull(entryRepository.findByEntryNameAndProfile(entryName, profile));
        }
        entry = new Entry();
        entry.setEntryName(entryName);
        entry.setProfile(profile);
        entry.setEntryFeatures(new HashSet<EntryFeature>());
        entryRepository.save(entry); // entry must be persistent for entry feature
        assertNotNull(entryRepository.findByEntryNameAndProfile(entryName, profile).getProfile());

        // create feature
        Feature feature = featureRepository.findByFeatureNameAndProfile(featureName,  profile);
        if(feature == null){
            feature = new Feature();
            feature.setFeatureName(featureName);
            feature.setProfile(profile);
            featureRepository.save(feature); // feature must be persistent for entryFeatures
            assertNotNull(featureRepository.findByFeatureNameAndProfile(featureName,  profile));
        }

        // create entry feature
        EntryFeature entryFeature = new EntryFeature();
        entryFeature.setEntry(entry);
        entryFeature.setFeature(feature);
        entryFeature.setFeatureValue(featureValue);
        entryRepository.saveEntryFeature(entry.getId(),feature.getId(),featureValue);

        String fetchedFeatureValue = entryRepository.findEntryFeatureById(entry.getId(), feature.getId());
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
    public void deleteAllRoles(){
        roleRepository.deleteAll();
        assertEquals(roleRepository.findAll().size(), 0);
        assertEquals(userRepository.findAllUserRoles().size(), 0);
    }

    @Test
    public void testUpdateUser(){
        // update testUser and change it back
        String username = "testUser";
        String newUsername = "newUser";
        User fetchedUser = userRepository.findByUsername(username);
        if(fetchedUser == null) return;
        fetchedUser.setUsername(newUsername);
        userRepository.save(fetchedUser);
        assertNotNull(userRepository.findByUsername(newUsername));
        fetchedUser.setUsername(username);
        userRepository.save(fetchedUser);
        assertNotNull(userRepository.findByUsername(username));
    }

    @Test
    public void testDeleteUser(){
        // delete testUser
        String username = "testUser";
        User user = userRepository.findByUsername(username);
        if(user == null) return;
        userRepository.delete(user);
        assertNull(userRepository.findByUsername(username));
        assertEquals(userRepository.findUserRoleByUserId(user.getId()).size(), 0);
        assertEquals(profileRepository.findByUser(user).size(), 0);
    }

    @Test
    public void deleteAllUsers(){
        userRepository.deleteAll();
        assertEquals(userRepository.findAll().size(), 0);
        assertEquals(profileRepository.findAll().size(), 0);
        assertEquals(entryRepository.findAll().size(), 0);
        assertEquals(entryRepository.findAllEntryFeatures().size(), 0);
        assertEquals(userRepository.findAllUserRoles().size(), 0);
    }

    @Test
    public void testUpdateEntry(){
        // update testUser and change it back
        User user = userRepository.findByUsername("testUser");
        Profile profile = profileRepository.findByProfileNameAndUser("testProfile", user);
        String entryName = "testEntry";
        String newEntryName = "newEntry";
        Entry fetchedEntry = entryRepository.findByEntryNameAndProfile(entryName, profile);
        if(fetchedEntry == null) return;

        entryRepository.updateEntryName(fetchedEntry.getId(), newEntryName);
        assertNotNull(entryRepository.findByEntryNameAndProfile(newEntryName, profile));
        entryRepository.updateEntryName(fetchedEntry.getId(), entryName);
        assertNotNull(entryRepository.findByEntryNameAndProfile(entryName, profile));
    }

    @Test
    public void testDeleteEntry(){
        User user = userRepository.findByUsername("testUser");
        Profile profile = profileRepository.findByProfileNameAndUser("testProfile", user);
        String entryName = "testEntry";
        Entry entry = entryRepository.findByEntryNameAndProfile(entryName, profile);
        if(entry == null) return;
        entryRepository.delete(entry.getId());
        assertNull(entryRepository.findByEntryNameAndProfile(entryName, profile));
        assertEquals(entryRepository.findEntryFeatureByEntryId(entry.getId()).size(), 0);
    }

    @Test
    public void deleteAllEntries(){
        entryRepository.deleteAll();
        assertEquals(entryRepository.findAll().size(), 0);
        assertEquals(entryRepository.findAllEntryFeatures().size(), 0);
    }

    @Test
    public void testUpdateFeature(){
        // update testUser and change it back
        User user = userRepository.findByUsername("testUser");
        Profile profile = profileRepository.findByProfileNameAndUser("testProfile", user);
        String featureName = "testFeature";
        String newFeatureName = "newFeature";
        Feature fetchedFeature = featureRepository.findByFeatureNameAndProfile(featureName,  profile);
        if(fetchedFeature == null) return;
        fetchedFeature.setFeatureName(newFeatureName);
        featureRepository.save(fetchedFeature);
        assertNotNull(featureRepository.findByFeatureNameAndProfile(newFeatureName, profile));
        fetchedFeature.setFeatureName(featureName);
        featureRepository.save(fetchedFeature);
        assertNotNull(featureRepository.findByFeatureNameAndProfile(featureName,  profile));
    }

    @Test
    public void testDeleteFeature(){
        User user = userRepository.findByUsername("testUser");
        Profile profile = profileRepository.findByProfileNameAndUser("testProfile", user);
        String featureName = "testFeature";
        Feature feature = featureRepository.findByFeatureNameAndProfile(featureName,  profile);
        if(feature == null) return;
        featureRepository.delete(feature.getId());
        assertNull(featureRepository.findByFeatureNameAndProfile(featureName,  profile));
        assertEquals(featureRepository.findEntryFeatureByFeatureId(feature.getId()).size(), 0);

    }

    @Test
    public void deleteAllFeatures(){
        featureRepository.deleteAll();
        assertEquals(featureRepository.findAll().size(), 0);
        assertEquals(entryRepository.findAllEntryFeatures().size(), 0);
    }

    @Test
    public void testSaveUserRole(){
        String username = "testUser";
        String roleName = "USER";
        User user = userRepository.findByUsername(username);
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
        String username = "testUser";
        String roleName = "USER";
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByRoleName(roleName);
        if(user == null || role == null) return;
        if(!userRepository.findUserRoleByUserId(user.getId()).contains(role.getId())) return;
        userRepository.deleteUserRoleById(user.getId(), role.getId());
        assertFalse(userRepository.findUserRoleByUserId(user.getId()).contains(role.getId()));
    }

    @Test
    public void testSaveEntryFeature() {
        //Already done in testSave
    }

    @Test
    public void testUpdateEntryFeature(){
        User user = userRepository.findByUsername("testUser");
        Profile profile = profileRepository.findByProfileNameAndUser("testProfile", user);
        String featureValue = "testFeatureValue";
        String newFeatureValue = "newFeatureValue";
        String entryName = "testEntry";
        String featureName = "testFeature";

        Entry entry = entryRepository.findByEntryNameAndProfile(entryName, profile);
        Feature feature = featureRepository.findByFeatureNameAndProfile(featureName,  profile);
        if(entry == null || feature == null) return;

        if(entryRepository.findEntryFeatureById(entry.getId(), feature.getId()) == null) return;
        entryRepository.updateEntryFeatureByFeatureValue(entry.getId(), feature.getId(), newFeatureValue);
        assertEquals(entryRepository.findEntryFeatureById(entry.getId(), feature.getId()), newFeatureValue);
        entryRepository.updateEntryFeatureByFeatureValue(entry.getId(), feature.getId(), featureValue);
        assertEquals(entryRepository.findEntryFeatureById(entry.getId(), feature.getId()), featureValue);
    }

    @Test
    public void testDeleteEntryFeature(){
        User user = userRepository.findByUsername("testUser");
        Profile profile = profileRepository.findByProfileNameAndUser("testProfile", user);
        String entryName = "testEntry";
        String featureName = "testFeature";
        Entry entry = entryRepository.findByEntryNameAndProfile(entryName, profile);
        Feature feature = featureRepository.findByFeatureNameAndProfile(featureName,  profile);
        if(entry == null || feature == null) return;
        if(entryRepository.findEntryFeatureById(entry.getId(), feature.getId()) == null) return;
        entryRepository.deleteEntryFeatureById(entry.getId(), feature.getId());
        assertNull(entryRepository.findEntryFeatureById(entry.getId(), feature.getId()));
    }

}
