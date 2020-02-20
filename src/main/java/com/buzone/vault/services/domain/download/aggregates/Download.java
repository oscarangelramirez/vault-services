package com.buzone.vault.services.domain.download.aggregates;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.buzone.vault.services.domain.user.aggregates.User;

@Entity
@Table(name = "Downloads", schema = "interjet")
public class Download {
	public enum Status{
		PROCESSING,
		GENERATED
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique=true, nullable = false)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="pathFile")
	private String pathFile;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="beginDate")
	private Date beginDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="endDate")
	private Date endDate;
	
	@Column(name="status")
	private Status status;
	
	@ManyToOne
    @JoinColumn(name="user_id", nullable=false)
	private User user;
	
	public Download() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
