package com.buzone.vault.services.domain.user.aggregates;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.buzone.vault.services.domain.download.aggregates.Download;

@Entity
@Table(name = "Users", schema = "interjet")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique=true, nullable = false)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="lastName")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Temporal(TemporalType.DATE)
	@Column(name="lastChangeDate")
	private Date lastChangeDate;
	
	@Column(name="numberAttemps")
	private Integer numberAttemps;
	
	@Column(name="isLocked")
	private Boolean isLocked;
	
	@Column(name="isActive")
	private Boolean isActive;
	
	@Column(name="role")
	private String role;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="user")
	private Set<Download> downloads;
	
	public User() {
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastChangeDate() {
		return lastChangeDate;
	}

	public void setLastChangeDate(Date lastChangeDate) {
		this.lastChangeDate = lastChangeDate;
	}

	public Integer getNumberAttemps() {
		return numberAttemps;
	}

	public void setNumberAttemps(Integer numberAttemps) {
		this.numberAttemps = numberAttemps;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<Download> getDownloads() {
		return downloads;
	}

	public void setDownloads(Set<Download> downloads) {
		this.downloads = downloads;
	}
	
	public void addDownload(Download download) {
		downloads.add(download);
	}
}
