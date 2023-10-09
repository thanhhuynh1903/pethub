package com.pethub.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}

	@GetMapping("/login")
	public String viewLoginPage() {
		return "login";
	}
	
	@GetMapping("/landing")
	public String viewLandingPage() {
		return "landing";
	}
	
	/*
	 * @GetMapping("/register") public String registerPage() { return "register"; }
	 */
}
