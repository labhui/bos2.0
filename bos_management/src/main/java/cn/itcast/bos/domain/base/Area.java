package cn.itcast.bos.domain.base;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;
@Entity
@Table(name="t_area")
public class Area {

	@Id
	@Column(name="c_id")
	private String id;
	@Column(name="c_province")
	private String province;
	@Column(name="c_city")
	private String city;
	@Column(name="c_district")
	private String district;
	@Column(name="c_postcode")
	private String postcode;
	@Column(name="c_citycode")
	private String citycode;
	@Column(name="c_shortcode")
	private String shortcode;
	@OneToMany(targetEntity=SubArea.class,mappedBy="area")
	private Set<SubArea> subareas=new HashSet<SubArea>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String getShortcode() {
		return shortcode;
	}
	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}
	@JSON(serialize=false)
	public Set<SubArea> getSubareas() {
		return subareas;
	}
	public void setSubareas(Set<SubArea> subareas) {
		this.subareas = subareas;
	}
	
}
