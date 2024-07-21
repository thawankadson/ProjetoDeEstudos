package com.thawandev.CrudDesafio.dto;

import com.thawandev.CrudDesafio.entities.Material;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public class MaterialDTO {
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

    public MaterialDTO() {}

    public MaterialDTO(Long id, String nome, String lote, LocalDate validade, Integer quantidadeTotal) {
        this.id = id;
        this.nome = nome;
        this.lote = lote;
        this.validade = validade;
        this.quantidadeTotal = quantidadeTotal;
    }

    public MaterialDTO(Material material) {
        this.id = material.getId();
        this.nome = material.getNome();
        this.lote = material.getLote();
        this.validade = material.getValidade();
        this.quantidadeTotal = material.getQuantidadeTotal();
    }

    // Getters e Setters
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
}
