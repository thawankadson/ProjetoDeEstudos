package com.thawandev.CrudDesafio.controllers;

import com.thawandev.CrudDesafio.dto.MaterialDTO;
import com.thawandev.CrudDesafio.service.MaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/material")
public class MaterialController {

    @Autowired
    private MaterialService service;

    @GetMapping("/{id}")
    public ResponseEntity<MaterialDTO> findById(@PathVariable Long id) {
        MaterialDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<MaterialDTO>> findAll(Pageable pageable) {
        Page<MaterialDTO> page = service.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<MaterialDTO> insert(@Valid @RequestBody MaterialDTO dto) {
        MaterialDTO createdDto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdDto.getId()).toUri();
        return ResponseEntity.created(uri).body(createdDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaterialDTO> update(@PathVariable Long id, @Valid @RequestBody MaterialDTO dto) {
        MaterialDTO updatedDto = service.update(id, dto);
        return ResponseEntity.ok(updatedDto);
    }

    @PutMapping("/{id}/decrementar")
    public ResponseEntity<MaterialDTO> decrementarEstoque(@PathVariable Long id, @RequestParam Integer quantidade) {
        MaterialDTO material = service.decrementarQuantidade(id, quantidade);
        return ResponseEntity.ok(material);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
