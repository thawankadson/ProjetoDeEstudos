package com.thawandev.CrudDesafio.dto;

import com.thawandev.CrudDesafio.entities.Medicamentos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class MedicamentoDTO {
    private Long id;

    @Size(min = 3, max = 80, message = "O nome deve ter entre 3 e 80 caracteres")
    @NotBlank(message = "Campo obrigatório")
    private String nome;

    @NotBlank(message = "Campo obrigatório")
    private String lote;

    @PastOrPresent(message = "A data de validade deve estar no passado ou presente")
    private LocalDate validade;

    @PositiveOrZero(message = "A quantidade total deve ser positiva ou zero")
    private Integer quantidadeTotal;

    private Long categoriaId;

    public MedicamentoDTO() {
    }

    public MedicamentoDTO(Long id, String nome, String lote, LocalDate validade, Integer quantidadeTotal, Long categoriaId) {
        this.id = id;
        this.nome = nome;
        this.lote = lote;
        this.validade = validade;
        this.quantidadeTotal = quantidadeTotal;
        this.categoriaId = categoriaId;
    }

    public MedicamentoDTO(Medicamentos medicamento) {
        this.id = medicamento.getId();
        this.nome = medicamento.getNome();
        this.lote = medicamento.getLote();
        this.validade = medicamento.getValidade();
        this.quantidadeTotal = medicamento.getQuantidadeTotal();
        this.categoriaId = medicamento.getCategoria() != null ? medicamento.getCategoria().getId() : null;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getLote() {
        return lote;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public Integer getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }
}
