package com.pethub.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.pethub.ControllerHelper;
import com.pethub.common.entity.Customer;
import com.pethub.common.entity.order.Order;
import com.pethub.customer.CustomerService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ControllerHelper controllerHelper;

	@GetMapping("/orders")
	public String getOrders(Model model, HttpServletRequest request, @RequestParam String keyword,
			@RequestParam String sortDir, @RequestParam String sortField, @PathVariable("pageNum") int pageNum) {
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		Page<Order> pageOrders = orderService.listForCustomerByPage(customer, pageNum, sortField, sortDir, keyword);
		model.addAttribute("customer", customer);
		model.addAttribute("authenticationType", customer.getAuthenticationType());
		model.addAttribute("pageOrders", pageOrders);
		return "order/order";
	}

}
