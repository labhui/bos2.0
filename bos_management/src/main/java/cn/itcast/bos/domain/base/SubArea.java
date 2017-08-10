package cn.itcast.bos.domain.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="t_sub_area")
public class SubArea {

	@Id
	@GeneratedValue
	@Column(name="c_id")
	private String id;
	@Column(name="c_startnum")
	private String startNum;
	@Column(name="c_endnum")
	private String endNum;
	@Column(name="c_single")
	private Character single;
	@Column(name="c_key_words")
	private String keyWords;
	@Column(name="c_assist_key_words")
	private String assistKeyWords;
	@ManyToOne(targetEntity=SubArea.class)
	@JoinColumn(name="c_area_id")
	private Area area;
	@ManyToOne(targetEntity=FixedArea.class)
	@JoinColumn(name="c_fixedarea_id")
	private FixedArea fixedArea;
	
	public FixedArea getFixedArea() {
		return fixedArea;
	}
	public void setFixedArea(FixedArea fixedArea) {
		this.fixedArea = fixedArea;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStartNum() {
		return startNum;
	}
	public void setStartNum(String startNum) {
		this.startNum = startNum;
	}
	public String getEndNum() {
		return endNum;
	}
	public void setEndNum(String endNum) {
		this.endNum = endNum;
	}
	public Character getSingle() {
		return single;
	}
	public void setSingle(Character single) {
		this.single = single;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getAssistKeyWords() {
		return assistKeyWords;
	}
	public void setAssistKeyWords(String assistKeyWords) {
		this.assistKeyWords = assistKeyWords;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	
}
