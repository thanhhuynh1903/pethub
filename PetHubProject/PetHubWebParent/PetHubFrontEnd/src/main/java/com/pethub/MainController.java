package com.pethub;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pethub.brand.BrandService;
import com.pethub.category.CategoryService;
import com.pethub.common.entity.Brand;
import com.pethub.common.entity.Category;
import com.pethub.common.entity.product.Product;
import com.pethub.product.ProductService;

@Controller
public class MainController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private ProductService productService;

	

	@GetMapping("")
	public String viewHomePage(Model model) {
		List<Category> listParentCategories = categoryService.listParentCategories();
		model.addAttribute("listParentCategories", listParentCategories);

		List<Brand> listBrands = brandService.listAllBrands();
		model.addAttribute("listBrands", listBrands);

		List<Product> listProducts = productService.getAllProducts();
		model.addAttribute("listProducts", listProducts);


		//lay 6 san pham duoc giam gia nhieu nhat
		List<Product> list6Products= productService.get6Products();
		model.addAttribute("list6Products", list6Products);

		//lay 6 san pham ban chay nhat
		List<Product> list6BestSaleProducts= productService.get6BestSaleProducts();
		model.addAttribute("list6BestSaleProducts", list6BestSaleProducts);
		return "index";
	}

	@GetMapping("/login")
	public String viewLoginPage() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		return "redirect:/";
	}
}
