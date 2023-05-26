package ca.sheridancollege.sakhiyap.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.sakhiyap.beans.BookEvent;
import ca.sheridancollege.sakhiyap.beans.User;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class Event_Security_Repository {
	
	private NamedParameterJdbcTemplate jdbc;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// import user from my beans package
		public User findUserAccount(String userName) {

			String query = "SELECT * FROM sec_user WHERE username=:userName";
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("userName", userName);

			ArrayList<User> users = (ArrayList<User>) jdbc.query(query, parameters,
					new BeanPropertyRowMapper<User>(User.class));

			return (users.size() > 0) ? users.get(0): null;

		}

		public List<String> getRolesById(long userId) {
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			String query = "SELECT user_role.userId, sec_role.roleName " + "FROM user_role, sec_role WHERE "
					+ "user_role.roleId=sec_role.roleId and userId=:id";
			parameters.addValue("id", userId);
			ArrayList<String> roles = new ArrayList<String>();
			List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
			for (Map<String, Object> row : rows) {
				roles.add((String) row.get("roleName"));
			}
			return roles;
		}

	public void addUser(String userName, String password) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "insert into SEC_User " + "(userName, encryptedPassword, ENABLED)"
				+ " values (:userName, :encryptedPassword, 1)";
		parameters.addValue("userName", userName);
		parameters.addValue("encryptedPassword", passwordEncoder().encode(password));
		jdbc.update(query, parameters);
	}

	public void addRole(long userId, long roleId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "insert into user_role (userId, roleId)"
					+ "values (:userId, :roleId);";
			parameters.addValue("userId", userId);
			parameters.addValue("roleId", roleId);
			jdbc.update(query, parameters);

	}

	public ArrayList<User> getGuestName() {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String query = "Select userName from SEC_USER where userId in (select userId from USER_ROLE where roleId=1) order by username";

		ArrayList<User> usr = (ArrayList<User>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<User>(User.class));
		return usr;
	}
}
