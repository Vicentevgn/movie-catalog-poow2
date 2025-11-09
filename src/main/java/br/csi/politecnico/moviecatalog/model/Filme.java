package br.csi.politecnico.moviecatalog.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "filme")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String sinopse;
    private Integer anoLancamento;

    // Relacionamento N:M com Genero, modelado com tabela de junção FilmeGenero
    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FilmeGenero> generos;

    // Relacionamento N:M com Ator, modelado com tabela de junção FilmeAtor
    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FilmeAtor> atores;
}
