package com.buzone.vault.services.application.issue.paysheet.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.buzone.vault.services.data.paysheet.GetIssuePaySheetResponse;
import com.buzone.vault.services.data.paysheet.GetPagedIssuePaySheetRequest;
import com.buzone.vault.services.domain.issue.paysheet.aggregates.IssuePaySheet;
import com.buzone.vault.services.domain.issue.paysheet.aggregates.IssuePaySheetRepository;
import com.buzone.vault.services.domain.issue.paysheet.aggregates.IssuePaySheetSpecification;
import com.buzone.vault.services.domain.specifications.SearchCriteria;
import com.buzone.vault.services.domain.specifications.SearchOperation;

@Service
public class IssuePaySheetService {
	@Autowired
	private IssuePaySheetRepository issuePaySheetRepository;

	public List<GetIssuePaySheetResponse> findAll() {
		List<IssuePaySheet> issuePaySheets = issuePaySheetRepository.findAll();

		return issuePaySheets.stream().map(issuePaySheet -> new GetIssuePaySheetResponse(issuePaySheet))
				.collect(Collectors.toList());
	}

	public Page<GetIssuePaySheetResponse> findPaginated(Pageable pageable, GetPagedIssuePaySheetRequest request) {
		IssuePaySheetSpecification issuePaySheetSpecification = new IssuePaySheetSpecification();

		if (request.getIdStateSAT().isPresent()) {
			issuePaySheetSpecification
					.add(new SearchCriteria("idStateSAT", request.getIdStateSAT().get(), SearchOperation.EQUAL));
		}
		if (request.getIssueDateFrom().isPresent()) {
			issuePaySheetSpecification.add(new SearchCriteria("issueDate", request.getIssueDateFrom().get(),
					SearchOperation.GREATER_THAN_EQUAL));
		}
		if (request.getIssueDateTo().isPresent()) {
			issuePaySheetSpecification.add(
					new SearchCriteria("issueDate", request.getIssueDateTo().get(), SearchOperation.LESS_THAN_EQUAL));
		}
		if (request.getUuid().isPresent() && !"".equals(request.getUuid().get())) {
			issuePaySheetSpecification
					.add(new SearchCriteria("uuid", UUID.fromString(request.getUuid().get()), SearchOperation.EQUAL));
		}
		if (request.getReceiverRfc().isPresent() && !"".equals(request.getReceiverRfc().get())) {
			issuePaySheetSpecification
					.add(new SearchCriteria("receiverRfc", request.getReceiverRfc().get(), SearchOperation.EQUAL));
		}
		if (request.getReceiver().isPresent() && !"".equals(request.getReceiver().get())) {
			issuePaySheetSpecification
					.add(new SearchCriteria("receiver", request.getReceiver().get(), SearchOperation.EQUAL));
		}
		if (request.getEmployerRegister().isPresent() && !"".equals(request.getEmployerRegister().get())) {
			issuePaySheetSpecification.add(
					new SearchCriteria("employerRegister", request.getEmployerRegister().get(), SearchOperation.EQUAL));
		}
		if (request.getType().isPresent() && !"".equals(request.getType().get())) {
			issuePaySheetSpecification.add(new SearchCriteria("type", request.getType().get(), SearchOperation.EQUAL));
		}
		if (request.getPaymentDateFrom().isPresent()) {
			issuePaySheetSpecification.add(new SearchCriteria("paymentDate", request.getPaymentDateFrom().get(),
					SearchOperation.GREATER_THAN_EQUAL));
		}
		if (request.getPaymentDateTo().isPresent()) {
			issuePaySheetSpecification.add(new SearchCriteria("paymentDate", request.getPaymentDateTo().get(),
					SearchOperation.LESS_THAN_EQUAL));
		}
		if (request.getTotal().isPresent()) {
			issuePaySheetSpecification
					.add(new SearchCriteria("total", request.getTotal().get(), SearchOperation.EQUAL));
		}
		if (request.getVersion().isPresent() && !"".equals(request.getVersion().get())) {
			issuePaySheetSpecification
					.add(new SearchCriteria("version", request.getVersion().get(), SearchOperation.EQUAL));
		}
		if (request.getCurrency().isPresent() && !"".equals(request.getCurrency().get())) {
			issuePaySheetSpecification
					.add(new SearchCriteria("currency", request.getCurrency().get(), SearchOperation.EQUAL));
		}
		if (request.getReceiverCurp().isPresent() && !"".equals(request.getReceiverCurp().get())) {
			issuePaySheetSpecification
					.add(new SearchCriteria("receiverCurp", request.getReceiverCurp().get(), SearchOperation.EQUAL));
		}
		if (request.getSocialSecurityNumber().isPresent() && !"".equals(request.getSocialSecurityNumber().get())) {
			issuePaySheetSpecification.add(new SearchCriteria("socialSecurityNumber",
					request.getSocialSecurityNumber().get(), SearchOperation.EQUAL));
		}
		if (request.getEmployeeNumber().isPresent() && !"".equals(request.getEmployeeNumber().get())) {
			String[] employeeNumbers = request.getEmployeeNumber().get().split("\\|");
			List<String> list = new ArrayList<String>();
			for (String employeeNumber : employeeNumbers) {
				list.add(employeeNumber.trim());
			}
			issuePaySheetSpecification.add(new SearchCriteria("employeeNumber", list, SearchOperation.IN));
		}
		if (request.getDepartment().isPresent() && !"".equals(request.getDepartment().get())) {
			issuePaySheetSpecification
					.add(new SearchCriteria("department", request.getDepartment().get(), SearchOperation.EQUAL));
		}
		if (request.getNumberDaysPaid().isPresent()) {
			issuePaySheetSpecification.add(
					new SearchCriteria("numberDaysPaid", request.getNumberDaysPaid().get(), SearchOperation.EQUAL));
		}

		Page<IssuePaySheet> issuePaySheets = issuePaySheetRepository.findAll(issuePaySheetSpecification, pageable);

		Page<GetIssuePaySheetResponse> pageIssuePaySheets = new PageImpl<GetIssuePaySheetResponse>(issuePaySheets
				.stream().map(issueCFDI -> new GetIssuePaySheetResponse(issueCFDI)).collect(Collectors.toList()),
				issuePaySheets.getPageable(), issuePaySheets.getTotalElements());

		return pageIssuePaySheets;
	}
}
