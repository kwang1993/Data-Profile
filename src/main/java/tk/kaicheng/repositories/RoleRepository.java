package tk.kaicheng.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tk.kaicheng.models.Role;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByRoleName(String role);

	@Query(value = "select user_id from user_role where role_id = ?1", nativeQuery = true)
	public List<Object> findUserRoleByRoleId(int role_id);
}
