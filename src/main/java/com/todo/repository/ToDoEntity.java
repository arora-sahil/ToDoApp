package com.todo.repository;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name="TD_TASK")
public class ToDoEntity {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int taskId;
	
	@Column(name="taskName")
	private String taskName;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createdAt")
    private Date createdAt ;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modifyDate")
	private Date modifyDate;

	@Column(name="taskPriority")
	private String taskPriority;
	
	@Column(name="taskStatus")
	private String taskStatus;
	
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskPriority() {
		return taskPriority;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public void setTaskPriority(String taskPriority) {
		this.taskPriority = taskPriority;
	}

}
