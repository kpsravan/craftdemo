package com.intuit.demo.api.security;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.intuit.demo.api.security.DummyUser;
import com.intuit.demo.api.security.DummyUserService;
import com.intuit.demo.model.User;
import com.intuit.demo.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class DummyUserServiceTest {

	@Mock
	private UserService userService;
	@InjectMocks
	private DummyUserService dUserService;

	@Before
	public void setUp() {
		User user = new User();
		user.setFirstName("user1FN");
		user.setLastName("user1LN");
		user.setUserName("user1");
		Mockito.when(userService.getUserByUserName(Mockito.anyString())).thenReturn(user);
	}

	@Test
	public void testLoadUserByUsernameOk() {
		UserDetails dUser = dUserService.loadUserByUsername("user");
		Assert.assertTrue(dUser instanceof DummyUser);
		Collection<? extends GrantedAuthority> auths = dUser.getAuthorities();
		Assert.assertTrue(auths.size() == 1);
		GrantedAuthority auth = auths.iterator().next();
		Assert.assertTrue("ROLE_USER".equals(auth.getAuthority()));
	}
}
