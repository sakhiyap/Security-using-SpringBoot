package ca.sheridancollege.sakhiyap.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ca.sheridancollege.sakhiyap.repository.Event_Repository;
import ca.sheridancollege.sakhiyap.repository.Event_Security_Repository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private Event_Security_Repository eventSecRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Find the user based on the user name
		ca.sheridancollege.sakhiyap.beans.User user = eventSecRepo.findUserAccount(username);
		//If the user doesn't exist then throw an exception
		if (user == null) {
			System.out.println("User not found.");
			throw new UsernameNotFoundException("User Not Found");
		}
		//Get a list of the roles for that user
		List<String> roles = eventSecRepo.getRolesById(user.getUserId());
		//Change the list of roles into a list of Granted Authorities
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			grantList.add(new SimpleGrantedAuthority(role));
		}
		//Create a Spring User based on the information above
		//import this user from spring security core
		User springUser = new User(user.getUserName(), user.getEncryptedPassword(), grantList);
		return (UserDetails) springUser;
	}
	
	
}
