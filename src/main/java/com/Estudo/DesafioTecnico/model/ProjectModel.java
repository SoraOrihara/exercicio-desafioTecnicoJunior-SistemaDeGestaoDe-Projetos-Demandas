package com.Estudo.DesafioTecnico.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "project_tb")
public class ProjectModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID projectId;
	
	@Column(name="name",nullable=false)
	private String name;
	@Column(name="description")
	private String description;
	@Column(name="startDate",nullable=false)
	private LocalDate startDate;
	@Column(name="endDate")
	private LocalDate endDate;
	
	@OneToMany(mappedBy="project")
	private List<TaskModel> tasks=new ArrayList<>();
	
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "project_members", // Nome da tabela intermediária (ex: project_members)
        joinColumns = @JoinColumn(name = "project_id"), // Coluna que referencia ProjectModel nesta tabela
        inverseJoinColumns = @JoinColumn(name = "user_id") // Coluna que referencia UserEntity nesta tabela
    )
    private Set<UserModel> members = new HashSet<>(); // Usamos Set para garantir que não haja membros duplicados
	
	public ProjectModel() {
		
	}
	
	public ProjectModel(String name, String description, LocalDate startDate, LocalDate endDate, List<TaskModel> tasks) {
		super();
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.tasks = tasks;
	}

	public Set<UserModel> getMembers() {
		return members;
	}

	public void setMembers(Set<UserModel> members) {
		this.members = members;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public UUID getProjectId() {
		return projectId;
	}
	public List<TaskModel> getTasks() {
		return tasks;
	}
	public void setTasks(List<TaskModel> tasks) {
		this.tasks = tasks;
	}
	
	
	
	
}
