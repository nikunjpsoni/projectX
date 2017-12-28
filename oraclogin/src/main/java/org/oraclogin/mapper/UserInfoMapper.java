package org.oraclogin.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
 
import org.oraclogin.model.UserInfo;
import org.springframework.jdbc.core.RowMapper;
 
public class UserInfoMapper implements RowMapper<UserInfo> {
 
    @Override
    public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
 
        String userName = rs.getString("userName");
        String password = rs.getString("password");
        String email = rs.getString("email");
 
        return new UserInfo(userName, password, email);
    }
 
}