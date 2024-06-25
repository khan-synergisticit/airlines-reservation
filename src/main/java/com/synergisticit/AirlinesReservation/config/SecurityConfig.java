package com.synergisticit.AirlinesReservation.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Autowired
    DataSource dataSource;

    @Autowired
    AuthenticationSuccessHandler successHandler;

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        return bCrypt;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers( "/", "/home","/signup",
                        "/saveSignup","/travel/searchTravel","/travel/","/WEB-INF/jsp/**", "/css/**", "/js/**", "/assets/**").permitAll()
                        .requestMatchers( "/flights/**", "/passenger/**").hasAuthority("STAFF")
                        .requestMatchers("/airlines/**", "/role/**")
                        .hasAuthority("ADMIN")

                        .anyRequest()
                        .authenticated())
                .formLogin((form) -> form.loginPage("/login").successHandler(successHandler)
                        .permitAll()
                        .failureUrl("/login?error=true"))
                .logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/"))
                .csrf(AbstractHttpConfigurer::disable).headers().cacheControl().disable();


                return http.build();
    }

    //Don't use this. Follow the JDBC Authentication schema.
    @Bean
    JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("select username, password, 'true' as enabled from user where username=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select username, role_name from user u, role r, user_role ur where u.username=? and u.user_id=ur.user_id and r.role_id=ur.role_id");
        return jdbcUserDetailsManager;
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(jdbcUserDetailsManager(dataSource));
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }
}
