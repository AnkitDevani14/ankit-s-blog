package com.project.blog.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select username as principal, password as credentails, true from user where username=?")
		.authoritiesByUsernameQuery("select username as principal, roles as role from user where username=?");
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		// TODO Auto-generated method stub
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/","/HomeAdmin{id}","/Edu{id}","/Tech{id}","/Category{id}","/CategoryImage{id}","/newText{id}","/newImage{id}").permitAll()
			.antMatchers("/admin","/profile","/posts","/text","/own","/allPost","/image","/textPosts","/ImagePosts","/textAllPosts","/ImageAllPosts","/dailyPost","/addCategory","/categoryDelete{id}","/text{id}","/textDelete{id}","/image{id}","/textEdit{id}","/textEdit{id}","/imageDelete{id}","/imageEdit{id}","/imageEdit{id}").hasRole("ADMIN")
			.antMatchers("/register").permitAll()
			.and()
			.formLogin()
			.loginProcessingUrl("/login")
			.loginPage("/login").permitAll()
			.usernameParameter("txtUsername")
			.passwordParameter("txtPassword")
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
	}
}
