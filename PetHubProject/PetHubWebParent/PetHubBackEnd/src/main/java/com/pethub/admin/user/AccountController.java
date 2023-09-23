package com.pethub.admin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pethub.admin.security.PetHubUserDetails;
import com.pethub.common.entity.User;

@Controller
public class AccountController {

	@Autowired
	private UserService service;

	@GetMapping("/account")
	public String viewDetails(@AuthenticationPrincipal PetHubUserDetails loggedUser, Model model) {
		String email = loggedUser.getUsername();
		User user = service.getByEmail(email);
		model.addAttribute("user", user);

		return "account_form";
	}
}
