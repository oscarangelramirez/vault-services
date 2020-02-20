package com.buzone.vault.services.data.paysheet;

import java.math.BigDecimal;
import java.util.Date;

import com.buzone.vault.services.domain.issue.paysheet.aggregates.IssuePaySheet;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

public class GetIssuePaySheetResponse {
	@JsonView
	private Long id;
	
	@JsonView
	private Integer idStateSAT;
	
	@JsonView
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private Date issueDate;
	
	@JsonView
	private String invoice;
	
	@JsonView
	private String serie;
	
	@JsonView
	private String uuid;
	
	@JsonView
	private String receiverRfc;
	
	@JsonView
	private String receiver;
	
	@JsonView
	private String employerRegister;
	
	@JsonView
	private String type;
	
	@JsonView
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private Date paymentDate;
	
	@JsonView
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private Date beginPaymentDate;
	
	@JsonView
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private Date endPaymentDate;
	
	@JsonView
	private BigDecimal numberDaysPaid;
	
	@JsonView
	private BigDecimal total;
	
	@JsonView
	private String wayPay;
	
	@JsonView
	private String taxRegime;
	
	@JsonView
	private String version;
	
	@JsonView
	private String currency;
	
	@JsonView
	private String receiverCurp;
	
	@JsonView
	private String socialSecurityNumber;
	
	@JsonView
	private String regimeType;
	
	@JsonView
	private String employeeNumber;
	
	@JsonView
	private String periodicityPayment;
	
	@JsonView
	private String department;
	
	@JsonView
	private String filePath;
		
	public GetIssuePaySheetResponse(IssuePaySheet issuePaySheet) {
		this.id = issuePaySheet.getId();
		this.idStateSAT = issuePaySheet.getIdStateSAT();
		this.issueDate = issuePaySheet.getIssueDate();
		this.invoice = issuePaySheet.getInvoice();
		this.serie = issuePaySheet.getSerie();
		this.uuid = issuePaySheet.getUuid().toString();
		this.receiverRfc = issuePaySheet.getReceiverRfc();
		this.receiver = issuePaySheet.getReceiver();
		this.employerRegister = issuePaySheet.getEmployerRegister();
		this.type = issuePaySheet.getType();
		this.paymentDate = issuePaySheet.getPaymentDate();
		this.beginPaymentDate = issuePaySheet.getBeginPaymentDate();
		this.endPaymentDate = issuePaySheet.getEndPaymentDate();
		this.numberDaysPaid = issuePaySheet.getNumberDaysPaid();
		this.total = issuePaySheet.getTotal();
		this.wayPay = issuePaySheet.getWayPay();
		this.taxRegime = issuePaySheet.getTaxRegime();
		this.version = issuePaySheet.getVersion();
		this.currency = issuePaySheet.getCurrency();
		this.receiverCurp = issuePaySheet.getReceiverCurp();
		this.socialSecurityNumber = issuePaySheet.getSocialSecurityNumber();
		this.regimeType = issuePaySheet.getRegimeType();
		this.employeeNumber = issuePaySheet.getEmployeeNumber();
		this.periodicityPayment = issuePaySheet.getPeriodicityPayment();
		this.department = issuePaySheet.getDepartment();
		this.filePath = issuePaySheet.getFilePath();
	}
}
