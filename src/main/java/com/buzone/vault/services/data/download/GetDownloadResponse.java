package com.buzone.vault.services.data.download;

import java.util.Date;

import com.buzone.vault.services.domain.download.aggregates.Download;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

public class GetDownloadResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private String name;
	
	@JsonView
	private String pathFile;
	
	@JsonView
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date beginDate;
	
	@JsonView
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date endDate;
	
	@JsonView
	private Integer status;
	
	public GetDownloadResponse(Download download) {
		id = download.getId();
		name = download.getName();
		pathFile = download.getPathFile();
		beginDate = download.getBeginDate();
		endDate = download.getEndDate();
		status = download.getStatus().ordinal();
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
