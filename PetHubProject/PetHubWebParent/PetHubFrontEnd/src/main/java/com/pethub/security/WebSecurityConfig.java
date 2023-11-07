package com.pethub.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.pethub.security.oauth.CustomerOAuth2UserService;
import com.pethub.security.oauth.OAuth2LoginSuccessHandler;

import jakarta.mail.Session;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private CustomerOAuth2UserService oAuth2UserService;

	@Autowired
	@Lazy
	private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

	@Autowired
	@Lazy
	private DatabaseLoginSuccessHandler databaseLoginSuccessHandler;

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomerUserDetailsService();
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
		http.authorizeHttpRequests(authz -> authz.requestMatchers("/images/**", "/js/**", "/webjars/**").permitAll()
				.requestMatchers("/account_details", "/update_account_details", "/orders/**", "/cart",
						"/address_book/**", "/checkout", "/place_order", "/reviews/**",
						"/process_paypal_order")
				.authenticated().anyRequest().permitAll())
				.oauth2Login(oauth2 -> oauth2.loginPage("/login").userInfoEndpoint().userService(oAuth2UserService)
						.and().successHandler(oAuth2LoginSuccessHandler))
				.formLogin(formLogin -> formLogin.loginPage("/login").usernameParameter("email")
						.successHandler(databaseLoginSuccessHandler).permitAll())
				.logout(logout -> logout.logoutUrl("/logout") // specify your logout URL here, if different from default
						.permitAll())
				.rememberMe(rememberMe -> rememberMe.key("dfsafhfjhlkjdsjfkdasjf_123132131231123898")// specify your
																										// secret key
						.tokenValiditySeconds(7 * 24 * 60 * 60) // specify token validity time in seconds
						.userDetailsService(userDetailsService()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
		return http.build();
	}
}
