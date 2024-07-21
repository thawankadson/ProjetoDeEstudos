package com.thawandev.CrudDesafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thawandev.CrudDesafio.entities.Medicamentos;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamentos, Long>{
    @Query("SELECT m FROM Medicamentos m WHERE " +
            "(:nome IS NULL OR m.nome LIKE %:nome%) AND " +
            "(:lote IS NULL OR m.lote LIKE %:lote%) AND " +
            "(:validade IS NULL OR m.validade = :validade) AND " +
            "(:categoriaId IS NULL OR m.categoria.id = :categoriaId)")
    List<Medicamentos> search(@Param("nome") String nome,
                              @Param("lote") String lote,
                              @Param("validade") LocalDate validade,
                              @Param("categoriaId") Long categoriaId);
}

