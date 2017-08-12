package cn.itcast.bos.web.action.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
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

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.base.FixedAreaService;
import cn.itcast.bos.web.action.common.BaseAction;
import cn.itcast.crm.domain.Customer;
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea>{

	@Autowired
	private FixedAreaService fixedAreaService;
	
	@Action(value="fixedArea_save",results={@Result(name="success",location="./pages/base/fixed_area.html",type="redirect")})
	public String save(){
		fixedAreaService.save(model);
		return SUCCESS;
		
	}
	
	@Action(value="fixedArea_pageQuery",results={@Result(name="success",type="json")})
	public String pageQuery(){
		Pageable pageable=new PageRequest(page-1,rows);
		Specification<FixedArea> spf=new Specification<FixedArea>() {
			
			@Override
			public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list=new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(model.getId())){
					Predicate p1 = cb.equal(root.get("id").as(String.class), model.getId());
					list.add(p1);
				}
				if(StringUtils.isNotBlank(model.getCompany())){
					Predicate p2 = cb.like(root.get("company").as(String.class), "%"+model.getCompany()+"%");
					list.add(p2);
				}
				return cb.and(list.toArray(new Predicate[0]));
			}

		};
		Page<FixedArea> page=fixedAreaService.findPage(spf,pageable);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("total", page.getNumberOfElements());
		map.put("rows", page.getContent());
		ActionContext.getContext().getValueStack().push(map);
		return SUCCESS;
	}
	
	//查询未关联列表
	@Action(value="fixedArea_findNoAssociationCustomers",results={@Result(name="success",type="json")})
	public String findNoAssociationCustomers(){
		//使用webClient调用webService接口
		Collection<? extends Customer> cols = WebClient.create("http://localhost:9002/crm_management/service/customerService/noassociationcustomers")
		.accept(MediaType.APPLICATION_JSON)
		.getCollection(Customer.class);
		ActionContext.getContext().getValueStack().push(cols);
		return SUCCESS;
		
	}
	
	//查询已经关联列表
	@Action(value="fixedArea_findHasAssociationCustomers",results={@Result(name="success",type="json")})
	public String findHasAssociationCustomers(){
		//使用webClient调用webService接口
		Collection<? extends Customer> cols = WebClient.create("http://localhost:9002/crm_management/service/customerService/associationfixedareacustomers/"+model.getId())
		.accept(MediaType.APPLICATION_JSON)
		.getCollection(Customer.class);
		ActionContext.getContext().getValueStack().push(cols);
		return SUCCESS;
		
	}
	
	private String[] customerIds;
	
		
	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}

	@Action(value="fixedArea_associationCustomersToFixedArea",results={@Result(name="success",location="./pages/base/fixed_area.html",type="redirect")})
	public String associationCustomersToFixedArea(){
		String customerIdStr = StringUtils.join(customerIds, ",");
		WebClient.create("http://localhost:9002/crm_management/service/customerService/associationcustomerstofixedarea?customerIdStr="+customerIdStr+"&fixedAreaId="+model.getId())
		.put(null);
		
		return SUCCESS;
	}
	
	private Integer courierId;
	private Integer takeTimeId;

	public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}

	public void setTakeTimeId(Integer takeTimeId) {
		this.takeTimeId = takeTimeId;
	}

	//定区关联快递员
	@Action(value="fixedArea_associationCourierToFixedArea",results={@Result(name="success",location="./pages/base/fixed_area.html",type="redirect")})
	public String associationCourierToFixedArea(){
		fixedAreaService.associationCourierToFixedArea(model,courierId,takeTimeId);
		return SUCCESS;
	}
}
