package com.pethub.shoppingcart;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pethub.ControllerHelper;
import com.pethub.address.AddressService;
import com.pethub.common.entity.Address;
import com.pethub.common.entity.CartItem;
import com.pethub.common.entity.Customer;
import com.pethub.common.entity.ShippingRate;
import com.pethub.shipping.ShippingRateService;

@Controller
public class ShoppingCartController {
	@Autowired
	private ControllerHelper controllerHelper;
	@Autowired
	private ShoppingCartService cartService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private ShippingRateService shipService;

	@GetMapping("/cart")
	public String viewCart(Model model, HttpServletRequest request) {
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		List<CartItem> cartItems = cartService.listCartItems(customer);

		float estimatedTotal = 0.0F;

		for (CartItem item : cartItems) {
			estimatedTotal += item.getSubtotal();
		}

		Address defaultAddress = addressService.getDefaultAddress(customer);
		ShippingRate shippingRate = null;
		boolean usePrimaryAddressAsDefault = false;

		if (defaultAddress != null) {
			shippingRate = shipService.getShippingRateForAddress(defaultAddress);
		} else {
			usePrimaryAddressAsDefault = true;
			shippingRate = shipService.getShippingRateForCustomer(customer);
		}

		model.addAttribute("usePrimaryAddressAsDefault", usePrimaryAddressAsDefault);
		model.addAttribute("shippingSupported", shippingRate != null);
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("estimatedTotal", estimatedTotal);
		model.addAttribute("defaultAddress", defaultAddress);
		model.addAttribute("customer", customer);

		return "cart/shopping_cart";
	}
}
