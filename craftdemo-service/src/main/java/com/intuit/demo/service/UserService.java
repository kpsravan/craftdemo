package com.intuit.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intuit.demo.dao.BaseDAO;
import com.intuit.demo.model.User;

@Service
public class UserService {
	@Autowired
	private BaseDAO baseDAO;

	public User getUserByUserName(String userName) {
		return baseDAO.findUnique(User.class,
				"SELECT u FROM User u WHERE u.userName=?", userName);
	}

}
