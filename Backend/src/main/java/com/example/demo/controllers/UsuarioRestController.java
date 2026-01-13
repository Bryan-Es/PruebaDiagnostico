package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.models.entity.Usuario;
import com.example.demo.models.services.IUsuarioService; 

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioRestController {

    @Autowired
    private IUsuarioService usuarioService;

    // 1. Obtener todos
    @GetMapping
    public List<Usuario> obtenerTodos() {
        return usuarioService.findAll();
    }

    // 2. Guardar uno nuevo
    @PostMapping
    public Usuario guardarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    // 3. Obtener por ID
    @GetMapping("/{id}")
    public Usuario obtenerPorId(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    // 4. Eliminar por ID
    @DeleteMapping("/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.delete(id);
        return "Usuario eliminado correctamente";
    }
}
