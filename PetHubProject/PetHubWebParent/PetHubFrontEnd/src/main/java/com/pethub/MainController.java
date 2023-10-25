package com.pethub;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pethub.category.CategoryService;
import com.pethub.common.entity.Category;

@Controller
public class MainController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("")
	public String viewHomePage(Model model) {
		List<Category> listCategories = categoryService.listNoChildrenCategories();

		model.addAttribute("listCategories", listCategories);
		return "home";
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
