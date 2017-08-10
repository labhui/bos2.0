package cn.itcast.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;
import cn.itcast.bos.utils.PinYin4jUtils;
import cn.itcast.bos.web.action.common.BaseAction;
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class AreaAction extends BaseAction<Area>{

	@Autowired
	private AreaService areaService;
	
	private File file;
	
	public void setFile(File file) {
		this.file = file;
	}


	@Action(value="area_batchImport")
	public String batchImport() throws FileNotFoundException, IOException{
		//解析excel文件,基于.xls解析,采用poi技术
		HSSFWorkbook hssf = new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet sheet = hssf.getSheetAt(0);
		List<Area> list=new ArrayList<Area>();
		for(Row row:sheet){
			Area are = new Area();
			if(row.getRowNum()==0){
				continue;
			}
			//跳过空行
			if(row.getCell(0)==null||StringUtils.isBlank(row.getCell(0).getStringCellValue())){
				continue;
			}
			are.setId(row.getCell(0).getStringCellValue());
			are.setProvince(row.getCell(1).getStringCellValue());
			are.setCity(row.getCell(2).getStringCellValue());
			are.setDistrict(row.getCell(3).getStringCellValue());
			are.setPostcode(row.getCell(4).getStringCellValue());
			//基于pinyin4j生成城市编码和简码
			String province = are.getProvince();
			String city = are.getCity();
			String district = are.getDistrict();
			province=province.substring(0, province.length()-1);
			city=city.substring(0, city.length()-1);
			district=district.substring(0, district.length()-1);
			String[] headArray = PinYin4jUtils.getHeadByString(province+city+district);
			StringBuffer sb = new StringBuffer();
			for(String headStr:headArray){
				sb.append(headStr);
			}
			are.setShortcode(sb.toString());
			are.setCitycode(PinYin4jUtils.hanziToPinyin(city,""));
			list.add(are);
		}
		areaService.saveBatch(list);
		return NONE;
		
	}
	
	@Action(value="area_pageQuery",results={@Result(name="success",type="json")})
	public String pageQuery(){
		Pageable pageable=new PageRequest(page-1, rows);
		
		Specification<Area> spf = new Specification<Area>() {

			@Override
			public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list=new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(model.getProvince())){
					Predicate p1 = cb.like(root.get("province").as(String.class),"%"+model.getProvince()+"%");
					list.add(p1);
				}
				if(StringUtils.isNotBlank(model.getCity())){
					Predicate p2 = cb.like(root.get("city").as(String.class),"%"+model.getCity()+"%");
					list.add(p2);
				}
				if(StringUtils.isNotBlank(model.getDistrict())){
					Predicate p3 = cb.like(root.get("district").as(String.class),"%"+model.getDistrict()+"%");
					list.add(p3);
				}
				
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		Page<Area> page =areaService.findPage(spf,pageable);
		Map<String ,Object> map=new HashMap<String ,Object>();
		map.put("total", page.getNumberOfElements());
		map.put("rows", page.getContent());
		ActionContext.getContext().getValueStack().push(map);
		return SUCCESS;
		
	}
}
