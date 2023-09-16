package com.pethub.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pethub.common.entity.Role;
import com.pethub.common.entity.User;

@Controller
public class UserController {
	@Autowired
	private UserService service;
	
	@GetMapping("/users") //handling HTTP get request
	public String listAll(Model model) {
		List<User> listUsers = service.listAll();
		model.addAttribute("listUsers", listUsers);
		return "users"; //return a logical view name to physical view file
	}
	
	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Role> listRoles = service.listRoles();
		User user = new User();
		user.setEnabled(true);
		model.addAttribute("user",user);
		model.addAttribute("listRoles",listRoles);
		return "user_form";
	}
	
	@PostMapping("/users/save") //handling HTTP post request
	public String saveUser(User user, RedirectAttributes redirectAttributes) {
		System.out.println(user);
		service.save(user);
		
		redirectAttributes.addFlashAttribute("message", "The user has been saved succesfully.");
		
		return "redirect:/users"; // = respond.sendRedirect
	}
}
