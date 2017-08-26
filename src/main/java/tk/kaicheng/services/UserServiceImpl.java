package tk.kaicheng.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tk.kaicheng.models.Role;
import tk.kaicheng.models.User;
import tk.kaicheng.repositories.RoleRepository;
import tk.kaicheng.repositories.UserRepository;

import java.util.*;

/**
 * Created by wangkaicheng on 2017/8/2.
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRoleName("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public void deleteAll(){
        userRepository.deleteAll();
    }

    @Override
    public void delete(Integer id){
        userRepository.delete(id);
    }

    @Override
    public void delete(User user){
        userRepository.delete(user);
    }

    @Override
    public List <User> findAll(){
        return userRepository.findAll();
    }

    @Override
    public User findOne(Integer id){
        return userRepository.findOne(id);
    }

    @Override
    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<Object> findUserRoleByUserId(int user_id) {
        return userRepository.findUserRoleByUserId( user_id);
    }

    @Override
    public List<Object[]> findAllUserRoles() {
        return userRepository.findAllUserRoles();
    }

    @Override
    public void deleteUserRoleById(int user_id, int role_id) {
        userRepository.deleteUserRoleById(user_id, role_id);
    }

    @Override
    public void saveUserRole(int user_id, int role_id) {
        userRepository.saveUserRole(user_id, role_id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user == null) throw new UsernameNotFoundException("User name not found");
        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);
    }
    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles);
        return grantedAuthorities;
    }
    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }
}
