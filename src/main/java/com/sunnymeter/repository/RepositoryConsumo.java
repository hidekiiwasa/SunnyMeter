package com.sunnymeter.repository;

import com.sunnymeter.consumo.Consumo;
import com.sunnymeter.instalacao.Instalacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryConsumo extends JpaRepository<Consumo, String> {
    List<Consumo> findByInstalacao(Instalacao instalacao);
    List<Consumo> findByInstalacao_id(String idInstalacao);
}
