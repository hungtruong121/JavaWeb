package com.paracelsoft.teamsport.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.paracelsoft.teamsport.security.jwt.JwtAuthenticationEntryPoint;
import com.paracelsoft.teamsport.security.jwt.JwtSecurityConfigurer;
import com.paracelsoft.teamsport.security.jwt.JwtTokenProvider;


@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Bean
	public MyCustomAuthSuccessHandler authSuccessHandler() {
	   return new MyCustomAuthSuccessHandler();
	}
	
	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;
	
	@Autowired
	@Qualifier("passwordEncoder")
	CustomPasswordEncoder passwordEncoder;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable().csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.and()
				.authorizeRequests()
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/api/v1/login").permitAll()
				.antMatchers("/api/v1/.well-known/apple-app-site-association").permitAll()
				.antMatchers("/api/v1/invite").permitAll()
				.antMatchers("/api/v1/user/create").permitAll()
				.antMatchers("/api/v1/user/active").permitAll()
				.antMatchers("/api/v1/user/forgotpass").permitAll()//remove
				.antMatchers("/api/v1/user/changepass").permitAll()//remove
				.antMatchers("/api/v1/user/logout").permitAll()//remove
				.antMatchers("/api/v1/user/changepass/otp").permitAll()//remove
				.antMatchers("/api/v1/sport/**").permitAll()//remove
				.antMatchers("/api/v1/user/**").permitAll()//remove
				.antMatchers("/api/v1/user/admin/*").permitAll()//remove
				.antMatchers("/api/v1/admin/**").permitAll()//remove
				.antMatchers("/api/v1/team/admin/*").permitAll()//remove
				.antMatchers("/api/v1/admin/paymenthistory/*").permitAll()//remove
				.antMatchers("/api/v1/team/**").permitAll()//remove
				.antMatchers("/api/v1/file/**").permitAll()//remove
				.antMatchers("/api/v1/file/download/**").permitAll()
				.antMatchers("/api/v1/folder/download/**").permitAll()
				.antMatchers("/api/v1/logout").permitAll()//remove
				.antMatchers("/api/v1/admin/paymenthistory/update").permitAll()
				.anyRequest().authenticated()
				.and()
				.exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
				.and()
				.formLogin().loginPage("/login").permitAll().successHandler(authSuccessHandler())
				.failureUrl("/login-error").usernameParameter("username").passwordParameter("password")
				.and()
					.logout().logoutSuccessUrl("/login").permitAll()
				.and()
				.apply(new JwtSecurityConfigurer(jwtTokenProvider));
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		super.configure(auth);
	}
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
//        .addResourceHandler("/resources/**")
//          .addResourceLocations(new File("E:/Paracel/TeamSport_API/SourceCode/team-sport/Source Code/Web/teamsport/src/main/resources").toURI().toString()); 
        

        .addResourceHandler("/files/**")
        .addResourceLocations("file:E:/Paracel/TeamSport_API/SourceCode/team-sport/Source Code/Web/teamsport/src/main/resources");
    }
}
