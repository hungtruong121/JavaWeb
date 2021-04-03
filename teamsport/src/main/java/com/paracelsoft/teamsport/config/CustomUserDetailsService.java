package com.paracelsoft.teamsport.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.paracelsoft.teamsport.entity.UserLogin;
import com.paracelsoft.teamsport.entity.UserRole;
import com.paracelsoft.teamsport.repository.UserLoginRepository;
import com.paracelsoft.teamsport.service.AuthenOfbizService;
import com.paracelsoft.teamsport.service.UserService;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserService userService;
	@Autowired
	AuthenOfbizService usersLoginService;

	private UserLoginRepository userLoginRepository;

	public CustomUserDetailsService(UserLoginRepository users) {
		this.userLoginRepository = users;
	}

	@Override
	public UserDetail loadUserByUsername(String loginUserName) throws UsernameNotFoundException {

		try {
			Optional<UserLogin> userLogin = userLoginRepository.findByUserNameAndIsActive(loginUserName, 1);

			if (!userLogin.isPresent()) {
				UsernameNotFoundException ex = new UsernameNotFoundException("userName not found:");
				System.out.println("CustomUserDetailsService Don't have RequestAttributes");
				throw ex;
			}

			Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
			UserRole userRole = userService.getRoleNameByUserId(userLogin.get().getUserId());
			if(userRole != null) {
				authorities.add(new SimpleGrantedAuthority(userRole.getUserRoleName()));
				UserDetail user = new UserDetail(userLogin.get().getCurrentPassword(), loginUserName,
						getGrantedPermissions(userRole), authorities);
				return user;
			}
			UserDetail user = new UserDetail(userLogin.get().getCurrentPassword(), loginUserName,null, authorities);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<String> getGrantedPermissions(UserRole userRole) {
		List<String> permissions = new ArrayList<String>();
		if(userRole != null) {
			permissions.add(userRole.getUserRoleName());
		}
		return permissions;
	}

}
