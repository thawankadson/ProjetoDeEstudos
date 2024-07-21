package com.thawandev.CrudDesafio.repositories;

import com.thawandev.CrudDesafio.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
}
