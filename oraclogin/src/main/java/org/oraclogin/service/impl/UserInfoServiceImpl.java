package org.oraclogin.service.impl;

import java.util.List;

import org.oraclogin.model.UserInfo;
import org.oraclogin.model.UsersToken;
import org.oraclogin.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.oraclogin.dao.UserInfoDAO;


@Service("UserInfoService")
public class UserInfoServiceImpl implements UserInfoService {
	
	@Autowired
    private UserInfoDAO userInfoDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserInfo findUserInfo(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getUserRoles(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(UserInfo user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		System.out.println("userinfoserviceimpl password = " + user.getPassword());
		if(!userInfoDAO.save(user))
		{
			return false;
		}
		return true;
	}

	@Override
	public UserInfo findUserByEmail(String email) {
		System.out.println("serviceimpl email -> " +email);
		UserInfo userInfo = userInfoDAO.findUserByEmail(email);
		return userInfo;
	}

	@Override
	public UsersToken findUserByToken(String token) {
		UsersToken userToken = userInfoDAO.findUserByToken(token);
		return userToken;
	}

	@Override
	public boolean addPasswordToken(UsersToken userToken) {
		//UsersToken userToken;
		try
		{
			System.out.println("service impl adding token");
			userInfoDAO.addPasswordToken(userToken);
			System.out.println("service impl added token");
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Exception -> " + e);
			return false;
		}
	}

	@Override
	public boolean updatePassword(String un, String password) {
		try
		{
			return userInfoDAO.updatePassword(un, passwordEncoder.encode(password) );
		}
		catch (Exception e)
		{
			System.out.println("Exception -> " + e);
			return false;
		}
	}

}
