package com.pethub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pethub.common.entity.Customer;
import com.pethub.customer.CustomerService;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class ControllerHelper {
	@Autowired
	private CustomerService customerService;

	public Customer getAuthenticatedCustomer(HttpServletRequest request) {
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		return customerService.getCustomerByEmail(email);
	}
}
