package com.way2p.todo.services.jwt;

import com.way2p.todo.entity.Role;
import com.way2p.todo.entity.User;
import com.way2p.todo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Trouver l'utilisateur par son email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Convertir les rôles de l'utilisateur en SimpleGrantedAuthority
        var authorities = user.getRoles().stream()
                .map(Role::getRoleName) // Récupère le nom du rôle
                .map(SimpleGrantedAuthority::new) // Crée des autorités à partir du nom du rôle
                .collect(Collectors.toList());

        // Retourner l'utilisateur avec ses rôles sous forme de GrantedAuthorities
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities // Les rôles sont maintenant inclus dans les autorités
        );
    }
}
