package com.buzone.vault.services.data.download;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AddPaysheetDownloadRequest {
	private List<Long> ids;
	
private Optional<Integer> idStateSAT;
	
	private Optional<Date> issueDateFrom;
	
	private Optional<Date> issueDateTo;
	
	private Optional<String> uuid;
	
	private Optional<String> receiverRfc;
	
	private Optional<String> receiver;
	
	private Optional<String> employerRegister;
	
	private Optional<String> type;
	
	private Optional<Date> paymentDateFrom;
	
	private Optional<Date> paymentDateTo;
	
	private Optional<BigDecimal> total;
	
	private Optional<String> version;
	
	private Optional<String> currency;
	
	private Optional<String> receiverCurp;
	
	private Optional<String> socialSecurityNumber;
	
	private Optional<String> employeeNumber;
	
	private Optional<String> department;
	
	private Optional<BigDecimal> numberDaysPaid;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Optional<Integer> getIdStateSAT() {
		return idStateSAT;
	}

	public void setIdStateSAT(Optional<Integer> idStateSAT) {
		this.idStateSAT = idStateSAT;
	}

	public Optional<Date> getIssueDateFrom() {
		return issueDateFrom;
	}

	public void setIssueDateFrom(Optional<Date> issueDateFrom) {
		this.issueDateFrom = issueDateFrom;
	}

	public Optional<Date> getIssueDateTo() {
		return issueDateTo;
	}

	public void setIssueDateTo(Optional<Date> issueDateTo) {
		this.issueDateTo = issueDateTo;
	}

	public Optional<String> getUuid() {
		return uuid;
	}

	public void setUuid(Optional<String> uuid) {
		this.uuid = uuid;
	}

	public Optional<String> getReceiverRfc() {
		return receiverRfc;
	}

	public void setReceiverRfc(Optional<String> receiverRfc) {
		this.receiverRfc = receiverRfc;
	}

	public Optional<String> getReceiver() {
		return receiver;
	}

	public void setReceiver(Optional<String> receiver) {
		this.receiver = receiver;
	}

	public Optional<String> getEmployerRegister() {
		return employerRegister;
	}

	public void setEmployerRegister(Optional<String> employerRegister) {
		this.employerRegister = employerRegister;
	}

	public Optional<String> getType() {
		return type;
	}

	public void setType(Optional<String> type) {
		this.type = type;
	}

	public Optional<Date> getPaymentDateFrom() {
		return paymentDateFrom;
	}

	public void setPaymentDateFrom(Optional<Date> paymentDateFrom) {
		this.paymentDateFrom = paymentDateFrom;
	}

	public Optional<Date> getPaymentDateTo() {
		return paymentDateTo;
	}

	public void setPaymentDateTo(Optional<Date> paymentDateTo) {
		this.paymentDateTo = paymentDateTo;
	}

	public Optional<BigDecimal> getTotal() {
		return total;
	}

	public void setTotal(Optional<BigDecimal> total) {
		this.total = total;
	}

	public Optional<String> getVersion() {
		return version;
	}

	public void setVersion(Optional<String> version) {
		this.version = version;
	}

	public Optional<String> getCurrency() {
		return currency;
	}

	public void setCurrency(Optional<String> currency) {
		this.currency = currency;
	}

	public Optional<String> getReceiverCurp() {
		return receiverCurp;
	}

	public void setReceiverCurp(Optional<String> receiverCurp) {
		this.receiverCurp = receiverCurp;
	}

	public Optional<String> getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public void setSocialSecurityNumber(Optional<String> socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	public Optional<String> getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(Optional<String> employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public Optional<String> getDepartment() {
		return department;
	}

	public void setDepartment(Optional<String> department) {
		this.department = department;
	}

	public Optional<BigDecimal> getNumberDaysPaid() {
		return numberDaysPaid;
	}

	public void setNumberDaysPaid(Optional<BigDecimal> numberDaysPaid) {
		this.numberDaysPaid = numberDaysPaid;
	}
}
