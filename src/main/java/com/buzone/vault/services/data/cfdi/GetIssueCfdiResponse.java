package com.buzone.vault.services.data.cfdi;

import java.math.BigDecimal;
import java.util.Date;

import com.buzone.vault.services.domain.issue.cfdi.aggregates.IssueCfdi;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

public class GetIssueCfdiResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private Integer idStateSAT;
	
	@JsonView
	private String uuid;
	
	@JsonView
	private String serie;
	
	@JsonView
	private String issue;
	
	@JsonView
	private String issueRfc;

	@JsonView
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date issueDate;
	
	@JsonView
	private String receiver;

	@JsonView
	private String receiverRfc;

	@JsonView
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date ringingDate;

	@JsonView
	private String invoice;

	@JsonView
	private String wayPay;

	@JsonView
	private String expeditionPlace;

	@JsonView
	private String paymentMethod;

	@JsonView
	private String currency;

	@JsonView
	private BigDecimal subTotal;
	
	@JsonView
	private BigDecimal total;

	@JsonView
	private String type;

	@JsonView
	private String use;

	@JsonView
	private String version;
	
	@JsonView
	private String filePath;
	
	public GetIssueCfdiResponse(IssueCfdi issueCFDI) {
		this.id = issueCFDI.getId();
		this.idStateSAT = issueCFDI.getIdStateSAT();
		this.uuid = issueCFDI.getUuid().toString();
		this.serie = issueCFDI.getSerie();
		this.issue = issueCFDI.getIssue();
		this.issueRfc = issueCFDI.getIssueRfc();
		this.issueDate = issueCFDI.getIssueDate();
		this.receiver = issueCFDI.getReceiver();
		this.receiverRfc = issueCFDI.getReceiverRfc();
		this.ringingDate = issueCFDI.getRingingDate();
		this.invoice = issueCFDI.getInvoice();
		this.wayPay = issueCFDI.getWayPay();
		this.expeditionPlace = issueCFDI.getExpeditionPlace();
		this.paymentMethod = issueCFDI.getPaymentMethod();
		this.currency = issueCFDI.getCurrency();
		this.subTotal = issueCFDI.getSubTotal();
		this.total = issueCFDI.getTotal();
		this.type = issueCFDI.getType();
		this.version = issueCFDI.getVersion();
		this.filePath = issueCFDI.getFilePath();
	}
}
