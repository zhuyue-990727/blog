package com.zy.web.admin;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zy.po.User;
import com.zy.service.Userservice;

@Controller
@RequestMapping("/admin")
public class LoginController {

	@Autowired
	private Userservice userService;

@GetMapping	
public String loginPage() {
	return "admin/login";
}

@PostMapping("/login")
public String login(@RequestParam String username,@RequestParam String password,HttpSession session,RedirectAttributes attributes) {
	
	User user =userService.checkUser(username,password);
	if(user!=null) {
		user.setPassword(null);
		session.setAttribute("user",user);
		return "admin/index";
	}else {
		attributes.addFlashAttribute("message","用户名和密码错误");
		return "redirect:/admin";
	}
}
@GetMapping("/logout")
public String logout(HttpSession session) {
	session.removeAttribute("user");
	return "redirect:/admin";
}
}
