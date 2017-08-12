package cn.itcast.crm.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.crm.dao.CustomerRepository;
import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public List<Customer> findNoAssociationCustomers() {
		return customerRepository.findByFixedAreaIdIsNull();
	}

	@Override
	public List<Customer> findHasAssociationFixedAreaCustomers(String fixedAreaId) {
		return customerRepository.findByFixedAreaId(fixedAreaId);
	}

	@Override
	public void associationCustomersToFixedArea(String customerIdStr, String fixedAreaId) {
		customerRepository.clearFixedAreaId(fixedAreaId);
		if(StringUtils.isBlank(customerIdStr)||StringUtils.equals(customerIdStr, "null")){
			return;
		}else{
			String[] ids = customerIdStr.split(",");
			for(String idStr:ids){
				Integer id = Integer.parseInt(idStr);
				customerRepository.updateFixedAreaId(fixedAreaId,id);
			}
		}
		
	}

}
