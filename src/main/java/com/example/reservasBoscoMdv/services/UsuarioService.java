package com.example.reservasBoscoMdv.services;

import com.example.reservasBoscoMdv.entities.Usuario;
import com.example.reservasBoscoMdv.repositories.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;

    public List<Usuario> findAllByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> update(Long id, Usuario usuarioActualizado) {
       return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellidos(usuarioActualizado.getApellidos());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setRoles(usuarioActualizado.getRoles());

            return usuarioRepository.save(usuario);
        });
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

}
