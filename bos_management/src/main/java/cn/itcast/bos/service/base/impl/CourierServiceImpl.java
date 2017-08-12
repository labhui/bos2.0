package cn.itcast.bos.service.base.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;
@Service
@Transactional
public class CourierServiceImpl implements CourierService {

	@Autowired
	private CourierRepository courierRepository;
	
	@Override
	public void save(Courier courier) {
		courierRepository.save(courier);
	}

	@Override
	public Page<Courier> pageQuery(Specification<Courier> specification,Pageable pageable) {
		return courierRepository.findAll(specification,pageable);
	}

	@Override
	public void delete(String[] id) {
		for(String idStr:id){
			Integer cid = Integer.parseInt(idStr);
			courierRepository.updateDelTag(cid);
		}
	}

	@Override
	public List<Courier> findNoAssociation() {
		Specification<Courier> spf = new Specification<Courier>() {

			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p=cb.isEmpty(root.get("fixedAreas").as(Set.class));
				return p;
			}
			
		};
		return courierRepository.findAll(spf);
	}

}
