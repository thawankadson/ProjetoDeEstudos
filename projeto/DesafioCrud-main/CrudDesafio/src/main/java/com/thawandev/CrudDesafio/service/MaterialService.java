package com.thawandev.CrudDesafio.service;

import com.thawandev.CrudDesafio.dto.MaterialDTO;
import com.thawandev.CrudDesafio.entities.Material;
import com.thawandev.CrudDesafio.repositories.MaterialRepository;
import com.thawandev.CrudDesafio.service.execption.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository repository;

    @Transactional(readOnly = true)
    public MaterialDTO findById(Long id) {
        return new MaterialDTO(repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Material não encontrado")));
    }

    @Transactional(readOnly = true)
    public Page<MaterialDTO> findAll(Pageable pageable) {
        Page<Material> result = repository.findAll(pageable);
        return result.map(MaterialDTO::new);
    }

    @Transactional
    public MaterialDTO insert(MaterialDTO dto) {
        Material entity = new Material();
        passDtoToEntity(dto, entity);
        return new MaterialDTO(repository.save(entity));
    }

    @Transactional
    public MaterialDTO update(Long id, MaterialDTO dto) {
        try {
            Material entity = repository.getReferenceById(id);
            passDtoToEntity(dto, entity);
            return new MaterialDTO(repository.save(entity));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Material não encontrado");
        }
    }

    @Transactional
    public MaterialDTO decrementarQuantidade(Long materialId, Integer quantidade) {
        Material material = repository.findById(materialId)
                .orElseThrow(() -> new ResourceNotFoundException("Material não encontrado"));
        if (material.getQuantidadeTotal() == null) {
            throw new RuntimeException("Quantidade não definida no estoque");
        }
        if (material.getQuantidadeTotal() < quantidade) {
            throw new RuntimeException("Quantidade insuficiente no estoque");
        }
        material.setQuantidadeTotal(material.getQuantidadeTotal() - quantidade);
        Material updatedMaterial = repository.save(material);
        return new MaterialDTO(updatedMaterial);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Material não encontrado");
        }
        repository.deleteById(id);
    }

    private void passDtoToEntity(MaterialDTO dto, Material entity) {
        entity.setNome(dto.getNome());
        entity.setLote(dto.getLote());
        entity.setValidade(dto.getValidade());
        entity.setQuantidadeTotal(dto.getQuantidadeTotal());
    }
}
