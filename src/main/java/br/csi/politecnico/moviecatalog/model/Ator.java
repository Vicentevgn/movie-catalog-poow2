package br.csi.politecnico.moviecatalog.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ator")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String biografia;
}
