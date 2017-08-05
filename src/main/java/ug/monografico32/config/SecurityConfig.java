package ug.monografico32.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Jose Elias on 05/08/2017.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.
           inMemoryAuthentication().
                withUser("admin").password("admin").roles("ADMIN").and().
                withUser("estudiante").password("estudiante").roles("ESTUDIANTE").and().
                withUser("docente").password("docente").roles("DOCENTE");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.
            authorizeRequests().
                antMatchers("/resources/**").permitAll().and().
            authorizeRequests().
                anyRequest().authenticated().and().
                formLogin().
                    loginPage("/login").
                    permitAll().
                    defaultSuccessUrl("/home");
    }
}
