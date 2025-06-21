//package com.springBoot.Room;
//
// import java.util.List;
//
// import org.springframework.data.jpa.repository.JpaRepository;
//
// //import com.springBoot.User.User;
//
// public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {
//  List<RoomBooking> findByUserId(Long userId);
// }


package com.springBoot.Room;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springBoot.User.User;

import java.util.List;

public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {
    List<RoomBooking> findByUser(User user);
    List<RoomBooking> findByUserIdOrderByIdAsc(Long userId);

}
