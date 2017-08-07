package com.hellokoding.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hellokoding.jpa.model.Profile;

/**
 * Created by wangkaicheng on 2017/8/1.
 */

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
