package com.example.reservasBoscoMdv.services;

import com.example.reservasBoscoMdv.DTO.usuario.UsuarioResponse;
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

    public Usuario findEntityById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public List<UsuarioResponse> findAllByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre)
                .stream()
                .map(UsuarioResponse::fromEntity)
                .toList();
    }

    public Optional<UsuarioResponse> findById(Long id) {
        return usuarioRepository.findById(id)
                .map(UsuarioResponse::fromEntity);
    }

    public Optional<UsuarioResponse> findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(UsuarioResponse::fromEntity);
    }

    public List<UsuarioResponse> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponse::fromEntity)
                .toList();
    }

    public Optional<UsuarioResponse> update(Long id, Usuario usuarioActualizado) {
       return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellidos(usuarioActualizado.getApellidos());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setRole(usuarioActualizado.getRole());

            return UsuarioResponse.fromEntity(usuarioRepository.save(usuario));
        });
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

}
