package cn.itcast.bos.domain.base;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name="t_archive")
public class Archive {

	@Id
	@GeneratedValue
	@Column(name="c_id")
	private Integer id;
	@Column(name="c_archive_num",unique=true)
	private String archiveNum;
	@Column(name="c_archive_name")
	private String archiveName;
	@Column(name="c_remark")
	private String remark;
	@Column(name="c_haschild")
	private Integer hasChild;
	@Column(name="c_operating_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date operatingTime;
	@Column(name="c_operator")
	private String operator;
	@Column(name="c_operator_company")
	private String operatingCompany;
	@OneToMany(targetEntity=SubArchive.class,mappedBy="archive")
	private Set<SubArchive> subArchives=new HashSet<SubArchive>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getArchiveNum() {
		return archiveNum;
	}
	public void setArchiveNum(String archiveNum) {
		this.archiveNum = archiveNum;
	}
	public String getArchiveName() {
		return archiveName;
	}
	public void setArchiveName(String archiveName) {
		this.archiveName = archiveName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getHasChild() {
		return hasChild;
	}
	public void setHasChild(Integer hasChild) {
		this.hasChild = hasChild;
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
	public Set<SubArchive> getSubArchives() {
		return subArchives;
	}
	public void setSubArchives(Set<SubArchive> subArchives) {
		this.subArchives = subArchives;
	}
	
	
}
