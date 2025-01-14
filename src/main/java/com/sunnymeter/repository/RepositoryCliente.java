package com.sunnymeter.repository;

import com.sunnymeter.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryCliente extends JpaRepository<Cliente, String> {
}
