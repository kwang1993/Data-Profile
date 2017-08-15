package com.hellokoding.jpa.repositories;

import com.hellokoding.jpa.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Iterator;

/**
 * Created by wangkaicheng on 2017/8/4.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    User findByUserNameAndPassword(String userName, String password);
    User findByUserName(String userName);
}
