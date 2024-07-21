package com.thawandev.CrudDesafio.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class HistoricoAlteracao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Medicamentos medicamento;

    private String campoAlterado;
    private String valorAntigo;
    private String valorNovo;
    private LocalDateTime dataAlteracao;

    // Construtor padrão
    public HistoricoAlteracao() {}

    // Construtor com parâmetros
    public HistoricoAlteracao(Long id, Medicamentos medicamento, String campoAlterado, String valorAntigo, String valorNovo, LocalDateTime dataAlteracao) {
        this.id = id;
        this.medicamento = medicamento;
        this.campoAlterado = campoAlterado;
        this.valorAntigo = valorAntigo;
        this.valorNovo = valorNovo;
        this.dataAlteracao = dataAlteracao;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medicamentos getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamentos medicamento) {
        this.medicamento = medicamento;
    }

    public String getCampoAlterado() {
        return campoAlterado;
    }

    public void setCampoAlterado(String campoAlterado) {
        this.campoAlterado = campoAlterado;
    }

    public String getValorAntigo() {
        return valorAntigo;
    }

    public void setValorAntigo(String valorAntigo) {
        this.valorAntigo = valorAntigo;
    }

    public String getValorNovo() {
        return valorNovo;
    }

    public void setValorNovo(String valorNovo) {
        this.valorNovo = valorNovo;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }
}
