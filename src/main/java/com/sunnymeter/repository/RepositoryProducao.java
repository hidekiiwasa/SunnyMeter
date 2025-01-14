package com.sunnymeter.repository;

import com.sunnymeter.instalacao.Instalacao;
import com.sunnymeter.producao.Producao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryProducao extends JpaRepository<Producao, String> {
    List<Producao> findByInstalacao(Instalacao instalacao);
    List<Producao> findByInstalacao_Id(String idInstalacao);
}
