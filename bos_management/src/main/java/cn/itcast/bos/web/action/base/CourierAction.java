package cn.itcast.bos.web.action.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
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

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;
import cn.itcast.bos.web.action.common.BaseAction;

@Controller
@ParentPackage("json-default")
@Namespace("/")
@Scope("prototype")
public class CourierAction extends BaseAction<Courier>{

	@Autowired
	private CourierService courierService;
	
	@Action(value="courier_save",results={@Result(name="success",location="./pages/base/courier.html",type="redirect")})
	public String save(){
		courierService.save(model);
		return SUCCESS;
	}
	
	//条件查询
	@Action(value="courier_pageQuery",results={@Result(name="success",type="json")})
	public String pageQuery(){
		Pageable pageable=new PageRequest(page-1, rows);
		Specification<Courier> specification = new Specification<Courier>(){

			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(model.getCourierNum())){
					Predicate p1 = cb.equal(root.get("courierNum").as(String.class), model.getCourierNum());
					list.add(p1);
				}
				if(StringUtils.isNotBlank(model.getCompany())){
					 Predicate p2 = cb.like(root.get("company").as(String.class),"%"+ model.getCompany()+"%");
					 list.add(p2);
				}
				if(StringUtils.isNotBlank(model.getType())){
					Predicate p3 = cb.equal(root.get("type").as(String.class), model.getType());
					list.add(p3);
				}
				//多表条件查询
				Join<Object, Object> standardRoot = root.join("standard",JoinType.INNER);
				if(model.getStandard()!=null&&StringUtils.isNotBlank(model.getStandard().getName())){
					Predicate p4 = cb.like(standardRoot.get("name").as(String.class), "%"+ model.getStandard().getName()+"%");
					list.add(p4);
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		Page<Courier> pagedata=courierService.pageQuery(specification,pageable);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("total", pagedata.getNumberOfElements());
		map.put("rows", pagedata.getContent());
		ActionContext.getContext().getValueStack().push(map);
		return SUCCESS;
	}
	
	private String ids;
	
	public void setIds(String ids) {
		this.ids = ids;
	}

	@Action(value="courier_delete",results={@Result(name="success",location="./pages/base/courier.html",type="redirect")})
	public String delete(){
		String[] id = ids.split(",");
		courierService.delete(id);
		return SUCCESS;
		
	}
	
	@Action(value="courier_findnoassociation",results={@Result(name="success",type="json")})
	public String findnoassociation(){
		List<Courier> couriers=courierService.findNoAssociation();
		ActionContext.getContext().getValueStack().push(couriers);
		return SUCCESS;
	}
}
