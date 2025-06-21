
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

	@PostMapping("/register")
	public String bookRoom(@ModelAttribute("roomBooking") RoomBooking booking,
			@AuthenticationPrincipal UserDetails userDetails) {
		Optional<User> userOptional = userRepo.findByEmail(userDetails.getUsername());
		userOptional.ifPresent(booking::setUser);
		long days = ChronoUnit.DAYS.between(booking.getCheckinDate(), booking.getCheckoutDate());
		if (days <= 0)
			days = 1;
		if (booking.getPricePerNight() != null) {
			BigDecimal totalPrice = booking.getPricePerNight().multiply(BigDecimal.valueOf(days));
			booking.setTotalPrice(totalPrice);
		}

		bookingRepo.save(booking);
		return "redirect:/bookings";
	}

	@GetMapping("/bookings")
	public String viewMyBookings(Model model, @AuthenticationPrincipal UserDetails userDetails) {
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
		RoomBooking booking = bookingRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid booking ID"));
		model.addAttribute("roomBooking", booking);
		return "Register";
	}

	@PostMapping("/booking/update/{id}")
	public String updateBooking(@PathVariable Long id, @ModelAttribute("roomBooking") RoomBooking updatedBooking) {
		RoomBooking existing = bookingRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid booking ID"));
		existing.setRoomType(updatedBooking.getRoomType());
		existing.setCheckinDate(updatedBooking.getCheckinDate());
		existing.setPricePerNight(updatedBooking.getPricePerNight());
		existing.setTotalPrice(updatedBooking.getTotalPrice());
		existing.setServices(updatedBooking.getServices());
		long days = ChronoUnit.DAYS.between(existing.getCheckinDate(), existing.getCheckoutDate());
		if (days <= 0)
			days = 1;
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
