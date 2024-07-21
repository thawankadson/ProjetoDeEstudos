package com.thawandev.CrudDesafio.entities;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_material")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String lote;
    private LocalDate validade;
    private int quantidadeTotal;

    public Material() {
    }

    public Material(Long id, String nome, String lote, LocalDate validade, int quantidadeTotal) {
        this.id = id;
        this.nome = nome;
        this.lote = lote;
        this.validade = validade;
        this.quantidadeTotal = quantidadeTotal;
    }

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
}

