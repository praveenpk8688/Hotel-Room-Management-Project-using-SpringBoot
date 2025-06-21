//package com.springBoot.User;
//
// import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;
//
//import jakarta.transaction.Transactional;
//
// @Service
// public class UserService implements UserDetailsService {
//  @Autowired
//  private UserRepository userRepo;
//  @Autowired
//  private PasswordEncoder passwordEncoder;
//
//  public boolean emailExists(String email) {
//  return userRepo.findByEmail(email).isPresent();
//  }
//
//  public User registerUser(User user) {
//	    System.out.println("Plain password before encoding: " + user.getPassword());
//	    user.setPassword(passwordEncoder.encode(user.getPassword()));
//	    System.out.println("Encoded password after encoding: " + user.getPassword());
//	    return userRepo.save(user);
//	}
//
//  public User findByEmail(String email) {
//  return userRepo.findByEmail(email).orElse(null);
//  }
//
//  public User findById(Long id) {
//  return userRepo.findById(id).orElse(null);
//  }
//
//  @Override
//  @Transactional
//  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//      System.out.println("loadUserByUsername called with username: " + username);
//      Optional<User> userOptional = userRepo.findByEmail(username);
//      if (userOptional.isEmpty()) {
//          System.out.println("User not found in the database");
//          throw new UsernameNotFoundException("User not found");
//      }
//
//      User user = userOptional.get();
//      user.setLastLogin(LocalDateTime.now()); // Update the lastLogin field
//      userRepo.save(user); // Save the updated user object
//
//      System.out.println("User found with email: " + user.getEmail());
//      System.out.println("Encoded password from database: " + user.getPassword());
//
//      return new org.springframework.security.core.userdetails.User(
//          user.getEmail(),
//          user.getPassword(),
//          List.of(new SimpleGrantedAuthority("ROLE_USER"))
//      );
//  }
//  
// }




/////////////////////////////////////////////////////UP./////////////////////////////////////////////

package com.springBoot.User;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
            .map(user -> new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getAuthorities()
            ))
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }
}



