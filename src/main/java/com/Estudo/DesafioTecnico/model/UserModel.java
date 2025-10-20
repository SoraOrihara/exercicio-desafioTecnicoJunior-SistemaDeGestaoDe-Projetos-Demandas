package com.Estudo.DesafioTecnico.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "users")
public class UserModel implements UserDetails,Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID userId;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER) // Geralmente buscamos os roles ansiosamente (EAGER)
    @JoinTable(
        name = "user_roles_link", // Nome da tabela intermediária
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
	@Column(name = "role")
	private Set<RoleModel>roles = new HashSet<>();
	
	// mappedBy = "members" diz ao JPA que ProjectModel já está controlando a relação
    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    private Set<ProjectModel> assignedProjects = new HashSet<>();
    
    
	public Set<ProjectModel> getAssignedProjects() {
		return assignedProjects;
	}

	public void setAssignedProjects(Set<ProjectModel> assignedProjects) {
		this.assignedProjects = assignedProjects;
	}

	public Set<RoleModel> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleModel> roles) {
		this.roles = roles;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserModel() {
	}

	public UserModel(String username, String password, Set<RoleModel> roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	// -----------------------------------------------------
    // 4. IMPLEMENTAÇÃO DO BUILDER (Substitui @Builder)
    // -----------------------------------------------------
    public static UserEntityBuilder builder() {
        return new UserEntityBuilder();
    }
    
    public static class UserEntityBuilder {
       
        private String username;
        private String password;
        private Set<RoleModel> roles = new HashSet<>();

       
        public UserEntityBuilder username(String username) {
            this.username = username;
            return this;
        }
        public UserEntityBuilder password(String password) {
            this.password = password;
            return this;
        }
        public UserEntityBuilder roles(Set<RoleModel> roles) {
            this.roles = roles;
            return this;
        }

        public UserModel build() {
            return new UserModel( username, password, roles);
        }
    }
	/**
     * Retorna a coleção de autoridades (perfis/roles) concedidas ao usuário.
     * Necessário para verificar a autorização (Ex: hasRole('ADMIN')).
     */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(roles==null) {
			
			return List.of();
		}
		//Converte lista de userRole para o formato que o Spring Security entende
		return this.roles.stream().map(role->(GrantedAuthority)()->role.getRoleName().name()).toList();
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}
	/** Indica se a conta do usuário não está expirada (True = válida). */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /** Indica se o usuário não está bloqueado (True = não bloqueado). */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /** Indica se as credenciais do usuário (senha) não expiraram (True = válidas). */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /** Indica se o usuário está habilitado (True = pode logar). */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
