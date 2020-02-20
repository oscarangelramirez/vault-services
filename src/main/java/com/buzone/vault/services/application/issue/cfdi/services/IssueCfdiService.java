package com.buzone.vault.services.application.issue.cfdi.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.buzone.vault.services.data.cfdi.GetIssueCfdiResponse;
import com.buzone.vault.services.data.cfdi.GetPagedIssueCfdiRequest;
import com.buzone.vault.services.domain.issue.cfdi.aggregates.IssueCfdi;
import com.buzone.vault.services.domain.issue.cfdi.aggregates.IssueCfdiRepository;
import com.buzone.vault.services.domain.issue.cfdi.aggregates.IssueCfdiSpecification;
import com.buzone.vault.services.domain.specifications.SearchCriteria;
import com.buzone.vault.services.domain.specifications.SearchOperation;

@Service
public class IssueCfdiService {
	@Autowired
	private IssueCfdiRepository issueCfdiRepository;

	public List<GetIssueCfdiResponse> findAll() {
		List<IssueCfdi> issueCFDIs = issueCfdiRepository.findAll();

		return issueCFDIs.stream().map(issueCFDI -> new GetIssueCfdiResponse(issueCFDI)).collect(Collectors.toList());
	}

	public Page<GetIssueCfdiResponse> findPaginated(Pageable pageable, GetPagedIssueCfdiRequest request) {
		IssueCfdiSpecification issueCfdiSpecification = new IssueCfdiSpecification();

		if (request.getIdStateSAT().isPresent()) {
			issueCfdiSpecification
					.add(new SearchCriteria("idStateSAT", request.getIdStateSAT().get(), SearchOperation.EQUAL));
		}
		if (request.getVersion().isPresent() &&  !"".equals(request.getVersion().get())) {
			issueCfdiSpecification
					.add(new SearchCriteria("version", request.getVersion().get(), SearchOperation.EQUAL));
		}
		if (request.getIssueDateFrom().isPresent()) {
			issueCfdiSpecification
					.add(new SearchCriteria("issueDate", request.getIssueDateFrom().get(), SearchOperation.GREATER_THAN_EQUAL));
		}
		if (request.getIssueDateTo().isPresent()) {
			issueCfdiSpecification
					.add(new SearchCriteria("issueDate", request.getIssueDateTo().get(), SearchOperation.LESS_THAN_EQUAL));
		}
		if (request.getRingingDateFrom().isPresent()) {
			issueCfdiSpecification
					.add(new SearchCriteria("ringingDate", request.getRingingDateFrom().get(), SearchOperation.GREATER_THAN_EQUAL));
		}
		if (request.getRingingDateTo().isPresent()) {
			issueCfdiSpecification
					.add(new SearchCriteria("ringingDate", request.getRingingDateTo().get(), SearchOperation.LESS_THAN_EQUAL));
		}
		if (request.getUuid().isPresent() &&  !"".equals(request.getUuid().get())) {
			issueCfdiSpecification
					.add(new SearchCriteria("uuid", UUID.fromString(request.getUuid().get()), SearchOperation.EQUAL));
		}
		if (request.getIssueRfc().isPresent() &&  !"".equals(request.getIssueRfc().get())) {
			issueCfdiSpecification
					.add(new SearchCriteria("issueRfc", request.getIssueRfc().get(), SearchOperation.EQUAL));
		}
		if (request.getIssue().isPresent() &&  !"".equals(request.getIssue().get())) {
			issueCfdiSpecification
					.add(new SearchCriteria("issue", request.getIssue().get(), SearchOperation.EQUAL));
		}
		if (request.getExpeditionPlace().isPresent() &&  !"".equals(request.getExpeditionPlace().get())) {
			issueCfdiSpecification
					.add(new SearchCriteria("expeditionPlace", request.getExpeditionPlace().get(), SearchOperation.EQUAL));
		}
		if (request.getReceiverRfc().isPresent() &&  !"".equals(request.getReceiverRfc().get())) {
			issueCfdiSpecification
					.add(new SearchCriteria("receiverRfc", request.getReceiverRfc().get(), SearchOperation.EQUAL));
		}
		if (request.getReceiver().isPresent() &&  !"".equals(request.getReceiver().get())) {
			issueCfdiSpecification
					.add(new SearchCriteria("receiver", request.getReceiver().get(), SearchOperation.EQUAL));
		}
		if (request.getUse().isPresent() && !"".equals(request.getUse().get())) {
			issueCfdiSpecification
					.add(new SearchCriteria("use", request.getUse().get(), SearchOperation.EQUAL));
		}
		if (request.getTotal().isPresent()) {
			issueCfdiSpecification
					.add(new SearchCriteria("total", request.getTotal().get(), SearchOperation.EQUAL));
		}
		if (request.getCurrency().isPresent() &&  !"".equals(request.getCurrency().get())) {
			issueCfdiSpecification
					.add(new SearchCriteria("currency", request.getCurrency().get(), SearchOperation.EQUAL));
		}

		Page<IssueCfdi> issueCFDIs = issueCfdiRepository.findAll(issueCfdiSpecification, pageable);

		Page<GetIssueCfdiResponse> pageIssueCFDIs = new PageImpl<GetIssueCfdiResponse>(
				issueCFDIs.stream().map(issueCFDI -> new GetIssueCfdiResponse(issueCFDI)).collect(Collectors.toList()),
				issueCFDIs.getPageable(), issueCFDIs.getTotalElements());

		return pageIssueCFDIs;
	}
}
