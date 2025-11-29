package com.uce.sedral.services.impl;

import com.uce.sedral.exceptions.ModeloNotFoundException;
import com.uce.sedral.models.entities.Responsable;
import com.uce.sedral.repositories.IResponsableRepo;
import com.uce.sedral.services.IResponsableService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResponsableServiceImpl implements IResponsableService, UserDetailsService {

    private final IResponsableRepo repo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Responsable save(Responsable responsable) {
        String claveEncriptada = passwordEncoder.encode(responsable.getPassword());
        responsable.setPassword(claveEncriptada);
        return repo.save(responsable);
    }

    @Override
    public Responsable update(Responsable responsable) {
        Responsable responsableUpdate = repo.findById(responsable.getIdResponsable())
                .orElseThrow(() -> new ModeloNotFoundException("Responsable no encontrado"));
        responsableUpdate.setNombre(responsable.getNombre());
        responsableUpdate.setApellido(responsable.getApellido());
        responsableUpdate.setUsername(responsable.getUsername());
        if (responsable.getPassword() != null && !responsable.getPassword().startsWith("$2a$")) {
            String claveEncriptada = passwordEncoder.encode(responsable.getPassword());
            responsableUpdate.setPassword(claveEncriptada);
        }
        responsableUpdate.setTelefono(responsable.getTelefono());
        responsableUpdate.setHabilitado(responsable.isHabilitado());
        responsableUpdate.setCorreo(responsable.getCorreo());
        responsableUpdate.setRoles(responsable.getRoles());
        return repo.save(responsableUpdate);
    }

    @Override
    public Responsable findById(Integer id) {
        Optional<Responsable> responsable = repo.findById(id);
        return responsable.orElseGet(Responsable::new);
    }

    @Override
    public List<Responsable> findAll() {
        return repo.findAll();
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Responsable responsable = repo.findOneByUsername(username);

        if (responsable == null) {
            throw new UsernameNotFoundException("Usuario no existe: " + username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (responsable.getRoles() != null) {
            responsable.getRoles().forEach(role -> {
                String authority = role.getNombre();
                authorities.add(new SimpleGrantedAuthority(authority));
            });
        }
        return new User(
                responsable.getUsername(),
                responsable.getPassword(),
                responsable.isHabilitado(),
                true,
                true,
                true,
                authorities
        );
    }
}
