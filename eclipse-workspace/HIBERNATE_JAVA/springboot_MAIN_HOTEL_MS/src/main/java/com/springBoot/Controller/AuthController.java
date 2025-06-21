//package com.springBoot.Controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.springBoot.Room.RoomBooking;
//import com.springBoot.Room.RoomBookingRepository;
//import com.springBoot.User.User;
//import com.springBoot.User.UserService;
//
//import jakarta.servlet.http.HttpSession;
//import jakarta.validation.Valid;
//
//@Controller
//public class AuthController {
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	@Autowired
//	private RoomBookingRepository bookingRepo;



///////////////////////////////////////////////////////////////DOWN////////////////////////////////////////////
//    @GetMapping("/login")
//    public String loginPage(Model model) {
//        model.addAttribute("user", new User());
//        return "login";
//    }
//
//
//    @GetMapping("/signup")
//    public String signupForm(Model model) {
//        model.addAttribute("user", new User());
//        return "login";
//    }

//    @GetMapping("/login")
//    public String loginPage(Model model) {
//        model.addAttribute("user", new User());
//        model.addAttribute("tab", "login");
//        return "login";
//    }
//
//    @GetMapping("/signup")
//    public String signupPage(Model model) {
//        model.addAttribute("user", new User());
//        model.addAttribute("tab", "signup");
//        return "login"; // same page
//    }
//
//    @PostMapping("/signup")
//    public String processSignup(@ModelAttribute User user) {
//        userService.register(user, passwordEncoder);
//        return "redirect:/login";
//    }/
/////////////////////////////////////////////UP//////////////////////////////////////////////////
//	@GetMapping("/login")
//	public String login(@RequestParam(value = "error", required = false) String error,
//			@RequestParam(value = "logout", required = false) String logout, Model model) {
//		if (error != null) {
//			model.addAttribute("error", "Invalid username or password");
//		}
//		if (logout != null) {
//			model.addAttribute("logout", "You have been logged out.");
//		}
//		model.addAttribute("user", new User()); // Add this line to initialize the user object for the signup form
//		return "login";
//	}
//
//	@PostMapping("/signup")
//	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result,
//			RedirectAttributes redirectAttributes, Model model) {
//		if (result.hasErrors()) {
//			// If there are validation errors, return to the login page with errors
//			model.addAttribute("user", user); // Re-add the user object to retain entered values
//			return "login";
//		}
//
//		if (userService.emailExists(user.getEmail())) {
//			redirectAttributes.addFlashAttribute("signupError", "Email already exists");
//			return "redirect:/login";
//		}
//
//		// Encode the password
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//		// Save the new user to the database
//		userService.registerUser(user);
//
//		redirectAttributes.addFlashAttribute("signupSuccess", "Signup successful! Please log in.");
//		return "redirect:/login";
//	}
//
//	@GetMapping("/register") // You might still have a separate signup link
//	public String showSignupForm(Model model) {
//		model.addAttribute("user", new User());
//		return "register"; // Or wherever your full signup form might be
//	}
//
//	@GetMapping("/")
//	public String showHome(HttpSession session, Model model) {
//		Long userId = (Long) session.getAttribute("userId");
//		if (userId != null) {
//			User user = userService.findById(userId);
//			model.addAttribute("user", user);
//			List<RoomBooking> bookings = bookingRepo.findByUserId(userId);
//			model.addAttribute("bookings", bookings);
//		}
//		return "Home";
//	}
//
//	@GetMapping("/register")
//	public String showRegistrationForm(Model model) {
//		model.addAttribute("user", new User());
//		return "Register";
//	}
//
//	@PostMapping("/register")
//	public String registerUser(@ModelAttribute User user, HttpSession session) {
//		if (userService.emailExists(user.getEmail())) {
//			return "redirect:/register?error=email";
//		}
//		User registeredUser = userService.registerUser(user);
//		session.setAttribute("userId", registeredUser.getId());
//		return "redirect:/";
//	}
//
//	@GetMapping("/login")
//	public String showLoginForm(Model model) {
//		model.addAttribute("user", new User());
//		return "Login";
//	}
////////////////////////////////////////////////////////DOWN//////////////////////////////////////////////////
//    @PostMapping("/login")
//    public String doLogin(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
//        User user = userService.findByEmail(email);
//        if (user == null || !new BCryptPasswordEncoder().matches(password, user.getPassword())) {
//            model.addAttribute("error", "Invalid credentials or user not signed up.");
//            return "login";
//        }
//        session.setAttribute("userId", user.getId());
//        return "redirect:/";
//    }
////////////////////////////////////////////////////////UP////////////////////////////////////////////////////////////////
//	@GetMapping("/signup")
//	public String signupPage() {
//		return "login";
//	}
//
//	@PostMapping("/signup")
//	public String doSignup(@ModelAttribute User user, Model model) {
//		if (userService.emailExists(user.getEmail())) {
//			model.addAttribute("error", "Email already exists");
//			return "login";
//		}
//		userService.registerUser(user);
//		model.addAttribute("signupSuccess", true);
//		return "login";
//	}
//
//	@GetMapping("/logout")
//	public String logout(HttpSession session) {
//		session.invalidate();
//		return "redirect:/";
//	}
//
//	@GetMapping("/rooms")
//	public String showBookingPage(HttpSession session, Model model) {
//		Long userId = (Long) session.getAttribute("userId");
//		if (userId == null)
//			return "redirect:/login";
//		List<RoomBooking> bookings = bookingRepo.findByUserId(userId);
//		model.addAttribute("bookings", bookings);
//		return "CustomerRecords";
//	}
//
//	@PostMapping("/rooms")
//	public String addBooking(@ModelAttribute RoomBooking booking, HttpSession session) {
//		Long userId = (Long) session.getAttribute("userId");
//		if (userId == null)
//			return "redirect:/login";
//		User user = userService.findById(userId);
//		booking.setUser(user);
//		bookingRepo.save(booking);
//		return "redirect:/rooms";
//	}
//////////////////////////////////////////////////DOWN/////////////////////////////////////////////////////////
//    @GetMapping("/rooms/delete/{id}")
//    public String deleteBooking(@PathVariable Long id, HttpSession session) {
//        Long userId = (Long) session.getAttribute("userId");
//        RoomBooking booking = bookingRepo.findById(id).orElse(null);
//        if (booking != null && booking.getUser().getId().equals(userId)) {
//            bookingRepo.deleteById(id);
//        }
//        return "redirect:/rooms";
//    }

//    @PostMapping("/rooms/update")
//    public String updateBooking(@ModelAttribute RoomBooking updated, HttpSession session) {
//        Long userId = (Long) session.getAttribute("userId");
//        RoomBooking original = bookingRepo.findById(updated.getId()).orElse(null);
//        if (original != null && original.getUser().getId().equals(userId)) {
//            updated.setUser(original.getUser());
//            bookingRepo.save(updated);
//        }
//        return "redirect:/rooms";
//    }
	
	
///////////////////////////////////////////////////////////UP////////////////////////////////////////////////////

	
	
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
	    public String home(Model model, @org.springframework.security.core.annotation.AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
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

	

