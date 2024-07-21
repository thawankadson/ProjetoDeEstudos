package com.thawandev.CrudDesafio.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_medicamentos")
public class Medicamentos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String lote;
    private LocalDate validade;
    private Integer quantidadeTotal;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    // Construtores
    public Medicamentos() {
    }

    public Medicamentos(Long id, String nome, String lote, LocalDate validade, Integer quantidadeTotal, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.lote = lote;
        this.validade = validade;
        this.quantidadeTotal = quantidadeTotal;
        this.categoria = categoria;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public Integer getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(Integer quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}