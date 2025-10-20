package com.Estudo.DesafioTecnico.model;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import com.Estudo.DesafioTecnico.model.enums.RoleName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class RoleModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID roleId;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, unique = true)
	private RoleName roleName;
	
	// Mapeamento Inverso: Um Perfil pode ser atribuído a Vários Usuários
    // mappedBy aponta para o campo "roles" na classe UserEntity (Onde a relação será definida)
    @ManyToMany(mappedBy = "roles") 
    private Set<UserModel> users; // Usamos Set para evitar duplicidade e garantir performance
	
	

	public UUID getRoleId() {
		return roleId;
	}
	
	public void setRoleId(UUID roleId) {
		this.roleId = roleId;
	}

	public RoleName getRoleName() {
		return roleName;
	}

	public void setRoleName(RoleName roleName) {
		this.roleName = roleName;
	}

	public Set<UserModel> getUsers() {
		return users;
	}

	public void setUsers(Set<UserModel> users) {
		this.users = users;
	}

	public RoleModel() {
	}

	public RoleModel(UUID roleId, RoleName roleName, Set<UserModel> users) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.users = users;
	}

	

}
