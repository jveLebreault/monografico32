package ug.monografico32.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table( indexes = {
            @Index(columnList = "username", unique = true)
        })
public abstract class Usuario extends Persona implements UserDetails{

    @Size(min = 1, max = 2)
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Roles> authorities;
    {authorities = new HashSet<>(); }

    @NotNull
    @Size(min = 6, max = 50)
    private String password;

    @NotNull
    @Size(min = 6, max = 50)
    @Column(unique = true)
    private String username;

    @NotNull
    private boolean accountNonExpired;

    @NotNull
    private boolean accountNonLocked;

    @NotNull
    private boolean credentialsNonExpired;

    @NotNull
    private boolean enabled;

    public Usuario(String username, String password, Roles... roles){
        this(username,password);
        Arrays.stream(roles).forEach(this::addAuthority);
    }

    public Usuario(String username, String password){
        this();
        this.username = username;
        this.password = password;
    }

    public Usuario(Roles role){
        this();
        authorities.add(role);
    }

    public Usuario(){
        super();
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }



    public boolean addAuthority(Roles role){

        if( authorities.contains(Roles.ESTUDIANTE) )
            return false;

        return authorities.add(role);
    }

    public void setAuthorities(Set<Roles> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean equals(Object obj) {

        if( !(obj instanceof Estudiante) )
            return false;

        Usuario usuario = (Usuario) obj;

        return this.username.equals(usuario.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.username);
    }
}
