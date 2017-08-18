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

    private static final String[] AUTH_URL = {"/asignatura/\\d+",
                            "/curso/\\d+","/curso/\\d+/estudiantes",
                            "/estudiante/\\d+","/estudiante/\\d+/cursos",
                            "/horario/clase/\\d+",
                            "/","/home"};

    private static final String[] ADMIN_URL = {"/asignatura/**", "/curso/**", "/docente/**",
                                    "/estudiante/**", "/horario/**", "/periodo/**"};

    private static final String[] DOCENTE_URL = {"/curso/all\\?encargado=\\d+", "/docente/\\d+",
                                            "/horario/clase\\?instructor=\\d+",
                                            "/periodo/seleccionar"};

    private static final String[] ESTUDIANTE_URL = {};

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.
            authorizeRequests().
                antMatchers("/resources/**", "/login**").permitAll().
                regexMatchers(AUTH_URL).authenticated().
                regexMatchers(DOCENTE_URL).hasRole("DOCENTE").
                antMatchers(ADMIN_URL).hasRole("ADMIN").
            and().
            authorizeRequests().
                anyRequest().authenticated().
            and().
                formLogin().
                loginPage("/login").
                defaultSuccessUrl("/home");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.
                inMemoryAuthentication().
                withUser("admin").password("admin").roles("ADMIN").and().
                withUser("estudiante").password("estudiante").roles("ESTUDIANTE").and().
                withUser("docente").password("docente").roles("DOCENTE");

    }
}
