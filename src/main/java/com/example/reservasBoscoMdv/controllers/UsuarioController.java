package com.example.reservasBoscoMdv.controllers;

import com.example.reservasBoscoMdv.entities.Usuario;
import com.example.reservasBoscoMdv.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/list")
    public ResponseEntity<Iterable<Usuario>> getAllUsuarios() {
        return ResponseEntity.ok().body(usuarioService.findAll());
    }

    @GetMapping("/list-name")
    public ResponseEntity<Iterable<Usuario>> getUsuariosByName(String nombre) {
        return ResponseEntity.ok().body(usuarioService.findAllByNombre(nombre));
    }

    @GetMapping("/list-email")
    public ResponseEntity<Usuario> getUsuarioByEmail(String email) {
        return usuarioService.findByEmail(email)
                .map(usuario -> ResponseEntity.ok().body(usuario))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(usuario -> ResponseEntity.ok().body(usuario))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUsuarioById(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        return usuarioService.update(id, usuarioActualizado)
                .map(updatedUsuario -> ResponseEntity.ok().body(updatedUsuario))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
