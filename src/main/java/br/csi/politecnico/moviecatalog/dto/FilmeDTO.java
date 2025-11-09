package br.csi.politecnico.moviecatalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmeDTO {
    private Long id;
    @NotBlank(message = "O título é obrigatório")
    private String titulo;
    private String sinopse;
    @NotNull(message = "O ano de lançamento é obrigatório")
    private Integer anoLancamento;

    // IDs para o relacionamento N:M com Genero
    private List<Long> generosIds;

    // DTOs para o relacionamento N:M com Ator, incluindo o papel
    private List<FilmeAtorDTO> atores;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FilmeAtorDTO {
        @NotNull(message = "O ID do ator é obrigatório")
        private Long atorId;
        @NotBlank(message = "O papel do ator é obrigatório")
        private String papel;
    }
}
