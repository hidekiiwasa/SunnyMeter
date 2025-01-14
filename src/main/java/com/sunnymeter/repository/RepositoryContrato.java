package com.sunnymeter.repository;

import com.sunnymeter.contrato.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryContrato extends JpaRepository<Contrato, String> {
    List<Contrato> findAllByAtivoTrue();
}
