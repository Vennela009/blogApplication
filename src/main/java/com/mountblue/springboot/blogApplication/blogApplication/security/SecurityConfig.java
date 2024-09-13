package com.mountblue.springboot.blogApplication.blogApplication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("select name,password,1 as enabled from user where name=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select name,role from user where name=?");
        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(configurer->
                configurer
                        .requestMatchers("/user/new","/user/register","/*/all","/posts/*","/comment/*","/posts/sort/*").permitAll()
                        .requestMatchers("/showLoginPage","/*/new","/*/update/**","/*/delete/**").hasAnyRole("ADMIN","AUTHOR")
                        .anyRequest().authenticated()
                )
                .formLogin(form->
                        form
                                .loginPage("/showLoginPage")
                                .loginProcessingUrl("/authenticatedTheUser")
                                .defaultSuccessUrl("/posts/all",true)
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/showLoginPage?logout")
                                .deleteCookies("JSESSIONID")
                                .invalidateHttpSession(true)
                                .permitAll()
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint((request, response, authException) -> response.sendRedirect("/showLoginPage"))
                );


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
