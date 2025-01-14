package com.sunnymeter.instalacao;

import com.sunnymeter.consumo.Consumo;
import com.sunnymeter.contrato.Contrato;
import com.sunnymeter.instalacao.DTO.CadastroInstalacao;
import com.sunnymeter.producao.Producao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "instalacoes")
@Entity(name = "Instalacao")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Instalacao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String endereco;
    private String cep;
    private boolean ativo;

    @OneToOne(mappedBy = "instalacao")
    private Contrato contrato;

    @OneToMany(mappedBy = "instalacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consumo> consumos = new ArrayList<>();

    @OneToMany(mappedBy = "instalacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Producao> producoes = new ArrayList<>();

    public Consumo adicionarConsumo(float consumoKwh, long medTimestamp) {
        Consumo consumo = new Consumo(this, consumoKwh, medTimestamp);
        this.consumos.add(consumo);
        return consumo;
    }

    public Producao adicionarProducao(float consumoKwh, long medTimestamp) {
        Producao producao = new Producao(this, consumoKwh, medTimestamp);
        this.producoes.add(producao);
        return producao;
    }

    public Instalacao(CadastroInstalacao dados) {
        this.endereco = dados.endereco();
        this.cep = dados.cep();
        this.ativo = true;
    }

    public void deletar() {
        this.ativo = false;
    }
}
