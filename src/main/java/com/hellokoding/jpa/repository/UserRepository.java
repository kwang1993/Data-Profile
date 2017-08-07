package com.hellokoding.jpa.repository;

import com.hellokoding.jpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wangkaicheng on 2017/8/4.
 */
public interface UserRepository extends JpaRepository<User, Integer>{
}
