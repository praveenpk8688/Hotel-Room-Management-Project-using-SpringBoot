package com.pk;

import java.time.LocalDate;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        HotelDAO hotelDAO = new HotelDAO();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n*** Hotel Booking System ***");
            System.out.println("1. Add Room");
            System.out.println("2. Add Customer");
            System.out.println("3. Book Room");
            System.out.println("4. Cancle Booking");
            System.out.println("5. View Available Rooms");
            System.out.println("6. View All Bookings");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter room number: ");
                    String roomNumber = scanner.next();
                    System.out.print("Enter room type: ");
                    String type = scanner.next();
                    System.out.print("Enter price per night: ");
                    double price = scanner.nextDouble();

                    Room room = new Room();
                    room.setRoomNumber(roomNumber);
                    room.setType(type);
                    room.setPricePerNight(price);
                    room.setAvailable(true);
                    hotelDAO.addRoom(room);
                    System.out.println("Room added successfully!");
                    break;

                case 2:
                    System.out.print("Enter customer name: ");
                    String name = scanner.next();
                    System.out.print("Enter email: ");
                    String email = scanner.next();
                    System.out.print("Enter phone: ");
                    String phone = scanner.next();

                    Customer customer = new Customer();
                    customer.setName(name);
                    customer.setEmail(email);
                    customer.setPhone(phone);
                    hotelDAO.addCustomer(customer);
                    System.out.println("Customer added successfully!");
                    break;

                case 3:
                    System.out.println("Available rooms:");
                    hotelDAO.getAvailableRooms().forEach(System.out::println);
                    System.out.print("Enter room ID to book a Room: ");
                    int roomId = scanner.nextInt();
                    System.out.print("Enter customer ID: ");
                    int customerId = scanner.nextInt();
                    System.out.print("Enter check-in date (YYYY-MM-DD): ");
                    String checkIn = scanner.next();
                    System.out.print("Enter check-out date (YYYY-MM-DD): ");
                    String checkOut = scanner.next();

                    try {
                        Booking booking = new Booking();
                        booking.setRoom(hotelDAO.getRoomById(roomId));
                        booking.setCustomer(hotelDAO.getCustomerById(customerId));
                        booking.setCheckIn(LocalDate.parse(checkIn));
                        booking.setCheckOut(LocalDate.parse(checkOut));
                        booking.calculateTotalPrice();

                        hotelDAO.addBooking(booking);
                        System.out.println("Room booked successfully!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("Enter booking ID to cancel: ");
                    int bookingId = scanner.nextInt();
                    hotelDAO.cancelBooking(bookingId);
                    break;

                case 5:
                    hotelDAO.getAvailableRooms().forEach(System.out::println);
                    break;

                case 6:
                    hotelDAO.getAllBookings().forEach(System.out::println);
                    break;

                case 7:
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!. Please try again");
            }
        }
    }
}
