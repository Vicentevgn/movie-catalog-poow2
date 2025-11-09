package br.csi.politecnico.moviecatalog.controller;

import br.csi.politecnico.moviecatalog.dto.FilmeDTO;
import br.csi.politecnico.moviecatalog.dto.ResponseDTO;
import br.csi.politecnico.moviecatalog.service.FilmeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filmes")
@RequiredArgsConstructor
@Tag(name = "Filmes", description = "Endpoints para gerenciamento de Filmes")
@SecurityRequirement(name = "bearerAuth")
public class FilmeController {

    private final FilmeService filmeService;

    @Operation(
        summary = "Cria um novo filme",
        description = "Cria um novo filme, associando-o a Gêneros e Atores existentes através dos seus IDs.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Filme criado com sucesso",
                content = @Content(schema = @Schema(implementation = FilmeDTO.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Requisição inválida (ex: ID de Gênero/Ator não encontrado, dados do filme inválidos)",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado"
            )
        }
    )
    @PostMapping
    public ResponseEntity<ResponseDTO<FilmeDTO>> create(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do novo filme, incluindo IDs de Gêneros e Atores") @RequestBody FilmeDTO dto) {
        try {
            return new ResponseEntity<>(new ResponseDTO<>("Filme criado com sucesso!", filmeService.save(dto)), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @Operation(
        summary = "Lista todos os filmes",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de filmes retornada com sucesso",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = FilmeDTO.class)))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado"
            )
        }
    )
    @GetMapping
    public ResponseEntity<ResponseDTO<List<FilmeDTO>>> findAll() {
        try {
            return ResponseEntity.ok(new ResponseDTO<>("Filmes", filmeService.findAll()));
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO<>(e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
        summary = "Busca um filme por ID",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Filme encontrado",
                content = @Content(schema = @Schema(implementation = FilmeDTO.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Filme não encontrado",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado"
            )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<FilmeDTO>> findById(@Parameter(description = "ID do filme a ser buscado") @PathVariable Long id) {
        try {
            return ResponseEntity.ok(new ResponseDTO<>("Filme", filmeService.findById(id)));
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO<>(e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
        summary = "Atualiza um filme existente",
        description = "Atualiza um filme, incluindo seus relacionamentos N×N com Gêneros e Atores.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Filme atualizado com sucesso",
                content = @Content(schema = @Schema(implementation = FilmeDTO.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Filme, Gênero ou Ator não encontrado",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Requisição inválida",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado"
            )
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<FilmeDTO>> update(@Parameter(description = "ID do filme a ser atualizado") @PathVariable Long id, @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do filme para atualização") @RequestBody FilmeDTO dto) {
        try {
            return ResponseEntity.ok(new ResponseDTO<>("Atualizado com sucesso!", filmeService.update(id, dto)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @Operation(
        summary = "Deleta um filme por ID",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Filme deletado com sucesso",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Filme não encontrado",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado"
            )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@Parameter(description = "ID do filme a ser deletado") @PathVariable Long id) {
        try {
            filmeService.delete(id);
            return ResponseEntity.ok(ResponseDTO.builder().message("Filme deletado com sucesso").build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(e.getMessage(), null));
        }
    }
}
