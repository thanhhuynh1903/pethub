package com.pethub.shoppingcart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pethub.common.entity.CartItem;
import com.pethub.common.entity.Customer;
import com.pethub.common.entity.product.Product;
import com.pethub.product.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ShoppingCartService {

	@Autowired
	private CartItemRepository cartRepo;
	@Autowired
	private ProductRepository productRepo;

	public Integer addProduct(Integer productId, Integer quantity, Customer customer) throws ShoppingCartException {
		Integer updatedQuantity = quantity;
		Product product = new Product(productId);

		CartItem cartItem = cartRepo.findByCustomerAndProduct(customer, product);

		if (cartItem != null) {
			updatedQuantity = cartItem.getQuantity() + quantity;

			if (updatedQuantity > 5) {
				throw new ShoppingCartException("Could not add more " + quantity + " item(s)"
						+ " because there's already " + cartItem.getQuantity() + " item(s) "
						+ "in your shopping cart. Maximum allowed quantity is 5.");
			}
		} else {
			cartItem = new CartItem();
			cartItem.setCustomer(customer);
			cartItem.setProduct(product);
		}

		cartItem.setQuantity(updatedQuantity);

		cartRepo.save(cartItem);

		return updatedQuantity;
	}

	
}
