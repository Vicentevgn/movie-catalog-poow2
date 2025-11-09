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
public class GeneroDTO {
    private Long id;
    @NotBlank(message = "O nome do gênero é obrigatório")
    private String nome;
}
