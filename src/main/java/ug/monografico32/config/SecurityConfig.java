package ug.monografico32.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ug.monografico32.service.UserService;

/**
 * Created by Jose Elias on 05/08/2017.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_URL = {"/docente/agregar/**", "/estudiante/agregar*",
                                                "/admin/agregar/**",
                                                "/resources/**", "/login**"};

    private static final String[] AUTH_URL = {"/asignatura/\\d+",
                            "/curso/\\d+","/curso/\\d+/estudiantes",
                            "/estudiante/\\d+","/estudiante/\\d+/cursos",
                            "/horario/clase/\\d+",
                            "/","/home"};

    private static final String[] ADMIN_URL = {"**/**", "/**", "/docente/**"};

    private static final String[] DOCENTE_URL = {"/curso/all\\?encargado=\\d+", "/docente/\\d+",
                                            "/horario/clase\\?instructor=\\d+",
                                            "/periodo/seleccionar"};

    private static final String[] ESTUDIANTE_URL = {};

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.
            authorizeRequests().
                antMatchers(PUBLIC_URL).permitAll().
                regexMatchers(AUTH_URL).authenticated().
                antMatchers(ADMIN_URL).hasAuthority("ADMINISTRADOR").
                regexMatchers(DOCENTE_URL).hasAuthority("DOCENTE").
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

        auth.userDetailsService(userService);
    }
}
