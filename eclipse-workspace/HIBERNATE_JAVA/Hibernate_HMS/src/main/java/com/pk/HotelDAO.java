package com.pk;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import jakarta.transaction.Transaction;

import java.util.List;

public class HotelDAO {

    private SessionFactory sessionFactory;

    public HotelDAO() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Room.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Booking.class)
                .buildSessionFactory();
    }

    public Room getRoomById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Room.class, id);
        }
    }

    public Customer getCustomerById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Customer.class, id);
        }
    }

    public void addRoom(Room room) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(room);
            session.getTransaction().commit();
        }
    }

    public void addCustomer(Customer customer) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(customer);
            session.getTransaction().commit();
        }
    }
    public void cancelBooking(int bookingId) {
        org.hibernate.Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            // Fetch the booking using the provided booking ID
            Booking booking = session.get(Booking.class, bookingId);

            if (booking != null) {
                // Update the room's availability
                Room room = booking.getRoom();
                if (room != null) {
                    room.setAvailable(true);
                    session.merge(room);
                }

                // Delete the booking
                session.delete(booking);
                transaction.commit();
                System.out.println("Booking with ID " + bookingId + " has been canceled successfully.");
            } else {
                System.out.println("No booking found with ID " + bookingId);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            System.out.println("An error occurred while canceling the booking.");
        }
    }


    public void addBooking(Booking booking) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(booking);
            Room room = session.get(Room.class, booking.getRoom().getId());
            if (room != null) {
                room.setAvailable(false);
                session.merge(room);
            }
            session.getTransaction().commit();
        }
    }

    public List<Room> getAvailableRooms() {
        try (Session session = sessionFactory.openSession()) {
            // Fetch available rooms
            List<Room> availableRooms = session.createQuery("from Room where available = true", Room.class).list();

            if (availableRooms.isEmpty()) {
                // No rooms available
                System.out.println("No rooms available. Please create rooms to show available rooms.");
            } else {
                // Display available rooms
                System.out.println("Available Rooms:");
                for (Room room : availableRooms) {
                    System.out.println(room);
                }
            }
            return availableRooms;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while fetching available rooms.");
            return null;
        }
    }


    public List<Booking> getAllBookings() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Booking", Booking.class).list();
        }
    }
}
