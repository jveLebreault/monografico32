package ug.monografico32.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ug.monografico32.dao.UsuarioRepository;
import ug.monografico32.model.Usuario;

@Service("userService")
public class UserService implements UserDetailsService{

    private static final String NOT_FOUND_MSG = "User not found";

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UserService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByUsername(s);

        if (usuario == null)
            throw new UsernameNotFoundException(NOT_FOUND_MSG);

        return usuario;
    }
}
