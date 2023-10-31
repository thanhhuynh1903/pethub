package com.pethub.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pethub.common.entity.Customer;



public class CustomerUserDetails implements UserDetails {
	private Customer customer;
	
	public CustomerUserDetails(Customer customer) {
		this.customer = customer;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return customer.getPassword();
	}

	@Override
	public String getUsername() {
		return customer.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return customer.isEnabled();
	}

	public String getFullName() {
		return customer.getFirstName() + " " + customer.getLastName();
	}

	public String getFirstName(){
		String arr[] = customer.getFirstName().split(" ", 2);
		return arr[0];
	}
	
	public Customer getCustomer() {
		return this.customer;
	}
	
	public String getAddress() {
		return this.customer.getAddressLine1() + ", " + 
				this.customer.getAddressLine2() + ", " +
				this.customer.getCity() + ", " +
				this.customer.getState()
				;
	}
}
