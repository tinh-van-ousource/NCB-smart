/**
 * 
 */
package com.tvo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tvo.dao.AppUserDAO;
import com.tvo.model.Role;
import com.tvo.model.User;
import com.tvo.model.UserDetailsImpl;

/**
 * @author Ace
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AppUserDAO appUserDAO;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = appUserDAO.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		//manyToMany
//		Set<Role> roles = user.getRoles();
//		for (Role role : roles) {
//			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
//		}
		//moi nguoi 1 quyen
		Role role = user.getRole();
		grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		 return new UserDetailsImpl(user, grantedAuthorities); 
		/*
		 * return new
		 * org.springframework.security.core.userdetails.User(user.getEmail(),
		 * user.getPassword(), grantedAuthorities);
		 */
	}

}
