package cn.itcast.bos.web.action.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Area;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{

	protected T model;
	@Override
	public T getModel() {
		return model;
	}
	
	public BaseAction() {
		try {
			Type type = this.getClass().getGenericSuperclass();
			ParameterizedType ptype = (ParameterizedType)type;
			Class<T> modelClass = (Class<T>) ptype.getActualTypeArguments()[0];
			model= modelClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("模型构造失败");
		}
	}

	protected int page;
	protected int rows;
	
	public void setPage(int page) {
		this.page = page;
	}


	public void setRows(int rows) {
		this.rows = rows;
	}
	
}
