package cn.itcast.bos.domain.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name="t_sub_archive")
public class SubArchive {

	@Id
	@GeneratedValue
	@Column(name="c_id")
	private Integer id;
	@Column(name="c_sub_archive_name")
	private String subArchiveName;
	@Column(name="c_memonic_code")
	private String memonicCode;
	@Column(name="c_remark")
	private String remark;
	@Column(name="c_monthballed")
	private Character monthballed;
	@Column(name="c_operating_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date operatingTime;
	@Column(name="c_operator")
	private String operator;
	@Column(name="c_operator_company")
	private String operatingCompany;
	@ManyToOne(targetEntity=Archive.class)
	@JoinColumn(name="c_archive_id")
	private Archive archive;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSubArchiveName() {
		return subArchiveName;
	}
	public void setSubArchiveName(String subArchiveName) {
		this.subArchiveName = subArchiveName;
	}
	public String getMemonicCode() {
		return memonicCode;
	}
	public void setMemonicCode(String memonicCode) {
		this.memonicCode = memonicCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Character getMonthballed() {
		return monthballed;
	}
	public void setMonthballed(Character monthballed) {
		this.monthballed = monthballed;
	}
	public Date getOperatingTime() {
		return operatingTime;
	}
	public void setOperatingTime(Date operatingTime) {
		this.operatingTime = operatingTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperatingCompany() {
		return operatingCompany;
	}
	public void setOperatingCompany(String operatingCompany) {
		this.operatingCompany = operatingCompany;
	}
	public Archive getArchive() {
		return archive;
	}
	public void setArchive(Archive archive) {
		this.archive = archive;
	}
	
}
