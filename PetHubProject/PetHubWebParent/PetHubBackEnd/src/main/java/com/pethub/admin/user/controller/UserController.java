package com.pethub.admin.user.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pethub.admin.FileUploadUtil;
import com.pethub.admin.paging.PagingAndSortingHelper;
import com.pethub.admin.paging.PagingAndSortingParam;
import com.pethub.admin.user.UserNotFoundException;
import com.pethub.admin.user.UserService;
import com.pethub.common.entity.Role;
import com.pethub.common.entity.User;

@Controller
public class UserController {
	private String defaultRedirectURL = "redirect:/users/page/1?sortField=firstName&sortDir=asc";
	@Autowired
	private UserService service;

	@GetMapping("/users")
	public String listFirstPage() {
		return defaultRedirectURL;
	}

	@GetMapping("/users/page/{pageNum}")
	public String listByPage(
			@PagingAndSortingParam(listName = "listUsers", moduleURL = "/users") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum) {
		service.listByPage(pageNum, helper);

		return "users/users";
	}

	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Role> listRoles = service.listRoles();
		User user = new User();
		user.setEnabled(true);
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Create New User");
		return "users/user_form";
	}

	@PostMapping("/users/save") // handling HTTP post request
	public String saveUser(User user, RedirectAttributes redirectAttributes,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {

		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);

			User savedUser = service.save(user);

			String uploadDir = "user-photos/" + savedUser.getId();

			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			if (user.getPhotos().isEmpty()) {
				user.setPhotos(null);
			}
			service.save(user);
		}

		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");

		return getRedirectURLtoAffectedUser(user);
	}

	private String getRedirectURLtoAffectedUser(User user) {
		String firstPartOfEmail = user.getEmail().split("@")[0];
		return "redirect:/users/page/1?sortField=id&sortDir=asc&keyword=" + firstPartOfEmail; // = respond.sendRedirect
	}

	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) { // Spring
		try {
			User user = service.get(id);
			model.addAttribute("user", user);
			List<Role> listRoles = service.listRoles();
			model.addAttribute("listRoles", listRoles);

			model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
			return "users/user_form";
		} catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/users";
		}
	}

	@GetMapping("/users/delete/{id}")
	public String delete(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) { // Spring
		try {
			service.delete(id);
			redirectAttributes.addFlashAttribute("message", "The user ID: " + id + " has been deleted successfully");
		} catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		return "redirect:/users";
	}

	@GetMapping("/users/{id}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,
			RedirectAttributes redirectAttributes) {
		service.updateUserEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The user ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/users";
	}
}
