
package com.springBoot.Controller;

import com.springBoot.User.User;
import com.springBoot.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/signup")
	public String signup(@ModelAttribute("user") User user, Model model) {
		if (userRepo.findByEmail(user.getEmail()).isPresent()) {
			model.addAttribute("signupError", "Email already registered");
			return "login";
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
		model.addAttribute("signupSuccess", "Signup successful! Please log in.");
		return "login";
	}

	@GetMapping("/login")
	public String showLoginPage(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@GetMapping("/")
	public String redirectToHome() {
		return "redirect:/home";
	}

	@GetMapping("/home")
	public String home(Model model,
			@org.springframework.security.core.annotation.AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
		if (principal != null) {
			String email = principal.getUsername(); // get email from logged-in user
			User user = userRepo.findByEmail(email).orElse(null);
			if (user != null) {
				model.addAttribute("fullName", user.getFirstName()); // pass full name to the view
			}
		}
		return "Home";
	}

}
