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

    User findByUsername(String username);

    List<Object> findUserRoleByUserId(Integer userId);

    List<Object[]> findAllUserRoles();

    void deleteUserRoleById(Integer userId, Integer roleId);

    void saveUserRole(Integer userId, Integer roleId);
}
