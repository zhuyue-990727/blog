package com.zy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zy.dao.UserRepository;
import com.zy.po.User;
import com.zy.util.MD5Utils;

@Service
public class UserserviceImpl implements Userservice{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User checkUser(String username, String password) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUsernameAndPassword(username, MD5Utils.getInstance().getMD5((password)));
		return user;
	}

}
