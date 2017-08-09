package com.hellokoding.jpa.repositories;

import com.hellokoding.jpa.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by wangkaicheng on 2017/8/4.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    
}
