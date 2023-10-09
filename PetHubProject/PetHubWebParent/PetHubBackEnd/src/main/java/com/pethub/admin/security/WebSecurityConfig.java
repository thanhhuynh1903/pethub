package com.pethub.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.pethub.admin.user.PetHubUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		return new PetHubUserDetailsService();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authz -> authz.
				requestMatchers("/images/**", "/js/**", "/webjars/**", "/login.css", "/landing", "/register/**").permitAll().
				requestMatchers("/states/list_by_country/**").hasAnyAuthority("Admin", "Salesperson")
				.requestMatchers("/users/**", "/settings/**", "/countries/**", "/states/**").hasAuthority("Admin")
				.requestMatchers("/categories/**", "/brands/**").hasAnyAuthority("Admin", "Editor")

				.requestMatchers("/products/new", "/products/delete/**").hasAnyAuthority("Admin", "Editor")

				.requestMatchers("/products/edit/**", "/products/save", "/products/check_unique")
				.hasAnyAuthority("Admin", "Editor", "Salesperson")

				.requestMatchers("/products", "/products/", "/products/detail/**", "/products/page/**")
				.hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")

				.requestMatchers("/products/**").hasAnyAuthority("Admin", "Editor")

				.requestMatchers("/orders", "/orders/", "/orders/page/**", "/orders/detail/**")
				.hasAnyAuthority("Admin", "Salesperson", "Shipper")

				.requestMatchers("/products/detail/**", "/customers/detail/**")
				.hasAnyAuthority("Admin", "Editor", "Salesperson", "Assistant")

				.requestMatchers("/customers/**", "/orders/**", "/get_shipping_cost", "/reports/**")
				.hasAnyAuthority("Admin", "Salesperson")

				.requestMatchers("/orders_shipper/update/**").hasAuthority("Shipper")

				.requestMatchers("/reviews/**").hasAnyAuthority("Admin", "Assistant").anyRequest().authenticated())
				.formLogin(formLogin -> formLogin.loginPage("/login").usernameParameter("email").permitAll())
				.logout(logout -> logout.logoutUrl("/logout").permitAll())
				.rememberMe(rememberMe -> rememberMe.key("$2a$10$Pxeg5oCBEHaxcROzD5gy5e7T6Q.mxLI9hPdPznDQ/5zJXcusv8VEm")
						.tokenValiditySeconds(7 * 24 * 60 * 60) // specify token validity time in seconds
						.userDetailsService(userDetailsService()) // specify your UserDetailsService here
				);
		return http.build();
	}

}
