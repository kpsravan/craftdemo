package com.intuit.demo.api.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.intuit.demo.model.User;
import com.intuit.demo.service.UserService;

@Component
public class DummyUserService implements UserDetailsService {
	@Autowired
	private UserService userService;

	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		User u = userService.getUserByUserName(userName);
		if (u == null) {
			return null;
		}
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
				"ROLE_USER");
		UserDetails ud = new org.springframework.security.core.userdetails.User(
				userName, userName, Arrays.asList(authority));
		return new DummyUser(u, ud);
	}
}
