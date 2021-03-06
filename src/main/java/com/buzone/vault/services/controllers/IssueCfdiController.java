package com.buzone.vault.services.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buzone.vault.services.application.issue.cfdi.services.IssueCfdiService;
import com.buzone.vault.services.data.cfdi.GetIssueCfdiResponse;
import com.buzone.vault.services.data.cfdi.GetPagedIssueCfdiRequest;

@RestController
@RequestMapping("/api/v1/issues/cfdis")
@CrossOrigin(origins = "*")
public class IssueCfdiController {
	@Autowired
	private IssueCfdiService issueCfdiService;
	
	@GetMapping("")
    public ResponseEntity<List<GetIssueCfdiResponse>> findAll() {
		List<GetIssueCfdiResponse> response = issueCfdiService.findAll();
		return ResponseEntity.ok(response);
    }
	
	@PostMapping("paged")
    public ResponseEntity<Page<GetIssueCfdiResponse>> findPaginated(
    		@RequestParam(value = "size", required = false) Optional<Integer> pageSize,
    		@RequestParam(value = "page", required = false) Optional<Integer> pageNumber,
    		@Valid @RequestBody GetPagedIssueCfdiRequest request) {
		PageRequest pageable = PageRequest.of(pageNumber.orElse(0), pageSize.orElse(10));
		Page<GetIssueCfdiResponse> response = issueCfdiService.findPaginated(pageable, request);
		return ResponseEntity.ok(response);
    }
}
