package com.juan.task.domain.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@NamedQueries({
	@NamedQuery(name="TASK_LISTBYUSER", query="select t from Task t where t.user.id = :userId"),
	@NamedQuery(name="TASK_LISTPENDINGPASTDATETIME", query="select t from Task t where t.status = 'pending' and t.dateTime < :dateTime"
)})
@Entity
@Table(name="task")
public class Task extends BaseEntity {
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	private User user;
	
	private String name;
	
	private String description;
	
	private String status;
	
	@Temporal(TemporalType.DATE)
	@JsonProperty("date_time")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	@Column//(updatable=false, insertable=false)
	private Date dateTime;
	
	protected Task() {
		
	}
	
	public Task(String name, String description, Date dateTime, String status) {
		super();
		this.name = name;
		this.description = description;
		this.dateTime = dateTime;
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	 @PrePersist
	 protected void prePersist() {
		 if(Objects.isNull(status)) {
			 status = "pending";
		 }
		 
		 if(Objects.isNull(dateTime)) {
			 dateTime = new Date();
		 }
	 }

	@Override
	public String toString() {
		return "Task [name=" + name + ", description=" + description + ", status=" + status + ", dateTime=" + dateTime + "]";
	}
}
