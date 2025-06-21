//package com.springBoot.Controller;
//
//import java.security.Principal;
//import java.util.List;
//
////import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import com.springBoot.Room.RoomBooking;
//import com.springBoot.Room.RoomBookingRepository;
//import com.springBoot.User.User;
//import com.springBoot.User.UserService;
//import jakarta.validation.Valid;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//@RequestMapping("/rooms")
//public class RoomBookingController {
//    @Autowired 
//    private RoomBookingRepository bookingRepo;
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/register")
//    public String bookingForm(Model model, Principal principal) { // Use Principal instead of Authentication
//        if (principal == null) {
//            return "redirect:/login?error=notSignedUp";
//        }
//        model.addAttribute("booking", new RoomBooking());
//        return "RegisterRoom"; // Assuming "RegisterRoom.html" is your booking form
//    }
/////////////////////////////////////////////////////////////DOWN/////////////////////////////////////////////
//    @PostMapping("/register")
//    public String submitBooking(@ModelAttribute RoomBooking booking, Principal principal) {
//        User user = userRepo.findByEmail(principal.getName())
//                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        booking.setUser(user);
//        bookingRepo.save(booking);
//        return "redirect:/home";
//    }
//
//    @GetMapping("/home")
//    public String homePage() {
//        return "home";
//    }
    
    ////////////////////////////////////////////////////////////////UP////////////////////////////////////
    
    
//    @PostMapping("/register")
//    public String submitBooking(@ModelAttribute @Valid RoomBooking booking, BindingResult result, Principal principal, RedirectAttributes redirectAttributes) { // Use Principal
//        if (principal == null) {
//            return "redirect:/login?error=notSignedUp";
//        }
//        if (result.hasErrors()) {
//            return "RegisterRoom";
//        }
//        String userEmail = principal.getName(); // Get email from Principal
//        User user = userService.findByEmail(userEmail);
//        booking.setUser(user);
//        bookingRepo.save(booking);
//        redirectAttributes.addFlashAttribute("bookingSuccess", "Room booked successfully!");
//        return "redirect:/rooms/list";
//    }
//
//    @GetMapping("/list")
//    public String showBookings(Model model, Principal principal) { // Use Principal
//        if (principal == null) {
//            return "redirect:/login?error=notSignedUp";
//        }
//        String userEmail = principal.getName(); // Get email from Principal
//        User user = userService.findByEmail(userEmail);
//        List<RoomBooking> bookings = bookingRepo.findByUserId(user.getId());
//        model.addAttribute("bookings", bookings);
//        return "CustomerRecords";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteBooking(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) { // Use Principal
//        if (principal == null) {
//            return "redirect:/login?error=notSignedUp";
//        }
//        String userEmail = principal.getName(); // Get email from Principal
//        User user = userService.findByEmail(userEmail);
//        RoomBooking booking = bookingRepo.findById(id).orElse(null);
//        if (booking != null && booking.getUser().getId().equals(user.getId())) {
//            bookingRepo.deleteById(id);
//            redirectAttributes.addFlashAttribute("bookingMessage", "Booking deleted successfully");
//        } else {
//            redirectAttributes.addFlashAttribute("bookingError", "You are not authorized to delete this booking");
//        }
//        return "redirect:/rooms/list";
//    }
//
//    @GetMapping("/update/{id}")
//    public String updateBookingForm(@PathVariable Long id, Model model, Principal principal, RedirectAttributes redirectAttributes) { // Use Principal
//        if (principal == null) {
//            return "redirect:/login?error=notSignedUp";
//        }
//        String userEmail = principal.getName(); // Get email from Principal
//        User user = userService.findByEmail(userEmail);
//        RoomBooking booking = bookingRepo.findById(id).orElse(null);
//        if (booking != null && booking.getUser().getId().equals(user.getId())) {
//            model.addAttribute("booking", booking);
//            return "RegisterRoom"; // Reusing the registration form for update
//        } else {
//            redirectAttributes.addFlashAttribute("bookingError", "You are not authorized to update this booking");
//            return "redirect:/rooms/list";
//        }
//    }
//
//    @PostMapping("/update")
//    public String updateBookingSubmit(@ModelAttribute @Valid RoomBooking updatedBooking, BindingResult result, Principal principal, RedirectAttributes redirectAttributes) { // Use Principal
//        if (principal == null) {
//            return "redirect:/login?error=notSignedUp";
//        }
//        if (result.hasErrors()) {
//            return "RegisterRoom";
//        }
//        String userEmail = principal.getName(); // Get email from Principal
//        User user = userService.findByEmail(userEmail);
//        RoomBooking originalBooking = bookingRepo.findById(updatedBooking.getId()).orElse(null);
//        if (originalBooking != null && originalBooking.getUser().getId().equals(user.getId())) {
//            updatedBooking.setUser(originalBooking.getUser());
//            bookingRepo.save(updatedBooking);
//            redirectAttributes.addFlashAttribute("bookingMessage", "Booking updated successfully");
//        } else {
//            redirectAttributes.addFlashAttribute("bookingError", "You are not authorized to update this booking");
//        }
//        return "redirect:/rooms/list";
//    }
//}


package com.springBoot.Controller;

import com.springBoot.Room.*;
import com.springBoot.User.*;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RoomBookingController {

    @Autowired
    private RoomBookingRepository bookingRepo;

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/register")
    public String showBookingForm(Model model) {
        model.addAttribute("roomBooking", new RoomBooking());
        return "Register";
    }
    
//    @GetMapping("/register")
//    public String showBookingPage(Model model, Principal principal) {
//        // Optional: add the logged-in user's name or email to the model
//        String username = principal.getName(); // Typically email or username
//        model.addAttribute("username", username);

        // Prepare an empty Booking object for form binding if needed
//        model.addAttribute("booking", new Booking());

//        return "Register"; // This corresponds to register.html
//    }
    	
    
    @PostMapping("/register")
    public String bookRoom(@ModelAttribute("roomBooking") RoomBooking booking,
                           @AuthenticationPrincipal UserDetails userDetails) {
    	  Optional<User> userOptional = userRepo.findByEmail(userDetails.getUsername());
          userOptional.ifPresent(booking::setUser);
          long days = ChronoUnit.DAYS.between(booking.getCheckinDate(), booking.getCheckoutDate());
          if (days <= 0) days = 1;
          if (booking.getPricePerNight() != null) {
        	    BigDecimal totalPrice = booking.getPricePerNight().multiply(BigDecimal.valueOf(days));
        	    booking.setTotalPrice(totalPrice);
        	}

          bookingRepo.save(booking);
          return "redirect:/bookings";
      }

    @GetMapping("/bookings")
    public String viewMyBookings(Model model,
                                 @AuthenticationPrincipal UserDetails userDetails) {
    	 Optional<User> userOptional = userRepo.findByEmail(userDetails.getUsername());
         if (userOptional.isPresent()) {
             model.addAttribute("bookings", bookingRepo.findByUser(userOptional.get()));
         } else {
             model.addAttribute("bookings", null);
         }
         return "CustomerRecords";
     }
    
    @GetMapping("/booking/edit/{id}")
    public String editBooking(@PathVariable Long id, Model model) {
        RoomBooking booking = bookingRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid booking ID"));
        model.addAttribute("roomBooking", booking);
        return "Register";
    }

    @PostMapping("/booking/update/{id}")
    public String updateBooking(@PathVariable Long id, @ModelAttribute("roomBooking") RoomBooking updatedBooking) {
        RoomBooking existing = bookingRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid booking ID"));
        existing.setRoomType(updatedBooking.getRoomType());
        existing.setCheckinDate(updatedBooking.getCheckinDate());
        existing.setPricePerNight(updatedBooking.getPricePerNight());
        existing.setTotalPrice(updatedBooking.getTotalPrice());
        existing.setServices(updatedBooking.getServices());
        long days = ChronoUnit.DAYS.between(existing.getCheckinDate(), existing.getCheckoutDate());
        if (days <= 0) days = 1;
        existing.setTotalPrice(existing.getPricePerNight().multiply(BigDecimal.valueOf(days)));
        bookingRepo.save(existing);
        return "redirect:/bookings";
    }

    @GetMapping("/booking/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingRepo.deleteById(id);
        return "redirect:/bookings";
    }
}


