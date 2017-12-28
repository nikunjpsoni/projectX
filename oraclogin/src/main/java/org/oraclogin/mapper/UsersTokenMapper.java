package org.oraclogin.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.oraclogin.model.UsersToken;
import org.springframework.jdbc.core.RowMapper;

public class UsersTokenMapper implements RowMapper<UsersToken> {

	@Override
	public UsersToken mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		String username = rs.getString("userName");
		String email = rs.getString("email");
		String token = rs.getString("token");
		
		return new UsersToken(username, email, token);
	}

}
