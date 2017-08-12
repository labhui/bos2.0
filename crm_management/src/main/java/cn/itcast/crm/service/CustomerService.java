package cn.itcast.crm.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import cn.itcast.crm.domain.Customer;

public interface CustomerService {

	@Path("/noassociationcustomers")
	@GET
	@Produces({"application/xml","application/json"})
	public List<Customer> findNoAssociationCustomers();
	
	@Path("/associationfixedareacustomers/{fixedareaid}")
	@GET
	@Produces({"application/xml","application/json"})
	public List<Customer> findHasAssociationFixedAreaCustomers(@PathParam("fixedareaid")String fixedAreaId);
	
	@Path("/associationcustomerstofixedarea")
	@PUT
	public void associationCustomersToFixedArea(@QueryParam("customerIdStr")String customerIdStr,@QueryParam("fixedAreaId") String fixedAreaId);
}
