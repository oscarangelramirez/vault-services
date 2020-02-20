package com.buzone.vault.services.data.download;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AddCfdiDownloadRequest {
	private List<Long> ids;
	
	private Optional<Integer> idStateSAT;
	
	private Optional<String> version;
	
	private Optional<Date> issueDateFrom;
	
	private Optional<Date> issueDateTo;
	
	private Optional<Date> ringingDateFrom;
	
	private Optional<Date> ringingDateTo;
	
	private Optional<String> uuid;
	
	private Optional<String> issueRfc;
	
	private Optional<String> issue;
	
	private Optional<String> expeditionPlace;
	
	private Optional<String> receiverRfc;
	
	private Optional<String> receiver;
	
	private Optional<String> use;
	
	private Optional<BigDecimal> total;
	
	private Optional<String> currency;

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

	public Optional<String> getVersion() {
		return version;
	}

	public void setVersion(Optional<String> version) {
		this.version = version;
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

	public Optional<Date> getRingingDateFrom() {
		return ringingDateFrom;
	}

	public void setRingingDateFrom(Optional<Date> ringingDateFrom) {
		this.ringingDateFrom = ringingDateFrom;
	}

	public Optional<Date> getRingingDateTo() {
		return ringingDateTo;
	}

	public void setRingingDateTo(Optional<Date> ringingDateTo) {
		this.ringingDateTo = ringingDateTo;
	}

	public Optional<String> getUuid() {
		return uuid;
	}

	public void setUuid(Optional<String> uuid) {
		this.uuid = uuid;
	}

	public Optional<String> getIssueRfc() {
		return issueRfc;
	}

	public void setIssueRfc(Optional<String> issueRfc) {
		this.issueRfc = issueRfc;
	}

	public Optional<String> getIssue() {
		return issue;
	}

	public void setIssue(Optional<String> issue) {
		this.issue = issue;
	}

	public Optional<String> getExpeditionPlace() {
		return expeditionPlace;
	}

	public void setExpeditionPlace(Optional<String> expeditionPlace) {
		this.expeditionPlace = expeditionPlace;
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

	public Optional<String> getUse() {
		return use;
	}

	public void setUse(Optional<String> use) {
		this.use = use;
	}

	public Optional<BigDecimal> getTotal() {
		return total;
	}

	public void setTotal(Optional<BigDecimal> total) {
		this.total = total;
	}

	public Optional<String> getCurrency() {
		return currency;
	}

	public void setCurrency(Optional<String> currency) {
		this.currency = currency;
	}
}
