package tk.kaicheng.services;


import tk.kaicheng.models.User;

/**
 * Created by wangkaicheng on 2017/8/20.
 */
public interface UserService {
    void save(User user);

    void deleteAll();

    void delete(Integer id);

    void delete(User user);

    Iterable <User> findAll();

    User findOne(Integer id);

    User findByUserName(String userName);
}
