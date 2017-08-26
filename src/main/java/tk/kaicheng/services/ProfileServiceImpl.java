package tk.kaicheng.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.kaicheng.models.Entry;
import tk.kaicheng.models.Profile;
import tk.kaicheng.models.User;
import tk.kaicheng.repositories.ProfileRepository;

import java.util.List;

/**
 * Created by wangkaicheng on 2017/8/26.
 */
@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Override
    public void save(Profile profile) {
       profileRepository.save(profile);
    }

    @Override
    public void deleteAll() {
        profileRepository.deleteAll();
    }

    @Override
    public void delete(Integer id) {
        profileRepository.delete(id) ;
    }

    @Override
    public void delete(Profile profile) {
        profileRepository.delete(profile) ;
    }

    @Override
    public List<Profile> findAll() {
        return profileRepository.findAll() ;
    }

    @Override
    public Profile findOne(Integer id) {
        return profileRepository.findOne(id);
    }

    @Override
    public Profile findByProfileNameAndUser(String profileName, User user) {
        return profileRepository.findByProfileNameAndUser(profileName, user);
    }

    @Override
    public List<Profile> findByUser(User user) {
        return profileRepository.findByUser(user);
    }

    @Override
    public void updateProfileName(Integer profile_id, String newProfileName) {
        profileRepository.updateProfileName(profile_id, newProfileName);
    }
}
