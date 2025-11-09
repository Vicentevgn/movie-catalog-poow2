package br.csi.politecnico.moviecatalog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AtorDTO {
    private Long id;
    @NotBlank(message = "O nome do ator é obrigatório")
    private String nome;
    private String biografia;
}
