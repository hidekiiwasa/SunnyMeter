package com.sunnymeter.cliente;

import com.sunnymeter.cliente.DTO.CadastroCliente;
import com.sunnymeter.contrato.Contrato;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "clientes")
@Entity(name = "Cliente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nome;
    private String endereco;
    private String documento;
    private String cep;

    private String tipo;

    private boolean ativo;

    @OneToOne(mappedBy = "cliente")
    private Contrato contrato;


    public Cliente(CadastroCliente dados) {
        this.nome = dados.nome();
        this.documento = dados.documento();
        this.cep = dados.cep();
        this.tipo = dados.tipo();
        this.endereco = dados.endereco();
        this.ativo = true;
    }

    public void deletar() {
        this.ativo = false;
    }
}
