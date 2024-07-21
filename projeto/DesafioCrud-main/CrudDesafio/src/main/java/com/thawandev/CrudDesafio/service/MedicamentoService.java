package com.thawandev.CrudDesafio.service;

import com.thawandev.CrudDesafio.dto.MedicamentoDTO;
import com.thawandev.CrudDesafio.entities.Categoria;
import com.thawandev.CrudDesafio.entities.HistoricoAlteracao;
import com.thawandev.CrudDesafio.entities.Medicamentos;
import com.thawandev.CrudDesafio.repositories.CategoriaRepository;
import com.thawandev.CrudDesafio.repositories.HistoricoAlteracaoRepository;
import com.thawandev.CrudDesafio.repositories.MedicamentoRepository;
import com.thawandev.CrudDesafio.service.execption.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MedicamentoService {

    @Autowired
    private MedicamentoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private HistoricoAlteracaoRepository historicoAlteracaoRepository;

    @Transactional(readOnly = true)
    public List<HistoricoAlteracao> findHistoricoById(Long id) {
        return historicoAlteracaoRepository.findByMedicamentoId(id);
    }

    @Transactional(readOnly = true)
    public MedicamentoDTO findById(Long id) {
        Medicamentos medicamento = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicamento não encontrado"));
        return new MedicamentoDTO(medicamento);
    }

    @Transactional(readOnly = true)
    public Page<MedicamentoDTO> findAll(Pageable pageable) {
        Page<Medicamentos> result = repository.findAll(pageable);
        return result.map(MedicamentoDTO::new);
    }

    @Transactional
    public MedicamentoDTO insert(MedicamentoDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        Medicamentos entity = new Medicamentos();
        passDtoToEntity(dto, entity);
        entity.setCategoria(categoria);

        Medicamentos savedEntity = repository.save(entity);

        // Register the creation in the history
        registerHistoricoAlteracao(savedEntity, "Nome", null, savedEntity.getNome());

        return new MedicamentoDTO(savedEntity);
    }

    @Transactional
    public MedicamentoDTO update(Long id, MedicamentoDTO dto) {
        Medicamentos entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicamento não encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        // Check and register changes
        checkAndRegisterChange("Nome", entity.getNome(), dto.getNome(), entity);
        checkAndRegisterChange("Lote", entity.getLote(), dto.getLote(), entity);
        checkAndRegisterChange("Validade",
                entity.getValidade() != null ? entity.getValidade().toString() : null,
                dto.getValidade() != null ? dto.getValidade().toString() : null,
                entity);
        checkAndRegisterChange("QuantidadeTotal",
                entity.getQuantidadeTotal() != null ? entity.getQuantidadeTotal().toString() : null,
                dto.getQuantidadeTotal() != null ? dto.getQuantidadeTotal().toString() : null,
                entity);

        passDtoToEntity(dto, entity);
        entity.setCategoria(categoria);
        Medicamentos updatedEntity = repository.save(entity);

        return new MedicamentoDTO(updatedEntity);
    }

    @Transactional
    public MedicamentoDTO decrementQuantity(Long medicamentoId, Integer quantidade) {
        Medicamentos medicamento = repository.findById(medicamentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Medicamento não encontrado"));

        if (medicamento.getQuantidadeTotal() == null) {
            throw new RuntimeException("Quantidade não definida no estoque");
        }
        if (medicamento.getQuantidadeTotal() < quantidade) {
            throw new RuntimeException("Quantidade insuficiente no estoque");
        }

        int quantidadeAntiga = medicamento.getQuantidadeTotal();
        medicamento.setQuantidadeTotal(quantidadeAntiga - quantidade);
        Medicamentos updatedMedicamento = repository.save(medicamento);

        // Register the change in the history
        registerHistoricoAlteracao(updatedMedicamento, "QuantidadeTotal", Integer.toString(quantidadeAntiga), Integer.toString(updatedMedicamento.getQuantidadeTotal()));

        return new MedicamentoDTO(updatedMedicamento);
    }

    @Transactional(readOnly = true)
    public List<MedicamentoDTO> search(String nome, String lote, LocalDate validade, Long categoriaId) {
        List<Medicamentos> medicamentos = repository.search(nome, lote, validade, categoriaId);
        return medicamentos.stream()
                .map(MedicamentoDTO::new)
                .toList();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Medicamento não encontrado");
        }
        repository.deleteById(id);
    }

    private void passDtoToEntity(MedicamentoDTO dto, Medicamentos entity) {
        entity.setNome(dto.getNome());
        entity.setLote(dto.getLote());
        entity.setValidade(dto.getValidade());
        entity.setQuantidadeTotal(dto.getQuantidadeTotal());
    }

    private void checkAndRegisterChange(String campo, String valorAntigo, String valorNovo, Medicamentos entity) {
        if (valorAntigo == null ? valorNovo != null : !valorAntigo.equals(valorNovo)) {
            registerHistoricoAlteracao(entity, campo, valorAntigo, valorNovo);
        }
    }

    private void registerHistoricoAlteracao(Medicamentos medicamento, String campoAlterado, String valorAntigo, String valorNovo) {
        HistoricoAlteracao historico = new HistoricoAlteracao();
        historico.setMedicamento(medicamento);
        historico.setCampoAlterado(campoAlterado);
        historico.setValorAntigo(valorAntigo);
        historico.setValorNovo(valorNovo);
        historico.setDataAlteracao(LocalDateTime.now());
        historicoAlteracaoRepository.save(historico);
    }
}
