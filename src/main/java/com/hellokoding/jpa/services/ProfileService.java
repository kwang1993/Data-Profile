package com.hellokoding.jpa.services;

import com.hellokoding.jpa.models.Profile;
import com.hellokoding.jpa.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangkaicheng on 2017/8/2.
 */
@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public Profile save(Profile profile){
        return profileRepository.save(profile);
    }

    public void deleteAll(){
        profileRepository.deleteAll();
    }

    public void delete(Integer id){
        profileRepository.delete(id);
    }

    public void delete(Profile profile){
        profileRepository.delete(profile);
    }

    public Iterable <Profile> findAll(){
        return profileRepository.findAll();
    }

    public Profile findOne(Integer id){
        return profileRepository.findOne(id);
    }

}
