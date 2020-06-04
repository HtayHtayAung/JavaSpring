package com.study.curd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.study.curd.model.User;
import com.study.curd.repository.UserRepo;

@Controller
public class UserController {
	@Autowired
	private UserRepo myRepo;

	/* Register Page - GET */
	@RequestMapping("/register")
	public String registerUser(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("checkExist", false);
		return "register";
	}

	/* Register Page - POST */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUserPost(Model model, @ModelAttribute User user) {
		User db_user = myRepo.checkEmail(user.getEmail());
		if (db_user == null) {
			myRepo.save(user);
			model.addAttribute("user", new User());
			// go to login
			return "login";
			
		} else {
			model.addAttribute("checkExist", true);
			// model.addAttribute("user", user);
		}
		model.addAttribute("user", user);
		return "register";
	}

	@RequestMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("loginError", false);
		return "login";
	}

	/* Login Page - POST */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUserPost(Model model, @ModelAttribute User user) {
		User db_user = myRepo.checkLogin(user.getEmail(), user.getPassword());
		if (db_user == null) {
			/* show error */
			model.addAttribute("loginError", true);

		} else {
			/* go to main page */
			List<User> users = myRepo.findAll();
			model.addAttribute("users", users);
			return "index";

		}
		model.addAttribute("user", user);
		return "login";
	}

	/* Edit Page */
	@RequestMapping("/edit/{id}")
	public String editPage(Model model, @PathVariable("id") long id) {
		User user = myRepo.findById(id).orElseThrow();
		model.addAttribute("user", user);
		return "edit";

	}

	/* Edit page Submit */
	@RequestMapping("/edit")
	public String editPageSubmit(Model model, @ModelAttribute User user) {
		myRepo.save(user);

		// to show update data in index page
		List<User> users = myRepo.findAll();
		model.addAttribute("users", users);

		return "index";

	}

	/* Delete page by id */
	@RequestMapping("/delete/{id}")
	public String deletePage(Model model, @PathVariable("id") long id) {
		myRepo.deleteById(id);

		List<User> users = myRepo.findAll();
		model.addAttribute("users", users);

		return "index";

	}

}
