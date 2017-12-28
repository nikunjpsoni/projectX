package org.oraclogin.dao.impl;

import java.util.List;

import javax.sql.DataSource;
 
import org.oraclogin.dao.UserInfoDAO;
import org.oraclogin.mapper.UserInfoMapper;
import org.oraclogin.mapper.UsersTokenMapper;
import org.oraclogin.model.UserInfo;
import org.oraclogin.model.UsersToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
@Service
@Transactional
public class UserInfoDAOImpl extends JdbcDaoSupport implements UserInfoDAO {
 
    @Autowired
    public UserInfoDAOImpl(DataSource dataSource) {
        this.setDataSource(dataSource);
    }
  
 
    @Override
    public UserInfo findUserInfo(String userName) {
        String sql = "Select u.userName, u.Password, u.email from Users u where u.userName = ? ";
 
        Object[] params = new Object[] { userName };
        UserInfoMapper mapper = new UserInfoMapper();
        try {
            UserInfo userInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return userInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
 
 
    @Override
    public List<String> getUserRoles(String userName) {
        String sql = "Select r.User_Role "//
                + " from User_Roles r where r.Username = ? ";
         
        Object[] params = new Object[] { userName };
         
        List<String> roles = this.getJdbcTemplate().queryForList(sql,params, String.class);
         
        return roles;
    }

	@Override
	public boolean save(UserInfo user) {
		System.out.println(user.getUserName() + "- pswd_-" + user.getPassword());
		//user.setPassword("1234");
		//user.setUserName("test1");
//		int enable = 1;
		String sql = "Insert into users (userName, password, email) VALUES (?, ?, ?)";
		try {
			this.getJdbcTemplate().update(sql, user.getUserName(), user.getPassword(), user.getEmail());
			return true;
		} catch (Exception e){
			System.out.println("Exception occur " + e);
			return false;
		}
	}


	@Override
	public UserInfo findUserByEmail(String email) {
		String sql = "Select u.Username "//
                + " from Users u where u.email = ? ";
 
        Object[] params = new Object[] { email };
       // UserInfoMapper mapper = new UserInfoMapper();
        try {
        	System.out.println("daoimpl email -> " + email);
            String userName = (String) this.getJdbcTemplate().queryForObject(sql, params, String.class);
            System.out.println("daoimpl found user -> " + userName);
            UserInfo userInfo = new UserInfo(userName, email);
            return userInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}


	//find token related to user
	@Override
	public UsersToken findUserByToken(String token) {
		String sql = "Select * from users_token u where u.token = ? ";
 
        Object[] params = new Object[] { token };
        UsersTokenMapper mapper = new UsersTokenMapper();
        try {
            UsersToken userToken = (UsersToken) this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return userToken;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
	}

	@Override
	public boolean addPasswordToken(UsersToken userToken) {
		String sql = "Insert into users_token (userName, email, token) VALUES (?, ?, ?)";
		try {
			System.out.println("daoimpl adding token");
			this.getJdbcTemplate().update(sql, userToken.getUserName(), userToken.getEmail(), userToken.getToken());
			System.out.println("Saved user token");
			return true;
		} catch (Exception e){
			System.out.println("Exception occur " + e);
			return false;
		}
	}


	@Override
	public boolean updatePassword(String un, String password) {
		System.out.println("un -> "+ un +"pswd -> "+password);
		String sql = "UPDATE users u SET u.password = ? WHERE u.username = ?";
		//delete token from database if password is updated
		String delsql = "DELETE * from users_token u WHERE u.userName = ?";
		try{
			this.getJdbcTemplate().update(sql, password, un);
			this.getJdbcTemplate().update(delsql, un);
			return true;
		} catch (Exception e)
		{
			System.out.println("Exception occur " + e);
			return false;
		}
		
	}
     
}