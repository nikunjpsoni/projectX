package org.oraclogin.dao;

import java.util.List;

import org.oraclogin.model.UserInfo;
import org.oraclogin.model.UsersToken;
 
public interface UserInfoDAO {
     
    public UserInfo findUserInfo(String userName);
     
    // [USER,AMIN,..]
    public List<String> getUserRoles(String userName);

	public boolean save(UserInfo user);
	
	public UserInfo findUserByEmail(String email);
	
	//find user from user_token table
	public UsersToken findUserByToken(String token);

	public boolean addPasswordToken(UsersToken userToken);

	public boolean updatePassword(String un, String password);  
        
}