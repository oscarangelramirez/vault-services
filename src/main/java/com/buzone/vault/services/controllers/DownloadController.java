package com.buzone.vault.services.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buzone.vault.services.application.download.services.DownloadService;
import com.buzone.vault.services.application.exceptions.ErrorException;
import com.buzone.vault.services.data.download.AddCfdiDownloadRequest;
import com.buzone.vault.services.data.download.AddDownloadResponse;
import com.buzone.vault.services.data.download.AddPaysheetDownloadRequest;
import com.buzone.vault.services.data.download.GetDownloadResponse;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class DownloadController {
	@Autowired
    private DownloadService downloadService;
	
	@GetMapping("{idUser}/downloads")
	public ResponseEntity<List<GetDownloadResponse>> findAll(@PathVariable Long idUser) {
		List<GetDownloadResponse> response = downloadService.findAll(idUser);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("{idUser}/downloads/cfdis")
    public ResponseEntity<AddDownloadResponse> addCfdis(
    		@PathVariable Long idUser,
    		@Valid @RequestBody AddCfdiDownloadRequest request) {
		try {
			AddDownloadResponse response = downloadService.add(idUser);
			downloadService.generateCfdiDownload(response, request);
			return ResponseEntity.ok(response);
		}catch(ErrorException exception) {
			throw exception;
		}
    }
	
	@PostMapping("{idUser}/downloads/paysheets")
    public ResponseEntity<AddDownloadResponse> addPaysheets(
    		@PathVariable Long idUser,
    		@Valid @RequestBody AddPaysheetDownloadRequest request) {
		try {
			AddDownloadResponse response = downloadService.add(idUser);
			downloadService.generatePaysheetDownload(response, request);
			return ResponseEntity.ok(response);
		}catch(ErrorException exception) {
			throw exception;
		}
    }
	
	@GetMapping("{idUser}/downloads/{id}/download")
	public ResponseEntity<InputStreamResource> download(
			@PathVariable Long idUser,
			@PathVariable Long id) {
		try {
			GetDownloadResponse response = downloadService.findById(idUser, id);
			
			File file = new File(response.getPathFile());
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			
		    HttpHeaders headers = new HttpHeaders();
		    headers.add("Content-Disposition", "attachment;filename=" + response.getName());

			return ResponseEntity.ok()
		            .headers(headers)
		            .contentType(MediaType.APPLICATION_OCTET_STREAM)
		            .body(resource);
			
		} catch (Exception exception) {
			return ResponseEntity.badRequest().build();
		}
	}
}
