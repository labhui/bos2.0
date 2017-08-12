package cn.itcast.bos.web.action.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
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
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.TakeTimeService;
import cn.itcast.bos.web.action.common.BaseAction;
@Controller
@ParentPackage(value="json-default")
@Namespace("/")
@Scope("prototype")
public class TakeTimeAction extends BaseAction<TakeTime>{
	
	@Autowired
	private TakeTimeService takeTimeService;

	@Action(value="taketime_pageQuery",results={@Result(name="success",type="json")})
	public String pageQuery(){
		Pageable pageable=new PageRequest(page-1,rows);
		Page<TakeTime> page=takeTimeService.findPage(pageable);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("total", page.getNumberOfElements());
		map.put("rows", page.getContent());
		ActionContext.getContext().getValueStack().push(map);
		return SUCCESS;
	}
	
	@Action(value="taketime_findAll",results={@Result(name="success",type="json")})
	public String findAll(){
		List<TakeTime> list=takeTimeService.findAll();
		ActionContext.getContext().getValueStack().push(list);
		return SUCCESS;
		
	}
	
}
