//package com.springBoot.SecurityConfig;
 //
 //import org.springframework.beans.factory.annotation.Autowired;
 //import org.springframework.context.annotation.Bean;
 //import org.springframework.context.annotation.Configuration;
 //import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 //import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
 //import org.springframework.security.core.userdetails.UserDetailsService;
 //import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 //import org.springframework.security.crypto.password.PasswordEncoder;
 //import org.springframework.security.web.SecurityFilterChain;
 //
 //import com.springBoot.User.UserService;
 //
 //@Configuration
 //@EnableWebSecurity
 //public class SecurityConfig {
 // @Autowired private UserService userService;
 //
 // @Bean
 // PasswordEncoder passwordEncoder() {
 // return new BCryptPasswordEncoder();
 // }
 //
 // @Bean
 // SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
 // http
 // .authorizeHttpRequests(auth -> auth
 // .requestMatchers("/signup", "/login", "/css/**", "/js/**").permitAll()
 // .anyRequest().authenticated()
 // )
 // .formLogin(form -> form
 // .loginPage("/login")
 // .defaultSuccessUrl("/home", true)
 // .permitAll()
 // )
 // .logout(logout -> logout.logoutSuccessUrl("/login").permitAll());
 // return http.build();
 // }
 //
 // @Bean
 // UserDetailsService userDetailsService() {
 // return userService;
 // }
 //}

//////////////////////////////////////////////////////////////////////////////UP/////////////////////////////////
// package com.springBoot.SecurityConfig;
//
//import org.springframework.beans.factory.annotation.Autowired;
//// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
//
// import com.springBoot.User.UserService;
//
// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {
//
//	 @Autowired
//	    private UserService userService;
//
//	    @Bean
//	     BCryptPasswordEncoder passwordEncoder() {
//	        return new BCryptPasswordEncoder();
//	    }
//
//	    
//	    @Bean
//	     DaoAuthenticationProvider authenticationProvider() {
//	        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//	        auth.setUserDetailsService(userService);
//	        auth.setPasswordEncoder(passwordEncoder());
//	        return auth;
//	    }
//	    
//	    @Bean
//	     SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	        http
//	            .authorizeHttpRequests((authorize) -> authorize
//	                .requestMatchers("/", "/home", "/css/**", "/js/**", "/images/**", "/signup").permitAll() // Allow home page and static resources without authentication
//	                .anyRequest().authenticated() // All other requests need authentication
//	            )
//	            .formLogin((formLogin) -> formLogin
//	                .loginPage("/login") // Specify your custom login page
//	                .permitAll() // Allow access to the login page without being logged in
//	                .defaultSuccessUrl("/home", true) // Redirect to home page after successful login
//	                .failureUrl("/login?error=true") // Redirect back to login page with error parameter on failure
//	            )
//	            .logout((logout) -> logout
//	                .permitAll() // Allow access to the logout URL
//	                .logoutSuccessUrl("/login?logout=true") // Redirect to login page with logout parameter after successful logout
//	            );
//	        return http.build();
//	    }
//
//  @Bean
//  UserDetailsService userDetailsService(UserService userService) {
//  return userService;
//  }
// }

//////////////////////////////////////////////////////////////////////////////////////////////////////


package com.springBoot.SecurityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.springBoot.User.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    	DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/home", "/login", "/signup", "/css/**", "/js/**").permitAll()
                .requestMatchers("/register", "/bookings/**").hasRole("USER")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            );

        return http.build();
    }
}
