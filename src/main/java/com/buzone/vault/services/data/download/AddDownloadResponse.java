package com.buzone.vault.services.data.download;

import com.buzone.vault.services.domain.download.aggregates.Download;
import com.fasterxml.jackson.annotation.JsonView;

public class AddDownloadResponse {
	@JsonView
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AddDownloadResponse(Download download) {
		id = download.getId();
	}
}
