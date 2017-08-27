package tk.kaicheng.models;

import javax.persistence.*;

@Entity
@Table(name = "role") // role_id, role_name
public class Role {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="role_id")
	private Integer id;

	@Column(name="role_name", unique = true)
	private String roleName;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
