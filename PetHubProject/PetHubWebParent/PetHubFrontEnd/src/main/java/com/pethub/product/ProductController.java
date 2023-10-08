package com.pethub.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pethub.category.CategoryService;
import com.pethub.common.entity.Category;

@Controller
public class ProductController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/c/{category_alias}")
	public String viewCategoryFirstPage(@PathVariable("category_alias") String alias, Model model) {
		Category category = categoryService.getCategory(alias);
		if (category == null) {
			return "error/404";
		}

		model.addAttribute("pageTitle", category.getName());

		return "products_by_category";
	}

}
