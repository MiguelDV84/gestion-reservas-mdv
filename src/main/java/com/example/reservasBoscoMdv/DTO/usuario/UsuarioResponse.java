package com.example.reservasBoscoMdv.DTO.usuario;

import com.example.reservasBoscoMdv.entities.Usuario;

public record UsuarioResponse(
        Long id,
        String nombre,
        String apellidos,
        String email,
        String roles
) {
    public static UsuarioResponse fromEntity(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getEmail(),
                usuario.getAuthorities().iterator().next().getAuthority()
        );
    }
}
