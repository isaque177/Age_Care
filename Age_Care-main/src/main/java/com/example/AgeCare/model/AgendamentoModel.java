package com.example.AgeCare.model;

@Table(name = "Agendamento")
@Getter
@Setter
@Entity
public class AgendamentoModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
