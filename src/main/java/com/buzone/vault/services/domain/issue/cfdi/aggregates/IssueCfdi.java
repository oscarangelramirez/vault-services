package com.buzone.vault.services.domain.issue.cfdi.aggregates;

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
@Table(name = "EmisionCFDI", schema = "interjet")
public class IssueCfdi {
	@Column(name="emisor")
	private String issue;

	@Temporal(TemporalType.DATE)
	@Column(name="fechaEmision")
	private Date issueDate;

	@Temporal(TemporalType.DATE)
	@Column(name="fechaTimbrado")
	private Date ringingDate;

	@Column(name="folio")
	private String invoice;

	@Column(name="formaPago")
	private String wayPay;

	@Id
	@Column(name="idEmisionCFDI")
	private Long id;

	@Column(name="idEstatusSat")
	private Integer idStateSAT;

	@Column(name="lugarExpedicion")
	private String expeditionPlace;

	@Column(name="metodoPago")
	private String paymentMethod;

	@Column(name="moneda")
	private String currency;

	@Column(name="receptor")
	private String receiver;

	@Column(name="rfcEmisor")
	private String issueRfc;

	@Column(name="rfcReceptor")
	private String receiverRfc;

	@Column(name="rutaFile")
	private String filePath;

	@Column(name="serie")
	private String serie;

	@Column(name="subTotal")
	private BigDecimal subTotal;

	@Column(name="tipoCfdi")
	private String type;

	@Column(name="total")
	private BigDecimal total;

	@Column(name="usoCfdi")
	private String use;

	@Column(name="uuid")
	private UUID uuid;

	@Column(name="versionCFDI")
	private String version;

	public IssueCfdi() {
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getRingingDate() {
		return ringingDate;
	}

	public void setRingingDate(Date ringingDate) {
		this.ringingDate = ringingDate;
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

	public String getExpeditionPlace() {
		return expeditionPlace;
	}

	public void setExpeditionPlace(String expeditionPlace) {
		this.expeditionPlace = expeditionPlace;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getIssueRfc() {
		return issueRfc;
	}

	public void setIssueRfc(String issueRfc) {
		this.issueRfc = issueRfc;
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

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public String getType() {
		return type;
	}

	public void setType(String typeCFDI) {
		this.type = typeCFDI;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getUse() {
		return use;
	}

	public void setUse(String useCFDI) {
		this.use = useCFDI;
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

	public void setVersion(String versionCFDI) {
		this.version = versionCFDI;
	}
}
