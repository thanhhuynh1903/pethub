package com.pethub.checkout;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.pethub.ControllerHelper;
import com.pethub.Utility;
import com.pethub.address.AddressService;
import com.pethub.common.entity.Address;
import com.pethub.common.entity.CartItem;
import com.pethub.common.entity.Customer;
import com.pethub.common.entity.ShippingRate;
import com.pethub.common.entity.order.Order;
import com.pethub.common.entity.order.PaymentMethod;
import com.pethub.setting.EmailSettingBag;
import com.pethub.setting.SettingService;
import com.pethub.shipping.ShippingRateService;
import com.pethub.shoppingcart.ShoppingCartService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CheckoutController {

	@Autowired private CheckoutService checkoutService;
	@Autowired private ControllerHelper controllerHelper;
	@Autowired private AddressService addressService;
	@Autowired private ShippingRateService shipService;
	@Autowired private ShoppingCartService cartService;
	@Autowired private SettingService settingService;
	
	@GetMapping("/checkout")
	public String showCheckoutPage(Model model, HttpServletRequest request) {
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		
		Address defaultAddress = addressService.getDefaultAddress(customer);
		ShippingRate shippingRate = null;
		
		if (defaultAddress != null) {
			model.addAttribute("shippingAddress", defaultAddress.toString());
			shippingRate = shipService.getShippingRateForAddress(defaultAddress);
		} else {
			model.addAttribute("shippingAddress", customer.toString());
			shippingRate = shipService.getShippingRateForCustomer(customer);
		}
		
		if (shippingRate == null) {
			return "redirect:/cart";
		}
		
		List<CartItem> cartItems = cartService.listCartItems(customer);
		CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(cartItems, shippingRate);
		

		model.addAttribute("customer", customer);
		model.addAttribute("checkoutInfo", checkoutInfo);
		model.addAttribute("cartItems", cartItems);
		
		return "checkout/checkout";
	}
	
	
}
