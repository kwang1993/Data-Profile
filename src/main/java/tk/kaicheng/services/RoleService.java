package tk.kaicheng.services;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import tk.kaicheng.models.Role;
import tk.kaicheng.models.User;

import java.util.List;

/**
 * Created by wangkaicheng on 2017/8/23.
 */
@Service
public interface RoleService {
    void save(Role role);

    void deleteAll();

    void delete(Integer id);

    void delete(Role role);

    Iterable <Role> findAll();

    Role findOne(Integer id);

    Role findByRoleName(String role);

    public List<Object> findUserRoleByRoleId(int role_id);
}
