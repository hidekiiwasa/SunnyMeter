package com.sunnymeter.consumo;

import com.sunnymeter.instalacao.Instalacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "consumos")
@Entity(name = "Consumo")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Consumo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "id_instalacao", nullable = false)
    private Instalacao instalacao;

    private float consumoKwh;
    private long medTimestamp;


    public Consumo(Instalacao instalacao, float consumoKwh, long medTimestamp) {
        this.instalacao = instalacao;
        this.consumoKwh = consumoKwh;
        this.medTimestamp = medTimestamp;
    }
}
