package cn.itcast.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierRepository;
import cn.itcast.bos.dao.base.FixedAreaRepository;
import cn.itcast.bos.dao.base.TakeTimeRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.FixedAreaService;
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

	@Autowired
	private FixedAreaRepository fixedAreaRepository;
	@Autowired
	private CourierRepository courierRepository;
	@Autowired
	private TakeTimeRepository takeTimeRepository;
	
	@Override
	public void save(FixedArea model) {
		fixedAreaRepository.save(model);
	}
	@Override
	public Page<FixedArea> findPage(Specification<FixedArea> spf, Pageable pageable) {
		return fixedAreaRepository.findAll(spf, pageable);
	}
	@Override
	public void associationCourierToFixedArea(FixedArea model, Integer courierId, Integer takeTimeId) {
		
		FixedArea fixedArea=fixedAreaRepository.findOne(model.getId());
		
		Courier courier = courierRepository.findOne(courierId);
		TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
		//快递员关联到定区
		fixedArea.getCouriers().add(courier);
		//将收派时间关联到快递员上
		courier.setTakeTime(takeTime);
	}

}
