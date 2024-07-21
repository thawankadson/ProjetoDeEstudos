package com.thawandev.CrudDesafio.controllers;

import com.thawandev.CrudDesafio.entities.Categoria;
import com.thawandev.CrudDesafio.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @PostMapping
    public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria categoria) {
        Categoria savedCategoria = categoriaRepository.save(categoria);
        return ResponseEntity.ok(savedCategoria);
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> getAllCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return ResponseEntity.ok(categorias);
    }
}
