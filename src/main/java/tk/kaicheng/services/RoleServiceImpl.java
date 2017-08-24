package tk.kaicheng.services;

import org.springframework.beans.factory.annotation.Autowired;
import tk.kaicheng.models.Role;
import tk.kaicheng.models.User;
import tk.kaicheng.repositories.RoleRepository;

import java.util.List;

/**
 * Created by wangkaicheng on 2017/8/23.
 */
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void deleteAll() {
        roleRepository.deleteAll();
    }

    @Override
    public void delete(Integer id) {
        roleRepository.delete(id);
    }

    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }

    @Override
    public Iterable<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findOne(Integer id) {
        return roleRepository.findOne(id);
    }

    @Override
    public Role findByRoleName(String role) {
        return roleRepository.findByRoleName(role);
    }

    @Override
    public List<Object> findUserRoleByRoleId(int role_id) {
        return roleRepository.findUserRoleByRoleId(role_id);
    }
}
