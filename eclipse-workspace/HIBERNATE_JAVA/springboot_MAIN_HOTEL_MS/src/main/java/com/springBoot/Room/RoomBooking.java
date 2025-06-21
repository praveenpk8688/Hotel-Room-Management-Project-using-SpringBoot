
package com.springBoot.Room;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import com.springBoot.User.User;

import jakarta.persistence.*;

@Entity
@Table(name = "room_booking")
public class RoomBooking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String guestName;
	private String guestEmail;
	private String guestPhone;

	@Column(name = "room_type")
	private String roomType;

	private String services;

	@Column(name = "checkin_date")
	private LocalDate checkinDate;

	@Column(name = "checkout_date")
	private LocalDate checkoutDate;

	@Column(name = "checkin_time")
	private LocalTime checkinTime;

	@Column(name = "checkout_time")
	private LocalTime checkoutTime;

	@Column(precision = 10, scale = 2)
	private BigDecimal pricePerNight;

	@Column(precision = 10, scale = 2)
	private BigDecimal totalPrice;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getGuestEmail() {
		return guestEmail;
	}

	public void setGuestEmail(String guestEmail) {
		this.guestEmail = guestEmail;
	}

	public String getGuestPhone() {
		return guestPhone;
	}

	public void setGuestPhone(String guestPhone) {
		this.guestPhone = guestPhone;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public LocalDate getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(LocalDate checkinDate) {
		this.checkinDate = checkinDate;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public LocalTime getCheckinTime() {
		return checkinTime;
	}

	public void setCheckinTime(LocalTime checkinTime) {
		this.checkinTime = checkinTime;
	}

	public LocalTime getCheckoutTime() {
		return checkoutTime;
	}

	public void setCheckoutTime(LocalTime checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setPricePerNight(BigDecimal pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public BigDecimal getPricePerNight() {
		return pricePerNight;
	}

}
