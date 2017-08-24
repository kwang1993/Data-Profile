package tk.kaicheng.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tk.kaicheng.models.User;

import java.util.List;

/**
 * Created by wangkaicheng on 2017/8/4.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String userName);

    @Query(value = "select role_id from user_role where user_id = ?1", nativeQuery = true)
    public List<Object> findUserRoleByUserId(int user_id);

    @Query(value = "select user_id, role_id from user_role", nativeQuery = true)
    public List<Object[]> findAllUserRoles();

    @Query(value = "delete from user_role where user_id = ?1 and role_id = ?2", nativeQuery = true)
    @Modifying
    @Transactional
    public void deleteUserRoleById(int user_id, int role_id);

    @Query(value = "insert into user_role values(?1, ?2)", nativeQuery = true)
    @Modifying
    @Transactional
    public void saveUserRole(int user_id, int role_id);
}
