package com.buzone.vault.services.domain.issue.paysheet.aggregates;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "EmisionNomina", schema = "interjet")
public class IssuePaySheet {
	@Column(name="curpReceptor")
	private String receiverCurp;

	@Column(name="departamento")
	private String department;

	@Temporal(TemporalType.DATE)
	@Column(name="fechaEmision")
	private Date issueDate;

	@Temporal(TemporalType.DATE)
	@Column(name="fechaFinPago")
	private Date endPaymentDate;

	@Temporal(TemporalType.DATE)
	@Column(name="fechaIniPago")
	private Date beginPaymentDate;

	@Temporal(TemporalType.DATE)
	@Column(name="fechaPago")
	private Date paymentDate;

	@Column(name="folio")
	private String invoice;

	@Column(name="formaPago")
	private String wayPay;

	@Id
	@Column(name="idEmisionNomina")
	private Long id;

	@Column(name="idEstatusSat")
	private Integer idStateSAT;

	@Column(name="moneda")
	private String currency;

	@Column(name="numDiasPagados")
	private BigDecimal numberDaysPaid;

	@Column(name="numEmpleado")
	private String employeeNumber;

	@Column(name="numSegSocial")
	private String socialSecurityNumber;

	@Column(name="periodicidadPago")
	private String periodicityPayment;

	@Column(name="receptor")
	private String receiver;

	@Column(name="regimenFiscal")
	private String taxRegime;

	@Column(name="registroPatronal")
	private String employerRegister;

	@Column(name="rfcReceptor")
	private String receiverRfc;

	@Column(name="rutaFile")
	private String filePath;

	@Column(name="serie")
	private String serie;

	@Column(name="tipoNomina")
	private String type;

	@Column(name="tipoRegimen")
	private String regimeType;

	@Column(name="total")
	private BigDecimal total;

	@Column(name="uuid")
	private UUID uuid;

	@Column(name="versionNomina")
	private String version;

	public IssuePaySheet() {
	}

	public String getReceiverCurp() {
		return receiverCurp;
	}

	public void setReceiverCurp(String receiverCurp) {
		this.receiverCurp = receiverCurp;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getEndPaymentDate() {
		return endPaymentDate;
	}

	public void setEndPaymentDate(Date endPaymentDate) {
		this.endPaymentDate = endPaymentDate;
	}

	public Date getBeginPaymentDate() {
		return beginPaymentDate;
	}

	public void setBeginPaymentDate(Date beginPaymentDate) {
		this.beginPaymentDate = beginPaymentDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getWayPay() {
		return wayPay;
	}

	public void setWayPay(String wayPay) {
		this.wayPay = wayPay;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIdStateSAT() {
		return idStateSAT;
	}

	public void setIdStateSAT(Integer idStateSAT) {
		this.idStateSAT = idStateSAT;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getNumberDaysPaid() {
		return numberDaysPaid;
	}

	public void setNumberDaysPaid(BigDecimal numberDaysPaid) {
		this.numberDaysPaid = numberDaysPaid;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	public String getPeriodicityPayment() {
		return periodicityPayment;
	}

	public void setPeriodicityPayment(String periodicityPayment) {
		this.periodicityPayment = periodicityPayment;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getTaxRegime() {
		return taxRegime;
	}

	public void setTaxRegime(String taxRegime) {
		this.taxRegime = taxRegime;
	}

	public String getEmployerRegister() {
		return employerRegister;
	}

	public void setEmployerRegister(String employerRegister) {
		this.employerRegister = employerRegister;
	}

	public String getReceiverRfc() {
		return receiverRfc;
	}

	public void setReceiverRfc(String receiverRfc) {
		this.receiverRfc = receiverRfc;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRegimeType() {
		return regimeType;
	}

	public void setRegimeType(String regimeType) {
		this.regimeType = regimeType;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
