package com.rdthelper.rdthelper.Config;

import com.rdthelper.rdthelper.Filter.JWTAuthFilter;
import com.rdthelper.rdthelper.Filter.JWTLoginFilter;
import com.rdthelper.rdthelper.Service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        ExceptionMappingAuthenticationFailureHandler exceptionMappingAuthenticationFailureHandler = new ExceptionMappingAuthenticationFailureHandler();
        Map<String, String> map = new HashMap<String, String>();

        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler(){
        return new RefererAuthentificationSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Configuration
    @Order(1)
    public class ApiSecurityConfig extends WebSecurityConfigurerAdapter{

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider());
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf()
                    .and()
                    .cors()
                    .disable()
                    .antMatcher("/api/**").authorizeRequests()
                    .antMatchers("/api/login").permitAll()
                    .antMatchers("/api/signup").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .addFilterBefore(new JWTLoginFilter("/api/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(new JWTAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        }
    }

    @Configuration
    @Order(2)
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider());
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.csrf()
                    .and()
                    .cors()
                    .disable()
                    .antMatcher("/web/**").authorizeRequests()
                    .antMatchers("/web/login").permitAll()
                    .antMatchers("/web/signup").permitAll()
                    .antMatchers("/web/perform_signup").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/web/login")
                    .failureHandler(authenticationFailureHandler())
                    .defaultSuccessUrl("/web/home", true)
                    .and()
                    .addFilterBefore(new JWTLoginFilter("/api/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(new JWTAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        }
    }




}
