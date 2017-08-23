package tk.kaicheng.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.kaicheng.models.Profile;
import tk.kaicheng.repositories.ProfileRepository;

/**
 * Created by wangkaicheng on 2017/8/2.
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Profile save(Profile profile){
        return profileRepository.save(profile);
    }

    @Override
    public void deleteAll(){
        profileRepository.deleteAll();
    }

    @Override
    public void delete(Integer id){
        profileRepository.delete(id);
    }

    @Override
    public void delete(Profile profile){
        profileRepository.delete(profile);
    }

    @Override
    public Iterable <Profile> findAll(){
        return profileRepository.findAll();
    }

    @Override
    public Profile findOne(Integer id){
        return profileRepository.findOne(id);
    }

}
