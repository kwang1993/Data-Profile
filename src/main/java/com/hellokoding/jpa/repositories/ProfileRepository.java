package com.hellokoding.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hellokoding.jpa.models.Profile;
import org.springframework.stereotype.Repository;

/**
 * Created by wangkaicheng on 2017/8/1.
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
