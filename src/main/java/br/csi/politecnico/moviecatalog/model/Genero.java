package br.csi.politecnico.moviecatalog.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "genero")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Genero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome;
}
