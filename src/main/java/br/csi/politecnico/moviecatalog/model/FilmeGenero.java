package br.csi.politecnico.moviecatalog.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "filme_genero")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmeGenero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "filme_id", nullable = false)
    private Filme filme;

    @ManyToOne
    @JoinColumn(name = "genero_id", nullable = false)
    private Genero genero;
}
