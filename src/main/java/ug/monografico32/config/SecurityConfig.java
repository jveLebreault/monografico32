package ug.monografico32.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ug.monografico32.service.UserService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_URL = {"/docente/agregar/**", "/estudiante/agregar*",
                                                "/admin/agregar/**",
                                                "/resources/**", "/login**"};

    private static final String[] AUTH_URL = {"/asignatura/\\d+",
                            "/curso/\\d+",
                            "/estudiante/\\d+","/estudiante/\\d+/cursos",
                            "/clase/\\d+", "/clase/\\d+/asignacion/all", "/clase/calificaciones/\\d+",
                            "/asignacion/\\d+", "/asignacion/\\d+/calificacion\\?estudianteId=\\d+", "/asignacion/\\d+/calificaciones",
                            "/","/home"};

    private static final String[] ADMIN_URL = {"**/**", "/**"};

    private static final String[] DOCENTE_URL = {"/curso/all\\?encargado=\\d+", "/docente/\\d+",
                                            "/curso/\\d+/estudiantes",
                                            "/clase\\?instructor=\\d+", "/clase/asignacion/crear", "/clase/\\d+/asignacion/crear", "/clase/calificaciones/\\d+\\?estudianteId=\\d+",
                                            "/asignacion/calificar\\?asignacionId=\\d+&estudianteId=\\d+", "/asignacion/calificar", "/asignacion/\\d+/calificaciones/\\d+",
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
                regexMatchers(DOCENTE_URL).hasAnyAuthority("DOCENTE", "ADMINISTRADOR").
                antMatchers(ADMIN_URL).hasAuthority("ADMINISTRADOR").
            and().
            authorizeRequests().
                anyRequest().authenticated().
            and().
                formLogin().
                loginPage("/login").
                defaultSuccessUrl("/home", true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{

        auth.userDetailsService(userService);
    }
}
