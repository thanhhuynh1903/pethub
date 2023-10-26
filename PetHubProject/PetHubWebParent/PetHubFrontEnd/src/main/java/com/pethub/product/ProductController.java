package com.pethub.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.pethub.category.CategoryService;
import com.pethub.common.entity.Category;
import com.pethub.common.entity.product.Product;
import com.pethub.common.exception.CategoryNotFoundException;
import com.pethub.common.exception.ProductNotFoundException;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/c/{category_alias}")
	public String viewCategoryFirstPage(@PathVariable("category_alias") String alias, Model model) {
		return viewCategoryByPage(alias, 1, alias, model);
	}

	// @GetMapping("/c/{category_alias}/page/{pageNum}")
	// public String viewCategoryByPage(@PathVariable("category_alias") String
	// alias, @PathVariable("pageNum") int pageNum,
	// Model model) {
	// try {
	// Category category = categoryService.getCategory(alias);

	// List<Category> listCategoryParents =
	// categoryService.getCategoryParents(category);
	// Page<Product> pageProducts = productService.listByCategory(pageNum,
	// category.getId());
	// List<Product> listProducts = pageProducts.getContent();

	// long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
	// long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
	// if (endCount > pageProducts.getTotalElements()) {
	// endCount = pageProducts.getTotalElements();
	// }

	// model.addAttribute("currentPage", pageNum);
	// model.addAttribute("totalPages", pageProducts.getTotalPages());
	// model.addAttribute("startCount", startCount);
	// model.addAttribute("endCount", endCount);
	// model.addAttribute("totalItems", pageProducts.getTotalElements());
	// model.addAttribute("pageTitle", category.getName());
	// model.addAttribute("listCategoryParents", listCategoryParents);
	// model.addAttribute("listProducts", listProducts);
	// model.addAttribute("category", category);
	// return "product/products_by_category";
	// } catch (CategoryNotFoundException ex) {
	// return "error/404";
	// }
	// }

	@GetMapping("/p/{product_alias}")
	public String viewProductDetail(@PathVariable("product_alias") String alias, Model model) {

		try {
			Product product = productService.getProduct(alias);
			List<Category> listCategoryParents = categoryService.getCategoryParents(product.getCategory());

			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("product", product);
			model.addAttribute("pageTitle", product.getShortName());

			return "product/product_detail";
		} catch (ProductNotFoundException e) {
			return "error/404";
		}
	}

	@GetMapping("/search")
	public String searchFirstPage(String keyword, Model model) {
		return searchByPage(keyword, keyword, 1, model);
	}

	@GetMapping("/search/page/{pageNum}")
	public String searchByPage(@RequestParam String keyword, @RequestParam String sortDir,
			@PathVariable("pageNum") int pageNum, Model model) {
        Page<Product> pageProducts = productService.search1(keyword, pageNum, sortDir);
		List<Product> listResult = pageProducts.getContent();

		long startCount = (pageNum - 1) * ProductService.SEARCH_RESULTS_PER_PAGE + 1;
		long endCount = startCount + ProductService.SEARCH_RESULTS_PER_PAGE - 1;
		if (endCount > pageProducts.getTotalElements()) {
			endCount = pageProducts.getTotalElements();
		}

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", pageProducts.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", pageProducts.getTotalElements());
		model.addAttribute("pageTitle", keyword + " - Search Result");

		model.addAttribute("keyword", keyword);
		model.addAttribute("searchKeyword", keyword);
		model.addAttribute("listResult", listResult);
		model.addAttribute("sortDir", sortDir);

		return "product/search_result";
	}

	@GetMapping("/c/{category_alias}/page/{pageNum}")
	public String viewCategoryByPage(@PathVariable("category_alias") String alias,
			@PathVariable("pageNum") int pageNum,
			@RequestParam(defaultValue = "asc") String sortDir,
			Model model) {
		try {
			// Retrieve the category using the provided alias
			Category category = categoryService.getCategory(alias);

			// Retrieve the list of parent categories for breadcrumb navigation
			List<Category> listCategoryParents = categoryService.getCategoryParents(category);

			// Retrieve a page of products in the category, sorted by price in the specified
			// direction
			Page<Product> pageProducts = productService.listByCategory1(pageNum, category.getId(), sortDir);
			List<Product> listProducts = pageProducts.getContent();

			// Calculate the start and end count for the current page
			long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
			long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
			if (endCount > pageProducts.getTotalElements()) {
				endCount = pageProducts.getTotalElements();
			}

			// Add attributes to the model for displaying in the view
			model.addAttribute("currentPage", pageNum);
			model.addAttribute("totalPages", pageProducts.getTotalPages());
			model.addAttribute("startCount", startCount);
			model.addAttribute("endCount", endCount);
			model.addAttribute("totalItems", pageProducts.getTotalElements());
			model.addAttribute("pageTitle", category.getName());
			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("listProducts", listProducts);
			model.addAttribute("category", category);
			model.addAttribute("sortDir", sortDir);

			// Return the name of the view that displays the products
			return "product/products_by_category";
		} catch (CategoryNotFoundException ex) {
			return "error/404";
		}
	}

}
