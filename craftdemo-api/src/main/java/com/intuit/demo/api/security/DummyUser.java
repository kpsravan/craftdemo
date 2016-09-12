package com.intuit.demo.api.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.intuit.demo.model.User;

public class DummyUser implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private UserDetails sUser;

	public User getUser() {
		return user;
	}

	public DummyUser(User user, UserDetails sUser) {
		this.user = user;
		this.sUser = sUser;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return sUser.getAuthorities();
	}

	public String getPassword() {
		return sUser.getPassword();
	}

	public String getUsername() {
		return sUser.getUsername();
	}

	public boolean isAccountNonExpired() {
		return sUser.isAccountNonExpired();
	}

	public boolean isAccountNonLocked() {
		return sUser.isAccountNonLocked();
	}

	public boolean isCredentialsNonExpired() {
		return sUser.isCredentialsNonExpired();
	}

	public boolean isEnabled() {
		return true;
	}

}
