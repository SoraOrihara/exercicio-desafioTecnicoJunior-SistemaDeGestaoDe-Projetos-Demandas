package com.Estudo.DesafioTecnico.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.Estudo.DesafioTecnico.model.enums.EnumPriority;
import com.Estudo.DesafioTecnico.model.enums.EnumStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "task_tb")
public class TaskModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID taskId;

	private String title;

	private String description;

	@Enumerated(EnumType.STRING)
	private EnumStatus status;

	@Enumerated(EnumType.STRING)
	private EnumPriority priority;

	private Date dueDate;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "projectId", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private ProjectModel project;

	public TaskModel() {

	}

	public TaskModel(String title, String description, EnumStatus status, EnumPriority priority, Date dueDate,
			ProjectModel project) {
		this.title = title;
		this.description = description;
		this.status = status;
		this.priority = priority;
		this.dueDate = dueDate;
		this.project = project;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public EnumStatus getStatus() {
		return status;
	}

	public void setStatus(EnumStatus status) {
		this.status = status;
	}

	public EnumPriority getPriority() {
		return priority;
	}

	public void setPriority(EnumPriority priority) {
		this.priority = priority;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public ProjectModel getProject() {
		return project;
	}

	public void setProject(ProjectModel project) {
		this.project = project;
	}

	public UUID getTaskId() {
		return taskId;
	}

}
