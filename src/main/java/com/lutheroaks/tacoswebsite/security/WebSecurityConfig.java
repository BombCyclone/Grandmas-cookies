package com.lutheroaks.tacoswebsite.security;

import com.lutheroaks.tacoswebsite.entities.member.MemberDetailsServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Bean
    public MemberDetailsServiceImpl memberDetailsServiceImpl() {
        return new MemberDetailsServiceImpl();
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(memberDetailsServiceImpl());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
     
    @Override
    protected void configure(final HttpSecurity http)throws Exception {
        http
            .csrf().disable()
            .cors().disable()
            .authorizeRequests()
                .antMatchers("/login").anonymous()
                .antMatchers("/profile").authenticated()
                .and()
			.formLogin()
                .defaultSuccessUrl("/index", true)
                .permitAll()
				.and()
			.logout()
				.permitAll(); 
    }
}
