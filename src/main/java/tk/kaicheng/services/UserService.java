package tk.kaicheng.services;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import tk.kaicheng.models.User;

import java.util.List;

/**
 * Created by wangkaicheng on 2017/8/20.
 */
public interface UserService {
    void save(User user);

    void deleteAll();

    void delete(Integer id);

    void delete(User user);

    List <User> findAll();

    User findOne(Integer id);

    User findByUserName(String userName);

    public List<Object> findUserRoleByUserId(int user_id);

    public List<Object[]> findAllUserRoles();

    public void deleteUserRoleById(int user_id, int role_id);

    public void saveUserRole(int user_id, int role_id);
}
