package tk.kaicheng.services;


import tk.kaicheng.models.Profile;

/**
 * Created by wangkaicheng on 2017/8/20.
 */
public interface ProfileService {
    Profile save(Profile profile);

    void deleteAll();

    void delete(Integer id);

    void delete(Profile profile);

    Iterable <Profile> findAll();

    Profile findOne(Integer id);
}
