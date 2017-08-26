package tk.kaicheng.services;


import org.springframework.stereotype.Service;
import tk.kaicheng.models.Entry;
import tk.kaicheng.models.Profile;
import tk.kaicheng.models.Profile;
import tk.kaicheng.models.User;

import java.util.List;

/**
 * Created by wangkaicheng on 2017/8/20.
 */

public interface ProfileService {
    void save(Profile profile);

    void deleteAll();

    void delete(Integer id);

    void delete(Profile profile);

    List <Profile> findAll();

    Profile findOne(Integer id);

    Profile findByProfileNameAndUser(String profileName, User user);

    List<Profile> findByUser(User user);

    void updateProfileName(Integer profile_id, String newProfileName);
}
