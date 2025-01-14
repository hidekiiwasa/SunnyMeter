package com.sunnymeter.producao;

import com.sunnymeter.instalacao.Instalacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "producoes")
@Entity(name = "Producao")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Producao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "id_instalacao", nullable = false)
    private Instalacao instalacao;
    private float producaoKwh;
    private long medTimestamp;


    public Producao(Instalacao instalacao, float producaoKwh, long medTimestamp) {
        this.instalacao = instalacao;
        this.producaoKwh = producaoKwh;
        this.medTimestamp = medTimestamp;
    }
}
