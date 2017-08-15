package com.hellokoding.jpa.services;

import com.hellokoding.jpa.models.User;
import com.hellokoding.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;


/**
 * Created by wangkaicheng on 2017/8/2.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }

    public void deleteAll(){
        userRepository.deleteAll();
    }

    public void delete(Integer id){
        userRepository.delete(id);
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    public Iterable <User> findAll(){
        return userRepository.findAll();
    }

    public User findOne(Integer id){
        return userRepository.findOne(id);
    }

    public User findByUserNameAndPassword(String userName, String password){
        return userRepository.findByUserNameAndPassword(userName, password);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public boolean isValidUser(User user){
        if(userRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword()) != null)
        //if(user.getUserName().equals("aaa") && user.getPassword().equals("bbb"))
            return true;
        else
            return false;
    }
}
