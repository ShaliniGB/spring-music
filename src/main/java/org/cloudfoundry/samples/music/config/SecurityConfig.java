package org.cloudfoundry.samples.music.config;


import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity( debug = true )
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //To test with in Memory store data and only authenticated users.
        /*http.authorizeRequests().anyRequest().
                fullyAuthenticated().and().httpBasic();*/

       /* http.csrf().disable()
                .formLogin().disable() // disable form authentication
                .anonymous().disable() // disable anonymous user
                .authorizeRequests().anyRequest().denyAll(); // denying all access*/

//to work with multiple routes
        http
                .requestMatcher(new OAuthRequestedMatcher())
                .csrf().disable()
                .anonymous().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                // when restricting access to 'Roles' you must remove the "ROLE_" part role
                // for "ROLE_USER" use only "USER"
                .antMatchers("/api/hello").access("hasAnyRole('USER')")
                .antMatchers("/api/admin").hasRole("ADMIN")
                // restricting all access to /api/** to authenticated users
                .antMatchers("/api/**").authenticated();



    }
    private static class OAuthRequestedMatcher implements RequestMatcher {
        public boolean matches(HttpServletRequest request) {
            // Determine if the resource called is "/api/**"
            String path = request.getServletPath();
            if (path.length() >= 5) {
                path = path.substring(0, 5);
                boolean isApi = path.equals("/api/");
                return isApi;
            } else return false;
        }

     /*   @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication() // creating user in memory
                .withUser("user")
                .password("password").roles("USER")
                .and().withUser("admin")
                .password("password").authorities("ROLE_ADMIN");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // provides the default AuthenticationManager as a Bean
        return super.authenticationManagerBean();
    }*/
    }
}
