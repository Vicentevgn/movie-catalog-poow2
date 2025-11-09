package br.csi.politecnico.moviecatalog.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "filme_ator")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmeAtor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "filme_id", nullable = false)
    private Filme filme;

    @ManyToOne
    @JoinColumn(name = "ator_id", nullable = false)
    private Ator ator;

    @Column(nullable = false)
    private String papel; // Adicionando um atributo extra na tabela de junção para demonstrar a utilidade
}
