package br.csi.politecnico.moviecatalog.controller;

import br.csi.politecnico.moviecatalog.dto.GeneroDTO;
import br.csi.politecnico.moviecatalog.dto.ResponseDTO;
import br.csi.politecnico.moviecatalog.service.GeneroService;
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
@RequestMapping("/api/generos")
@RequiredArgsConstructor
@Tag(name = "Gêneros", description = "Endpoints para gerenciamento de Gêneros de filmes")
@SecurityRequirement(name = "bearerAuth")
public class GeneroController {

    private final GeneroService generoService;

    @Operation(
        summary = "Cria um novo gênero",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Gênero criado com sucesso",
                content = @Content(schema = @Schema(implementation = GeneroDTO.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Requisição inválida (ex: nome do gênero em branco)",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado"
            )
        }
    )
    @PostMapping
    public ResponseEntity<ResponseDTO<GeneroDTO>> create(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do novo gênero") @RequestBody GeneroDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>("Genero", generoService.save(dto)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @Operation(
        summary = "Lista todos os gêneros",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de gêneros retornada com sucesso",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = GeneroDTO.class)))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado"
            )
        }
    )
    @GetMapping
    public ResponseEntity<ResponseDTO<List<GeneroDTO>>> findAll() {
        try {
            return ResponseEntity.ok(new ResponseDTO<>("Generos", generoService.findAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @Operation(
        summary = "Busca um gênero por ID",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Gênero encontrado",
                content = @Content(schema = @Schema(implementation = GeneroDTO.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Gênero não encontrado",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado"
            )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<GeneroDTO>> findById(@Parameter(description = "ID do gênero a ser buscado") @PathVariable Long id) {
        try {
            return ResponseEntity.ok(new ResponseDTO<>("Genero", generoService.findById(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @Operation(
        summary = "Atualiza um gênero existente",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Gênero atualizado com sucesso",
                content = @Content(schema = @Schema(implementation = GeneroDTO.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Gênero não encontrado",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Requisição inválida (ex: nome do gênero em branco)",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado"
            )
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<GeneroDTO>> update(@Parameter(description = "ID do gênero a ser atualizado") @PathVariable Long id, @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do gênero para atualização") @RequestBody GeneroDTO dto) {
        try {
            return ResponseEntity.ok(new ResponseDTO<>("Atualizado!", generoService.update(id, dto)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }

    @Operation(
        summary = "Deleta um gênero por ID",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Gênero deletado com sucesso",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Gênero não encontrado",
                content = @Content(schema = @Schema(implementation = ResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Não autorizado"
            )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@Parameter(description = "ID do gênero a ser deletado") @PathVariable Long id) {
        try {
            generoService.delete(id);
            return ResponseEntity.ok(ResponseDTO.builder().message("Gênero deletado com sucesso").build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<>(e.getMessage(), null));
        }
    }
}
