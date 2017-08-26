package tk.kaicheng.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tk.kaicheng.models.Entry;
import tk.kaicheng.models.Profile;
import tk.kaicheng.models.User;

import java.util.List;

/**
 * Created by wangkaicheng on 2017/8/26.
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Profile findByProfileNameAndUser(String profileName, User user);
    List<Profile> findByUser(User user);

    @Query(value = "update profile set profile_name = ?2 where profile_id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void updateProfileName(Integer profile_id, String newProfileName);
}
