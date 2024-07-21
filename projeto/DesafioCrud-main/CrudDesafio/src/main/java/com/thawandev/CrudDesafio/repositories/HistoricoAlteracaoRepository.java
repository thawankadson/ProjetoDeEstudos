package com.thawandev.CrudDesafio.repositories;

import com.thawandev.CrudDesafio.entities.HistoricoAlteracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HistoricoAlteracaoRepository extends JpaRepository<HistoricoAlteracao, Long> {
    List<HistoricoAlteracao> findByMedicamentoId(Long medicamentoId);
}