package com.sunnymeter.contrato;

import com.sunnymeter.cliente.Cliente;
import com.sunnymeter.instalacao.Instalacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "contratos")
@Entity(name = "Contrato")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private long dataDeInicio;
    private int duracao;
    private boolean ativo;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente cliente;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_instalacao", referencedColumnName = "id")
    private Instalacao instalacao;

    public Contrato(int duracao, long dataDeInicio, Instalacao instalacao, Cliente cliente) {
        this.duracao = duracao;
        this.dataDeInicio = dataDeInicio;
        this.instalacao = instalacao;
        this.cliente = cliente;
        this.ativo = true;
    }

    public void deletar() {
        this.ativo = false;
    }
}
