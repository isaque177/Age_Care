package com.example.AgeCare.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

@Entity
@Table(name = "relatorios")
@Getter
@Setter
public class RelatorioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_profissional")
    private String titulo;
    private Text Relatorio;

}
